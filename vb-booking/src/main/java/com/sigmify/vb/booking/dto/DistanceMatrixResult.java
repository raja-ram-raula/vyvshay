package com.sigmify.vb.booking.dto;

import java.io.Serializable;
import java.util.List;

public class DistanceMatrixResult implements Serializable {
	
	public List<List<Double>> distances;
    public String code;
    public List<List<Double>> getDistances() {
		return distances;
	}
	public void setDistances(List<List<Double>> distances) {
		this.distances = distances;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public List<List<Double>> getDurations() {
		return durations;
	}
	public void setDurations(List<List<Double>> durations) {
		this.durations = durations;
	}
	public List<List<Double>> durations;
 
}
