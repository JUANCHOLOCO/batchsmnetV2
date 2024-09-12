package com.millicom.gtc.batchfit.dto.smnet;


import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "response" })
public class SoapBody {

    private ConsultarPruebaResponse response;

    @XmlElement(name = "consultarPruebaResponse", namespace = "http://ws.smnet.ospinternational.com")
    public ConsultarPruebaResponse getResponse() {
        return response;
    }

    public void setResponse(ConsultarPruebaResponse response) {
        this.response = response;
    }
}
