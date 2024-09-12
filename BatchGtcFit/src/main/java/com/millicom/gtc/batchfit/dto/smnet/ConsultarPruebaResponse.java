package com.millicom.gtc.batchfit.dto.smnet;


import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "returnValue" })
public class ConsultarPruebaResponse {

    private PruebaIntegrada returnValue;

    @XmlElement(name = "return", namespace = "http://ws.smnet.ospinternational.com")
    public PruebaIntegrada getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(PruebaIntegrada returnValue) {
        this.returnValue = returnValue;
    }
}

