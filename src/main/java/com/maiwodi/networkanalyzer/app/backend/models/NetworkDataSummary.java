package com.maiwodi.networkanalyzer.app.backend.models;

import java.util.ArrayList;
import java.util.List;

public class NetworkDataSummary {

	private double avgDownloadSpeed;
	private double avgRssiValue;
	private double avgSpeedInMbps;
//	private double standardDiviationDownloadSpeed;
//	private double maxDownloadSpeed;
//	private double minDownloadSpeed;
//	private double geoMinDownloadSpeed;
//	private double kurtosisDownloadSpeed;
//	private double medianDownloadSpeed;
//	private double variantDownloadSpeed;
//	private double populationVariantDownloadSpeed;
//	private double skewnessDownloadSpeed;

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
//		StandardDeviation standardDeviation = new StandardDeviation();

		List<Double> downloadSpeedList = new ArrayList<>();

		for (NetworkData n : networkDatas) {
			ttlRssiValue += n.getRssiValue();
			ttlSpeedInMbps += n.getSpeedInMbps();
			ttlAvgDownloadSpeed += n.getDownloadSpeed();
			downloadSpeedList.add(n.getDownloadSpeed());
		}

		avgRssiValue = ttlRssiValue / networkDatas.size();
		avgSpeedInMbps = ttlSpeedInMbps / networkDatas.size();
		avgDownloadSpeed = ttlAvgDownloadSpeed / networkDatas.size();

//		double[] downloadSpeedArray = downloadSpeedList.stream().mapToDouble(Double::doubleValue).toArray();
//		standardDiviationDownloadSpeed = standardDeviation.evaluate(downloadSpeedArray);
//
//		DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics(downloadSpeedArray);
//		maxDownloadSpeed = descriptiveStatistics.getMax();
//		minDownloadSpeed = descriptiveStatistics.getMin();
//		standardDiviationDownloadSpeed = descriptiveStatistics.getStandardDeviation();
//		geoMinDownloadSpeed = descriptiveStatistics.getGeometricMean();
//		kurtosisDownloadSpeed = descriptiveStatistics.getKurtosis();
//		medianDownloadSpeed = descriptiveStatistics.getPercentile(50);
//		variantDownloadSpeed = descriptiveStatistics.getVariance();
//		populationVariantDownloadSpeed = descriptiveStatistics.getPopulationVariance();
//		skewnessDownloadSpeed = descriptiveStatistics.getSkewness();

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

	public double getAvgDownloadSpeed() {
		return avgDownloadSpeed;
	}

	public void setAvgDownloadSpeed(double avgDownloadSpeed) {
		this.avgDownloadSpeed = avgDownloadSpeed;
	}

//	public double getStandardDiviationDownloadSpeed() {
//		return standardDiviationDownloadSpeed;
//	}
//
//	public void setStandardDiviationDownloadSpeed(double standardDiviationDownloadSpeed) {
//		this.standardDiviationDownloadSpeed = standardDiviationDownloadSpeed;
//	}
//
//	public double getMaxDownloadSpeed() {
//		return maxDownloadSpeed;
//	}
//
//	public void setMaxDownloadSpeed(double maxDownloadSpeed) {
//		this.maxDownloadSpeed = maxDownloadSpeed;
//	}
//
//	public double getMinDownloadSpeed() {
//		return minDownloadSpeed;
//	}
//
//	public void setMinDownloadSpeed(double minDownloadSpeed) {
//		this.minDownloadSpeed = minDownloadSpeed;
//	}
//
//	public double getGeoMinDownloadSpeed() {
//		return geoMinDownloadSpeed;
//	}
//
//	public void setGeoMinDownloadSpeed(double geoMinDownloadSpeed) {
//		this.geoMinDownloadSpeed = geoMinDownloadSpeed;
//	}
//
//	public double getKurtosisDownloadSpeed() {
//		return kurtosisDownloadSpeed;
//	}
//
//	public void setKurtosisDownloadSpeed(double kurtosisDownloadSpeed) {
//		this.kurtosisDownloadSpeed = kurtosisDownloadSpeed;
//	}
//
//	public double getMedianDownloadSpeed() {
//		return medianDownloadSpeed;
//	}
//
//	public void setMedianDownloadSpeed(double medianDownloadSpeed) {
//		this.medianDownloadSpeed = medianDownloadSpeed;
//	}
//
//	public double getVariantDownloadSpeed() {
//		return variantDownloadSpeed;
//	}
//
//	public void setVariantDownloadSpeed(double variantDownloadSpeed) {
//		this.variantDownloadSpeed = variantDownloadSpeed;
//	}
//
//	public double getPopulationVariantDownloadSpeed() {
//		return populationVariantDownloadSpeed;
//	}
//
//	public void setPopulationVariantDownloadSpeed(double populationVariantDownloadSpeed) {
//		this.populationVariantDownloadSpeed = populationVariantDownloadSpeed;
//	}
//
//	public double getSkewnessDownloadSpeed() {
//		return skewnessDownloadSpeed;
//	}
//
//	public void setSkewnessDownloadSpeed(double skewnessDownloadSpeed) {
//		this.skewnessDownloadSpeed = skewnessDownloadSpeed;
//	}

}
