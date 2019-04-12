package com.maiwodi.networkanalyzer.app.backend;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
	@Path("post/data")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	// TODO: model
	public DummyModel postDataForAnalysis(List<NetworkData> networkDataList) {

		if (null != networkDataList) {
			
			// Add code to process data.
			return new DummyModel("Got it", "Got it");
		} else {

			return new DummyModel("No data", "no data");
		}

	}

}