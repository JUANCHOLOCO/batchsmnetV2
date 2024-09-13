package com.millicom.gtc.batchfit.dto.smnet;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TestResponseDto implements Serializable {

private static final long serialVersionUID = 1L;
	
	@JsonProperty("code")
	private String code;
	@JsonProperty("status")
	private String status;
	@JsonProperty("message")
	private String message;
	@JsonProperty("data")
	private TestDataDto data;
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public TestDataDto getData() {
		return data;
	}
	public void setData(TestDataDto data) {
		this.data = data;
	}	
}
