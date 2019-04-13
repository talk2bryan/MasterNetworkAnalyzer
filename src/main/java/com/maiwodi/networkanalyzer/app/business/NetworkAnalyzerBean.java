package com.maiwodi.networkanalyzer.app.business;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.json.JSONObject;

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
//		workers = new Workers();
		workers = Utilities.unmarshall(
				JerseyClient.sendGetResponse("http://localhost:8080/networkanalyzer/", "rest/master/getAllWorkers"),
				Workers.class);

		cloudWorkers = Utilities.unmarshall(JerseyClient.sendGetResponse("http://localhost:8080/networkanalyzer/",
				"rest/master/getAllCloudWorkers"), Workers.class);

		worker = new Worker();
	}

	public void submitWorkerBasedOnOption() {
		switch (selectedOption) {
		case CLOUD:
			submitCloudWorker();
			break;

		case FOG:
			submitWorker();
			break;

		default:
			break;
		}
	}

	public void submitWorker() {

		JerseyClient.sendPostResponse("http://localhost:8080/networkanalyzer/", "rest/master/postAddWorker",
				new JSONObject(this.getWorker()));

		workers.addWorker(worker);

		Utilities.showInfoMessage("Worker Submitted", "The submitted worker IP is " + worker.getWorkerIP());

		// reset after submit
		worker = new Worker();
	}

	/**
	 * 
	 */
	public void clearNetworkData() {
		JerseyClient.sendGetResponse("http://localhost:8080/networkanalyzer/", "rest/master/cleanupNetworkData");

		Utilities.showInfoMessage("Success", "Network data has been reset");
	}

	public void submitCloudWorker() {

		JerseyClient.sendPostResponse("http://localhost:8080/networkanalyzer/", "rest/master/postAddCloudWorker",
				new JSONObject(this.getWorker()));

		cloudWorkers.addWorker(worker);

		Utilities.showInfoMessage("Worker Submitted", "The submitted worker IP is " + worker.getWorkerIP());

		worker = new Worker();
	}

	// TODO: data from Android

	public void collectDataFromClient(String json) {
		LOGGER.debug("Received JSON: {}", json);

		Response response = JerseyClient.sendPostResponse("http://localhost:8080/networkanalyzer/",
				"rest/master/post/data", json);

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
