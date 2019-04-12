package com.maiwodi.networkanalyzer.app.business;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.maiwodi.networkanalyzer.app.models.Worker;
import com.maiwodi.networkanalyzer.app.models.Workers;
import com.maiwodi.networkanalyzer.app.utils.AbstractPageBean;
import com.maiwodi.networkanalyzer.app.utils.JerseyClient;
import com.maiwodi.networkanalyzer.app.utils.Utilities;

@Named
@ViewScoped
public class NetworkAnalyzerBean extends AbstractPageBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8692181958971244558L;

	private static final Logger LOGGER = LogManager.getLogger(NetworkAnalyzerBean.class.getName());

	private Workers workers;

	private Worker worker;

	private Workers cloudWorkers;

	private Worker cloudWorker;

	private List<String> options;

	private String selectedOption;

	private static final String CLOUD = "cloud";

	private static final String FOG = "fog";

	@PostConstruct
	public void loadPage() {

		options = new ArrayList<>();

		options.add(CLOUD);
		options.add(FOG);
		selectedOption = FOG;

		// TODO: call ws and init from db
		workers = new Workers();
		worker = new Worker();
	}

	public void submitWorker() {
		workers.addWorker(worker);

		Utilities.showInfoMessage("Worker Submitted", "The submitted worker IP is " + worker.getWorkerIP());

		// reset after submit
		worker = new Worker();
	}

	/*
	 * public void submitWorker() { workers.addWorker(worker);
	 * 
	 * String response =
	 * JerseyClient.sendGetResponse("http://localhost:8080/networkanalyzer/",
	 * "rest/myresource/get");
	 * 
	 * LOGGER.debug("Response: {}", response);
	 * 
	 * Utilities.showInfoMessage("Worker Submitted", "The submitted worker IP is " +
	 * worker.getWorkerIP()); }
	 */

	// TODO: data from Android

	public void collectDataFromClient(String json) {
		LOGGER.debug("Received JSON: {}", json);

		Response response = JerseyClient.sendPostResponse("http://localhost:8080/networkanalyzer/",
				"rest/myresource/post/data", json);

		LOGGER.debug("Response: {}", response.readEntity(String.class));

	}

	public Workers getWorkers() {
		return workers;
	}

	public void setWorkers(Workers workers) {
		this.workers = workers;
	}

	public Worker getWorker() {
		return worker;
	}

	public void setWorker(Worker worker) {
		this.worker = worker;
	}

	public Worker getCloudWorker() {
		return cloudWorker;
	}

	public void setCloudWorker(Worker cloudWorker) {
		this.cloudWorker = cloudWorker;
	}

	public Workers getCloudWorkers() {
		return cloudWorkers;
	}

	public void setCloudWorkers(Workers cloudWorkers) {
		this.cloudWorkers = cloudWorkers;
	}

	public List<String> getOptions() {
		return options;
	}

	public void setOptions(List<String> options) {
		this.options = options;
	}

	public String getSelectedOption() {
		return selectedOption;
	}

	public void setSelectedOption(String selectedOption) {
		this.selectedOption = selectedOption;
	}

}
