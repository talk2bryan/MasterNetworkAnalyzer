package com.maiwodi.networkanalyzer.app.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.primefaces.json.JSONObject;

import com.maiwodi.networkanalyzer.app.utils.JerseyClient;
import com.maiwodi.networkanalyzer.app.utils.Utilities;

public class Workers implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5858572571184737099L;

	private List<Worker> workers;

	public Workers() {
		super();
	}

	public Workers(List<Worker> workers) {
		super();
		this.workers = workers;
	}

	public void removeWorker(Worker worker) {
		if (this.workers != null) {

			JerseyClient.sendPostResponse("http://localhost:8080/networkanalyzer/", "rest/master/postDeleteWorker",
					new JSONObject(worker));

			workers.remove(worker);
		}

		Utilities.showInfoMessage("Info", "Worker IP: " + worker.getWorkerIP() + " has been successfull removed");
	}

	public void removeCloudWorker(Worker worker) {
		if (this.workers != null) {

			JerseyClient.sendPostResponse("http://localhost:8080/networkanalyzer/", "rest/master/postDeleteCloudWorker",
					new JSONObject(worker));

			workers.remove(worker);
		}

		Utilities.showInfoMessage("Info", "Worker IP: " + worker.getWorkerIP() + " has been successfull removed");
	}

	public void addWorker(Worker worker) {
		if (this.workers == null) {
			workers = new ArrayList<>();
		}
		workers.add(worker);
	}

	public List<Worker> getWorkers() {
		return workers;
	}

	public void setWorkers(List<Worker> workers) {
		this.workers = workers;
	}

}
