package com.maiwodi.networkanalyzer.app.business;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

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

	@PostConstruct
	public void loadPage() {
		workers = new Workers();
		worker = new Worker();
	}

	public void submitWorker() {
		workers.addWorker(worker);

		String response = JerseyClient.sendGetResponse("http://localhost:8080/networkanalyzer/", "rest/myresource/get");

		LOGGER.debug("Response: {}", response);

		Utilities.showInfoMessage("Worker Submitted", "The submitted worker IP is " + worker.getWorkerIP());
	}

	// TODO: remove it later
	public void backendTest() {
		// JerseyClient.sendGetResponse(base, path)

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

}
