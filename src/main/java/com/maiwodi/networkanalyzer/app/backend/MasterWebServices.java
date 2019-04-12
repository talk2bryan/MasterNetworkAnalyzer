package com.maiwodi.networkanalyzer.app.backend;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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

import com.maiwodi.networkanalyzer.app.backend.models.DummyModel;
import com.maiwodi.networkanalyzer.app.backend.models.NetworkData;

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
	 * */
	private Connection connect() {
		// Load properties file.
		try {
			input = new FileInputStream("config.properties");
			prop.load(input);
			
			dbLocation = prop.getProperty("dbLocation");
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}
		}
		
		String urlString = String.format("%s%s","jdbc:sqlite:", dbLocation);
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
	
	@POST
	@Path("/postAddWorker")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addWorker(String workerBaseAddress) {
		String sql = "INSERT INTO Worker(BaseAddress) VALUES(?)";
		
		try (Connection connection = this.connect();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, workerBaseAddress);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Added new worker.";
	}
	
	@POST
	@Path("/postAddCloudWorker")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addCloudWorker(String workerBaseAddress) {
		String sql = "INSERT INTO Cloud(BaseAddress) VALUES(?)";
		
		try (Connection connection = this.connect();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, workerBaseAddress);
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
			String sql = "INSERT INTO "
					+ "NetworkData(TimeStamp, rssiValue, SpeedInMbps) "
					+ "VALUES(?, ?, ?)";
			// Add code to process data.
			try (Connection connection = this.connect();) {
				for (NetworkData networkData : networkDataList) {
					PreparedStatement statement = connection.prepareStatement(sql);
					statement.setString(1, networkData.getTimeStamp());
					statement.setInt(2, networkData.getRssiValue());
					statement.setInt(3, networkData.getSpeedInMbps());
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
	             Statement stmt  = conn.createStatement();
	             ResultSet rs    = stmt.executeQuery(sql)){
			List<NetworkData> networkDataList = new ArrayList<NetworkData>(); 
	            while (rs.next()) {
	            	NetworkData data = new NetworkData(
	            			rs.getInt("rssiValue"),
	            			rs.getInt("SpeedInMbps"),
	            			rs.getString("TimeStamp"));
	            	networkDataList.add(data);
	            }
	            return networkDataList;
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
		return null;
	}

}