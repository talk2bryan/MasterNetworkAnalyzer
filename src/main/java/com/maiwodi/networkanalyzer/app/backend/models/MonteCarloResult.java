package com.maiwodi.networkanalyzer.app.backend.models;

public class MonteCarloResult {
	private Integer M;
	private Integer N;
	private Double executionTime;
	private String deviceType;

	public MonteCarloResult() {
		super();
	}

	public MonteCarloResult(Integer m, Integer n, Double executionTime, String deviceType) {
		super();
		M = m;
		N = n;
		this.deviceType = deviceType;
		this.executionTime = executionTime;
	}

	public Integer getM() {
		return M;
	}

	public void setM(Integer m) {
		M = m;
	}

	public Integer getN() {
		return N;
	}

	public void setN(Integer n) {
		N = n;
	}
	
	public void setDeviceType(String d) {
		deviceType = d;
	}

	public Double getExecutionTime() {
		return executionTime;
	}
	
	public String getDeviceType() {
		return deviceType;
	}

	public void setExecutionTime(Double executionTime) {
		this.executionTime = executionTime;
	}
}
