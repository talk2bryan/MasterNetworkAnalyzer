package com.maiwodi.networkanalyzer.app.backend;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.json.JSONObject;

import com.maiwodi.networkanalyzer.app.backend.models.DummyModel;
import com.maiwodi.networkanalyzer.app.backend.models.MonteCarloParam;
import com.maiwodi.networkanalyzer.app.backend.models.NetworkData;
import com.maiwodi.networkanalyzer.app.backend.models.NetworkDataSummary;
import com.maiwodi.networkanalyzer.app.models.Worker;
import com.maiwodi.networkanalyzer.app.models.Workers;
import com.maiwodi.networkanalyzer.app.utils.JerseyClient;
import com.maiwodi.networkanalyzer.app.utils.Utilities;

/**
 * Root resource (exposed at "master" path)
 */
@Path("master")
public class MasterWebServices {

	private static final Logger LOGGER = LogManager.getLogger(MasterWebServices.class.getName());

	Properties prop = new Properties();
	InputStream input = null;
	String dbLocation = null;

	/*
	 * Connection to db/NetworkAnalyzerDB.db
	 * 
	 * @return Connection Object
	 */
	private Connection connect() {
		// Load properties file.
		dbLocation = DBUtils.loadProp("dbLocation");

		String urlString = String.format("%s%s", "jdbc:sqlite:", dbLocation);
		Connection connection = null;

		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection(urlString);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connection;
	}

	/**
	 * Method handling HTTP GET requests. The returned object will be sent to the
	 * client as "text/plain" media type.
	 *
	 * @return String that will be returned as a text/plain response.
	 */
	@GET
	@Path("/hello")
	@Produces(MediaType.TEXT_PLAIN)
	public String getIt() {
		return "Got it!";
	}

	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public DummyModel sampleJson() {
		return new DummyModel("afd", "ss");
	}

