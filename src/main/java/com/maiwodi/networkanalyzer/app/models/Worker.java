package com.maiwodi.networkanalyzer.app.models;

import java.io.Serializable;

public class Worker implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5287967311114543682L;
	
	private String workerIP;

	public Worker() {
		super();
	}

	public Worker(String workerIP) {
		super();
		this.workerIP = workerIP;
	}

	public String getWorkerIP() {
		return workerIP;
	}

	public void setWorkerIP(String workerIP) {
		this.workerIP = workerIP;
	}

}
