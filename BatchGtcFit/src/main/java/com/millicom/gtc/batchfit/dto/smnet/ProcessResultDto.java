package com.millicom.gtc.batchfit.dto.smnet;

public class ProcessResultDto {
    private String consolidatedResponse;
    private String htmlCdata;

    public ProcessResultDto(String consolidatedResponse, String htmlCdata) {
        this.consolidatedResponse = consolidatedResponse;
        this.htmlCdata = htmlCdata;
    }

    public String getConsolidatedResponse() {
        return consolidatedResponse;
    }

    public String getHtmlCdata() {
        return htmlCdata;
    }
}