	@GET
	@Path("/getAllWorkers")
	@Produces(MediaType.APPLICATION_JSON)
	public Workers getAllWorkers() {

		Workers workers = new Workers();

		String sql = "SELECT * from Worker";

		try (Connection conn = this.connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				workers.addWorker(new Worker(rs.getString("BaseAddress")));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return workers;
	}

	@GET
	@Path("/getAllCloudWorkers")
	@Produces(MediaType.APPLICATION_JSON)
	public Workers getAllCloudWorkers() {

		Workers clouds = new Workers();

		String sql = "SELECT * from Cloud";

		try (Connection conn = this.connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				clouds.addWorker(new Worker(rs.getString("BaseAddress")));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return clouds;
	}

	@POST
	@Path("/postAddWorker")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addWorker(Worker worker) {
		String sql = "INSERT INTO Worker(BaseAddress) VALUES(?)";

		try (Connection connection = this.connect(); PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, worker.getWorkerIP());

			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Added new worker.";
	}

	@POST
	@Path("/postDeleteWorker")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteWorker(Worker worker) {
		String sql = "DELETE from Worker WHERE BaseAddress = (?)";

		try (Connection connection = this.connect(); PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, worker.getWorkerIP());

			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Deleted a worker.";
	}

	@POST
	@Path("/postDeleteCloudWorker")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteCloudWorker(Worker cloud) {
		String sql = "DELETE from Cloud WHERE BaseAddress = (?)";

		try (Connection connection = this.connect(); PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, cloud.getWorkerIP());

			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Deleted a worker.";
	}

	@POST
	@Path("/postAddCloudWorker")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addCloudWorker(Worker worker) {
		String sql = "INSERT INTO Cloud(BaseAddress) VALUES(?)";

		try (Connection connection = this.connect(); PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, worker.getWorkerIP());

			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "Added new CloudWorker.";
	}

	@POST
	@Path("post/data")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String postDataForAnalysis(List<NetworkData> networkDataList) {

		if (null != networkDataList) {
			String sql = "INSERT INTO " + "NetworkData(downloadSpeed, TimeStamp, rssiValue, SpeedInMbps) "
					+ "VALUES(?, ?, ?, ?)";
			// Add code to process data.
			try (Connection connection = this.connect();) {
				for (NetworkData networkData : networkDataList) {
					PreparedStatement statement = connection.prepareStatement(sql);
					statement.setDouble(1, networkData.getDownloadSpeed());
					statement.setString(2, networkData.getTimeStamp());
					statement.setInt(3, networkData.getRssiValue());
					statement.setInt(4, networkData.getSpeedInMbps());

					statement.executeUpdate();
				}

				return "Added NetworkData";
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {

			return "Could not add new NetworkData.";
		}
		return null;
	}

	@GET
	@Path("/readNetworkDataTable")
	@Produces(MediaType.APPLICATION_JSON)
	public List<NetworkData> readNetworkDataRepository() {
		String sql = "SELECT TimeStamp, rssiValue, SpeedInMbps, downloadSpeed FROM NetworkData";
		try (Connection conn = this.connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			List<NetworkData> networkDataList = new ArrayList<NetworkData>();
			while (rs.next()) {
				NetworkData data = new NetworkData(rs.getDouble("downloadSpeed"), rs.getInt("rssiValue"),
						rs.getInt("SpeedInMbps"), rs.getString("TimeStamp"));
				networkDataList.add(data);
			}
			return networkDataList;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Deprecated
	@GET
	@Path("/analyze")
	@Produces(MediaType.APPLICATION_JSON)
	public NetworkDataSummary invokeWorkderAnalyzeNetworkData() {

		Map<String, String> map = new HashMap<>();

		long executionTime = 0;

		String networkDataStr = JerseyClient.sendGetResponse("http://localhost:8080/networkanalyzer/",
				"rest/master/readNetworkDataTable");

		Workers workers = Utilities.unmarshall(
				JerseyClient.sendGetResponse("http://localhost:8080/networkanalyzer/", "rest/master/getAllWorkers"),
				Workers.class);

		Workers cloudWorkers = Utilities.unmarshall(JerseyClient.sendGetResponse(
				"http://localhost:8080/networkanalyzer/", "rest/master/getAllCloudWorkers"), Workers.class);

		// TODO: for testing purposes only
		Worker worker = workers.getWorkers().get(0);
		Worker cloud = cloudWorkers.getWorkers().get(0);

		long startTime = System.nanoTime();

//		return map;
		Response response = JerseyClient.sendPostResponse(worker.getWorkerIP(), "rest/worker/post/data",
				networkDataStr);

//		Response response = JerseyClient.sendPostResponse(cloud.getWorkerIP(), "rest/worker/post/data",
//				networkDataStr);

		long stopTime = System.nanoTime();

		executionTime = stopTime - startTime;

		map.put("executionTime", executionTime + "");

		LOGGER.info("executionTime: {} ns", executionTime);
		List<NetworkData> networkDataList = Utilities.unmarshall(networkDataStr, List.class);
		LOGGER.info("size of network data: {}", networkDataList.size());

		return response.readEntity(NetworkDataSummary.class);
	}

	// device option: fog, master, cloud
	@GET
	@Path("/analyze/{device}")
	@Produces(MediaType.APPLICATION_JSON)
	public NetworkDataSummary invokeWorkderAnalyzeNetworkData(@PathParam("device") String device) {

		Map<String, String> map = new HashMap<>();

		long executionTime = 0;

		String networkDataStr = JerseyClient.sendGetResponse("http://localhost:8080/networkanalyzer/",
				"rest/master/readNetworkDataTable");

		Workers workers = Utilities.unmarshall(
				JerseyClient.sendGetResponse("http://localhost:8080/networkanalyzer/", "rest/master/getAllWorkers"),
				Workers.class);

		Workers cloudWorkers = Utilities.unmarshall(JerseyClient.sendGetResponse(
				"http://localhost:8080/networkanalyzer/", "rest/master/getAllCloudWorkers"), Workers.class);

		// TODO: for testing purposes only
		Worker worker = workers.getWorkers().get(0);
		Worker cloud = cloudWorkers.getWorkers().get(0);

		long startTime = System.nanoTime();

//		return map;

		Response response = null;

		switch (device) {
		case "fog":
			response = JerseyClient.sendPostResponse(worker.getWorkerIP(), "rest/worker/post/data", networkDataStr);
			break;
		case "master":
			response = JerseyClient.sendPostResponse("http://localhost:8080/networkanalyzer/", "rest/worker/post/data",
					networkDataStr);
			break;

		case "cloud":
			response = JerseyClient.sendPostResponse(cloud.getWorkerIP(), "rest/worker/post/data", networkDataStr);
			break;

		default:
			response = JerseyClient.sendPostResponse("http://localhost:8080/networkanalyzer/", "rest/worker/post/data",
					networkDataStr);
			break;
		}

		long stopTime = System.nanoTime();

		executionTime = stopTime - startTime;

		map.put("executionTime", executionTime + "");

		LOGGER.info("executionTime: {} ns", executionTime);
		List<NetworkData> networkDataList = Utilities.unmarshall(networkDataStr, List.class);
		LOGGER.info("size of network data: {}", networkDataList.size());

		return response.readEntity(NetworkDataSummary.class);
	}

	@POST
	@Path("/mcSim/{device}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, String> invokeMCSim(@PathParam("device") String device, String monteCarloParam) {

		Map<String, String> map = new HashMap<>();

		long executionTime = 0;

		Workers workers = Utilities.unmarshall(
				JerseyClient.sendGetResponse("http://localhost:8080/networkanalyzer/", "rest/master/getAllWorkers"),
				Workers.class);

		Workers cloudWorkers = Utilities.unmarshall(JerseyClient.sendGetResponse(
				"http://localhost:8080/networkanalyzer/", "rest/master/getAllCloudWorkers"), Workers.class);

		// TODO: for testing purposes only
		Worker worker = workers.getWorkers().get(0);
		Worker cloud = cloudWorkers.getWorkers().get(0);

		long startTime = System.nanoTime();

//		return map;

		Response response = null;

		switch (device) {
		case "fog":
			response = JerseyClient.sendPostResponse(worker.getWorkerIP(), "rest/worker/post/mcSim", monteCarloParam);
			break;
		case "master":
			response = JerseyClient.sendPostResponse("http://localhost:8080/networkanalyzer/", "rest/worker/post/mcSim",
					monteCarloParam);
			break;

		case "cloud":
			response = JerseyClient.sendPostResponse(cloud.getWorkerIP(), "rest/worker/post/mcSim", monteCarloParam);
			break;

		default:
//			response = JerseyClient.sendPostResponse("http://localhost:8080/networkanalyzer/", "rest/worker/post/mcSim",
//					monteCarloParam);
			break;
		}

		long stopTime = System.nanoTime();

		executionTime = stopTime - startTime;

		map.put("executionTime", executionTime + "");
		map.put("optionValue", response.readEntity(Double.class).toString());
		map.put("monteCarloParam", monteCarloParam);

		LOGGER.info("executionTime: {} s", executionTime / 1_000_000_000.0);

		return map;
	}

	@GET
	@Path("/autoMcSim/{device}")
	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	@Produces("\"application/vnd.ms-excel\"")
	public Response invokeMCSim(@PathParam("device") String device) {

		Map<String, String> map = new HashMap<>();

		long executionTime = 0;

		Workers workers = Utilities.unmarshall(
				JerseyClient.sendGetResponse("http://localhost:8080/networkanalyzer/", "rest/master/getAllWorkers"),
				Workers.class);

		Workers cloudWorkers = Utilities.unmarshall(JerseyClient.sendGetResponse(
				"http://localhost:8080/networkanalyzer/", "rest/master/getAllCloudWorkers"), Workers.class);

		// TODO: for testing purposes only
		Worker worker = workers.getWorkers().get(0);
		Worker cloud = cloudWorkers.getWorkers().get(0);

		long startTime = System.nanoTime();

		MonteCarloParam monteCarloParam = new MonteCarloParam();

		XSSFWorkbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet();

		monteCarloParam.setM(1000);

		monteCarloParam.setN(10);

		JSONObject jsonObject = new JSONObject(monteCarloParam);

		Response response = null;

		switch (device) {
		case "fog":

			Row tempRow = sheet.getRow(0);
			if (tempRow == null) {
				tempRow = sheet.createRow(0);
			}

			tempRow.createCell(0, CellType.STRING).setCellValue('M');
			tempRow.createCell(0, CellType.STRING).setCellValue('N');
			tempRow.createCell(0, CellType.STRING).setCellValue("Option value");
			tempRow.createCell(0, CellType.STRING).setCellValue("Exceution Time");

//			

			for (int i = 0; i < 10; i++) {

				long localExecutionTime = 0;

				long localStartTime = System.nanoTime();

				response = JerseyClient.sendPostResponse(worker.getWorkerIP(), "rest/worker/post/mcSim", jsonObject);

				long localStopTime = System.nanoTime();

				localExecutionTime = localStopTime - localStartTime;

			}
			break;
		case "master":

			for (int i = 0; i < 1000; i++) {
				response = JerseyClient.sendPostResponse("http://localhost:8080/networkanalyzer/",
						"rest/worker/post/mcSim", jsonObject);
			}

			break;

		case "cloud":

			for (int i = 0; i < 1000; i++) {
				response = JerseyClient.sendPostResponse(cloud.getWorkerIP(), "rest/worker/post/mcSim", jsonObject);
			}
			break;

		default:
			break;
		}

		StreamingOutput streamingOutput = new StreamingOutput() {

			@Override
			public void write(OutputStream output) throws IOException, WebApplicationException {
				try {
					FileOutputStream outputStream = null;
					try {
						outputStream = new FileOutputStream("Test.xlsx");
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
					workbook.write(outputStream);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};

		long stopTime = System.nanoTime();

		executionTime = stopTime - startTime;

		map.put("executionTime", executionTime / 1_000_000_000.0 + "");
		map.put("optionValue", response.readEntity(Double.class).toString());
//		map.put("monteCarloParam", monteCarloParam);

		LOGGER.info("executionTime: {} s", executionTime / 1_000_000_000.0);

//		ResponseBuilder rb = Response.ok(streamingOutput);
//		rb.header("content-disposition", "attachment; filename=test2.xlsx");
//		return rb.build();

//		return Response.ok(streamingOutput, MediaType.APPLICATION_OCTET_STREAM)
//				.header("Content-Disposition", "attachment; filename=\"" + "test2.xlsx" + "\"") // optional
//				.build();

		try {
			FileOutputStream outputStream = new FileOutputStream("test");
			workbook.write(outputStream);
			workbook.close();

			ResponseBuilder responseBuilder = Response.ok(outputStream);

			responseBuilder.header("Content-Disposition", "attachment; filename=new-excel-file.xlsx");
			return responseBuilder.build();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;

	}

	@GET
	@Path("/cleanupNetworkData")
	public String cleanupNetworkDataRepository() {
		String sql = "DELETE FROM NetworkData";
		try (Connection connection = this.connect(); PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.executeUpdate();
			// TODO: remove this call
			printDataRepoDEBUG();
			return "Cleaned up NetworkData repository";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void printDataRepoDEBUG() {
		List<NetworkData> datas = readNetworkDataRepository();
		if (datas.size() > 0) {
			for (NetworkData networkData : datas) {
				System.out.println("DLS: " + networkData.getDownloadSpeed());
			}
		} else {
			System.out.println("Empty repo");
		}
	}

}