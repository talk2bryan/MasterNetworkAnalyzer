package com.maiwodi.networkanalyzer.app.backend.models;

public class MonteCarloResult {
	private Integer M;
	private Integer N;
	private Double executionTime;

	public MonteCarloResult() {
		super();
	}

	public MonteCarloResult(Integer m, Integer n, Double executionTime) {
		super();
		M = m;
		N = n;
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

	public Double getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(Double executionTime) {
		this.executionTime = executionTime;
	}
}
