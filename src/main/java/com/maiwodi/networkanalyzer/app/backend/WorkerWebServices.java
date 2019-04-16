package com.maiwodi.networkanalyzer.app.backend;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.maiwodi.networkanalyzer.app.backend.models.MonteCarloParam;
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

		System.out.println(networkDataSummary);

		return networkDataSummary;
	}

	@POST
	@Path("post/mcSim")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public double mcSimulation(MonteCarloParam monteCarloParam) {

		double result = MonteCarloSimulation.mcSimulation(monteCarloParam.getK(), monteCarloParam.getT(),
				monteCarloParam.getS(), monteCarloParam.getR(), 0.4, 0.0, monteCarloParam.getN(),
				monteCarloParam.getM());

		return result;
	}

	@POST
	@Path("post/batchMcSim")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Double> batchMcSimulation(List<MonteCarloParam> monteCarloParams) {
		List<Double> results = new ArrayList<>();

		for (MonteCarloParam m : monteCarloParams) {
			results.add(this.mcSimulation(m));
		}

		return results;
	}

}
