package com.millicom.gtc.batchfit.dto.smnet;


import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(name = "ParametroID", namespace = "http://parametros.ws.smnet.ospinternational.com/xsd", propOrder = { "tipoID", "valorID" })
public class ParametroID {

    private String tipoID;
    private String valorID;

    @XmlElement(name = "tipoID", namespace = "http://parametros.ws.smnet.ospinternational.com/xsd")
    public String getTipoID() {
        return tipoID;
    }

    public void setTipoID(String tipoID) {
        this.tipoID = tipoID;
    }

    @XmlElement(name = "valorID", namespace = "http://parametros.ws.smnet.ospinternational.com/xsd")
    public String getValorID() {
        return valorID;
    }

    public void setValorID(String valorID) {
        this.valorID = valorID;
    }
}
