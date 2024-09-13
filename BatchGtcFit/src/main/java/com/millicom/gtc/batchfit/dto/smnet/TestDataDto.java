package com.millicom.gtc.batchfit.dto.smnet;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TestDataDto implements Serializable{

private static final long serialVersionUID = 1L;
	
	@JsonProperty("CallID")
	public String CallID;
	@JsonProperty("UNETestAndDiagnoseID")
	public String UNETestAndDiagnoseID;
	@JsonProperty("UNEAction1Required")
	public boolean UNEAction1Required;
	@JsonProperty("UNEAction2Required")
	public boolean UNEAction2Required;
	@JsonProperty("UNEAction1Parameter")
	public String UNEAction1Parameter;
	@JsonProperty("UNEAction2Parameter")
	public String UNEAction2Parameter;
	@JsonProperty("UNEActionResult")
	public String UNEActionResult;
	@JsonProperty("UNEAction1Details")
	public String UNEAction1Details;
	@JsonProperty("UNEAction2Details")
	public String UNEAction2Details;
	@JsonProperty("UNEActivateTestAndDiagnoseResult")
	public String UNEActivateTestAndDiagnoseResult;
	@JsonProperty("UNEActivateTestAndDiagnoseResultDetails")
	public String UNEActivateTestAndDiagnoseResultDetails;
	public String getCallID() {
		return CallID;
	}
	public void setCallID(String callID) {
		CallID = callID;
	}
	public String getUNETestAndDiagnoseID() {
		return UNETestAndDiagnoseID;
	}
	public void setUNETestAndDiagnoseID(String uNETestAndDiagnoseID) {
		UNETestAndDiagnoseID = uNETestAndDiagnoseID;
	}
	public boolean isUNEAction1Required() {
		return UNEAction1Required;
	}
	public void setUNEAction1Required(boolean uNEAction1Required) {
		UNEAction1Required = uNEAction1Required;
	}
	public boolean isUNEAction2Required() {
		return UNEAction2Required;
	}
	public void setUNEAction2Required(boolean uNEAction2Required) {
		UNEAction2Required = uNEAction2Required;
	}
	public String getUNEAction1Parameter() {
		return UNEAction1Parameter;
	}
	public void setUNEAction1Parameter(String uNEAction1Parameter) {
		UNEAction1Parameter = uNEAction1Parameter;
	}
	public String getUNEAction2Parameter() {
		return UNEAction2Parameter;
	}
	public void setUNEAction2Parameter(String uNEAction2Parameter) {
		UNEAction2Parameter = uNEAction2Parameter;
	}
	public String getUNEActionResult() {
		return UNEActionResult;
	}
	public void setUNEActionResult(String uNEActionResult) {
		UNEActionResult = uNEActionResult;
	}
	public String getUNEAction1Details() {
		return UNEAction1Details;
	}
	public void setUNEAction1Details(String uNEAction1Details) {
		UNEAction1Details = uNEAction1Details;
	}
	public String getUNEAction2Details() {
		return UNEAction2Details;
	}
	public void setUNEAction2Details(String uNEAction2Details) {
		UNEAction2Details = uNEAction2Details;
	}
	public String getUNEActivateTestAndDiagnoseResult() {
		return UNEActivateTestAndDiagnoseResult;
	}
	public void setUNEActivateTestAndDiagnoseResult(String uNEActivateTestAndDiagnoseResult) {
		UNEActivateTestAndDiagnoseResult = uNEActivateTestAndDiagnoseResult;
	}
	public String getUNEActivateTestAndDiagnoseResultDetails() {
		return UNEActivateTestAndDiagnoseResultDetails;
	}
	public void setUNEActivateTestAndDiagnoseResultDetails(String uNEActivateTestAndDiagnoseResultDetails) {
		UNEActivateTestAndDiagnoseResultDetails = uNEActivateTestAndDiagnoseResultDetails;
	}
	
	
}
