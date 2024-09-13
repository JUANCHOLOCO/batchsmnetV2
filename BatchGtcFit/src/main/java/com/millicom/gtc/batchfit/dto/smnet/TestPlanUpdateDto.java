package com.millicom.gtc.batchfit.dto.smnet;

public class TestPlanUpdateDto {
    private String diagnosticId;
    private String newStatus;

    public TestPlanUpdateDto(String diagnosticId, String newStatus) {
        this.diagnosticId = diagnosticId;
        this.newStatus = newStatus;
    }

    public String getDiagnosticId() {
        return diagnosticId;
    }

    public String getNewStatus() {
        return newStatus;
    }
}

