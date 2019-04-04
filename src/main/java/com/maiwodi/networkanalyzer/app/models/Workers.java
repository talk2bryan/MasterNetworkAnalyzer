package com.maiwodi.networkanalyzer.app.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
