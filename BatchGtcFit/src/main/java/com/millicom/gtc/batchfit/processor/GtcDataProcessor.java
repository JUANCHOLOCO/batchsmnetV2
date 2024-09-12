package com.millicom.gtc.batchfit.processor;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.UUID;

import org.springframework.batch.item.ItemProcessor;

import com.millicom.gtc.batchfit.entity.TestPlan;
import com.millicom.gtc.batchfit.service.IntegrationService;
import com.millicom.gtc.batchfit.service.impl.IntegrationServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GtcDataProcessor implements ItemProcessor<TestPlan, String> {
	
	private static final Logger logger = LoggerFactory.getLogger(GtcDataProcessor.class);
	@Override
	public String process(TestPlan item) {
			
		IntegrationService service = new IntegrationServiceImpl();
		String response = null;
		logger.info("[GtcDataProcessor][process] Status " + item.status());
		String id = item.diagnosticId();
		logger.info("[GtcDataProcessor][process] id " + id);
		response = service.sendRequestedSmnet(id);
		/*
		 * TestPlan testPlan = new TestPlan( 1, UUID.randomUUID(), "TypeD", true,
		 * "RT123", "SVC456", "DIAG789", "WO1234", "Param1", "Value1", 1001,
		 * OffsetDateTime.now(), "COMPLETED" );
		 */
	      
	      
		return id;
	}
}
