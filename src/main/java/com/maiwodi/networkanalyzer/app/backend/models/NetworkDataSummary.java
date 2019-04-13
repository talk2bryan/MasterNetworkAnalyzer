package com.maiwodi.networkanalyzer.app.backend.models;

import java.util.List;

public class NetworkDataSummary {

	private double avgDownloadSpeed;
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
		double ttlAvgDownloadSpeed = 0.0;

		for (NetworkData n : networkDatas) {
			ttlRssiValue += n.getRssiValue();
			ttlSpeedInMbps += n.getSpeedInMbps();
			ttlAvgDownloadSpeed += n.getDownloadSpeed();
		}
		avgRssiValue = ttlRssiValue / networkDatas.size();
		avgSpeedInMbps = ttlSpeedInMbps / networkDatas.size();
		avgSpeedInMbps = ttlAvgDownloadSpeed / networkDatas.size();
	}

	@Override
	public String toString() {
		return "NetworkDataSummary [avgDownloadSpeed=" + avgDownloadSpeed + ", avgRssiValue=" + avgRssiValue
				+ ", avgSpeedInMbps=" + avgSpeedInMbps + "]";
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
