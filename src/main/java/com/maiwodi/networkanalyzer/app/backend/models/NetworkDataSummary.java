package com.maiwodi.networkanalyzer.app.backend.models;

import java.util.List;

public class NetworkDataSummary {

	private double avgRssiValue;
	private double avgSpeedInMbps;

	public NetworkDataSummary() {
		super();
	}

	/**
	 * 
	 * @param networkDatas
	 */
	public void analysis(List<NetworkData> networkDatas) {
		double ttlRssiValue = 0.0;
		double ttlSpeedInMbps = 0.0;

		for (NetworkData n : networkDatas) {
			ttlRssiValue += n.getRssiValue();
			ttlSpeedInMbps += n.getSpeedInMbps();
		}
		avgRssiValue = ttlRssiValue / networkDatas.size();
		avgSpeedInMbps = ttlSpeedInMbps / networkDatas.size();
	}

	public double getAvgRssiValue() {
		return avgRssiValue;
	}

	public void setAvgRssiValue(double avgRssiValue) {
		this.avgRssiValue = avgRssiValue;
	}

	public double getAvgSpeedInMbps() {
		return avgSpeedInMbps;
	}

	public void setAvgSpeedInMbps(double avgSpeedInMbps) {
		this.avgSpeedInMbps = avgSpeedInMbps;
	}

}
