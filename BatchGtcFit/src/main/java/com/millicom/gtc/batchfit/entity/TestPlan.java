package com.millicom.gtc.batchfit.entity;

import java.time.OffsetDateTime;
import java.util.UUID;

public record TestPlan(
		int id,
	    String traceId,
	    String testType,
	    boolean serviceRepair,
	    String relatedTestId,
	    String serviceId,
	    String diagnosticId,
	    String workOrderId,
	    String nameParameter,
	    String valueParameter,
	    int responseSmnetId,
	    OffsetDateTime createDate,
	    String status) {

}
