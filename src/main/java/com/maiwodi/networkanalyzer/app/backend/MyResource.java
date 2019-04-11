package com.maiwodi.networkanalyzer.app.backend;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

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
	@Path("/getAddWorker")
	@Produces(MediaType.APPLICATION_JSON)
	public String addWorker() {
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