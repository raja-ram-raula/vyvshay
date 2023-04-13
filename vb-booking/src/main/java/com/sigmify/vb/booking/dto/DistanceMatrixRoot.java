package com.sigmify.vb.booking.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DistanceMatrixRoot implements Serializable {

	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("Server") 
    public String server;
    public String version;
    public DistanceMatrixResult results;
    public int responseCode;
}
