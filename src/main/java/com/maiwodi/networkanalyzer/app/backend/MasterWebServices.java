package com.maiwodi.networkanalyzer.app.backend;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.condition.JRE;

import com.maiwodi.networkanalyzer.app.backend.models.DummyModel;
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
			String sql = "INSERT INTO " + "NetworkData(TimeStamp, rssiValue, SpeedInMbps) " + "VALUES(?, ?, ?)";
			// Add code to process data.
			try (Connection connection = this.connect();) {
				for (NetworkData networkData : networkDataList) {
					PreparedStatement statement = connection.prepareStatement(sql);
					statement.setString(1, networkData.getTimeStamp());
					statement.setInt(2, networkData.getRssiValue());
					statement.setInt(3, networkData.getSpeedInMbps());

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
		String sql = "SELECT TimeStamp, rssiValue, SpeedInMbps FROM NetworkData";
		try (Connection conn = this.connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			List<NetworkData> networkDataList = new ArrayList<NetworkData>();
			while (rs.next()) {
				NetworkData data = new NetworkData(rs.getInt("downloadSpeed"), rs.getInt("rssiValue"),
						rs.getInt("SpeedInMbps"), rs.getString("TimeStamp"));
				networkDataList.add(data);
			}
			return networkDataList;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	@GET
	@Path("/analyze")
//	@Produces(MediaType.APPLICATION_JSON)
	public void invokeWorkderAnalyzeNetworkData() {

		List<NetworkData> networkDataList = Utilities.unmarshall(JerseyClient.sendGetResponse(
				"http://localhost:8080/networkanalyzer/", "rest/master/readNetworkDataTable"), List.class);

		Workers workers = Utilities.unmarshall(
				JerseyClient.sendGetResponse("http://localhost:8080/networkanalyzer/", "rest/master/getAllWorkers"),
				Workers.class);

		Workers cloudWorkers = Utilities.unmarshall(JerseyClient.sendGetResponse(
				"http://localhost:8080/networkanalyzer/", "rest/master/getAllCloudWorkers"), Workers.class);

		return;
	}
	
	
	@GET
	@Path("/cleanupNetworkData")
	public String cleanupNetworkDataRepository() {
		String sql = "DELETE FROM NetworkData";
		try (Connection connection = this.connect(); 
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.executeUpdate();
			return "Cleaned up NetworkData repository";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

}