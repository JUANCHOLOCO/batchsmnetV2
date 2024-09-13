package com.millicom.gtc.batchfit.service;

import com.millicom.gtc.batchfit.dto.smnet.MessageSalesForceDto;
import com.millicom.gtc.batchfit.dto.smnet.SoapEnvelope;

public interface IntegrationService {

	public SoapEnvelope sendRequestedSmnet(String id);
	public String processMessage(MessageSalesForceDto message);
}
