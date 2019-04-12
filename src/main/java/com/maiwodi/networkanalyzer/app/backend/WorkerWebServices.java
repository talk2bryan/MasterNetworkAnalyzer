package com.maiwodi.networkanalyzer.app.backend;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.maiwodi.networkanalyzer.app.backend.models.NetworkData;
import com.maiwodi.networkanalyzer.app.backend.models.NetworkDataSummary;

@Path("worker")
public class WorkerWebServices {

	@GET
	@Path("/hello")
	@Produces(MediaType.TEXT_PLAIN)
	public String getIt() {
		return "hello world from worker";
	}

	@POST
	@Path("post/data")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public NetworkDataSummary networkDataAnalysis(List<NetworkData> networkDataList) {

		NetworkDataSummary networkDataSummary = new NetworkDataSummary();

		if (null != networkDataList && !networkDataList.isEmpty()) {
			networkDataSummary.analysis(networkDataList);
		}

		return networkDataSummary;
	}

}
