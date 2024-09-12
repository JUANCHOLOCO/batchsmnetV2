package com.millicom.gtc.batchfit.dto.smnet;


public class IdDto {

	private String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return callId();
	}
	
	private String callId() {
		return (id != null) ? "<ns1:ID>" + id + "</ns1:ID>" : "";
	}
	
}
