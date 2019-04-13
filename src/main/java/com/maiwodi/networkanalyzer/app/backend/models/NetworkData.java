package com.maiwodi.networkanalyzer.app.backend.models;

import java.io.Serializable;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class NetworkData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -420440970134215957L;

	private double downloadSpeed;
	private int rssiValue;
	private int speedInMbps;
	private String timeStamp;

	public NetworkData() {
		super();
	}

	public NetworkData(double downloadSpeed, int rssiValue, int speedInMbps, String timeStamp) {
		super();
		this.downloadSpeed = downloadSpeed;
		this.rssiValue = rssiValue;
		this.speedInMbps = speedInMbps;
		this.timeStamp = timeStamp;
	}

	public double getDownloadSpeed() {
		return downloadSpeed;
	}

	public void setDownloadSpeed(double downloadSpeed) {
		this.downloadSpeed = downloadSpeed;
	}

	public int getRssiValue() {
		return rssiValue;
	}

	public void setRssiValue(int rssiValue) {
		this.rssiValue = rssiValue;
	}

	public int getSpeedInMbps() {
		return speedInMbps;
	}

	public void setSpeedInMbps(int speedInMbps) {
		this.speedInMbps = speedInMbps;
	}

	@JsonIgnore
	public Date getConvertedTimestamp() {
		DateTime date = new DateTime(Long.valueOf(timeStamp), DateTimeZone.UTC);

		return date.toDate();

	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

}
