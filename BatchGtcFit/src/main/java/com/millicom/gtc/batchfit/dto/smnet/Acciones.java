package com.millicom.gtc.batchfit.dto.smnet;


import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlAttribute;

@XmlType(propOrder = { "accion" })
public class Acciones {

    private String accion;
    private Boolean isNil;

    @XmlElement(name = "accion", namespace = "http://respuestas.ws.smnet.ospinternational.com/xsd")
    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    @XmlAttribute(name = "xsi:nil", namespace = "http://www.w3.org/2001/XMLSchema-instance")
    public Boolean getIsNil() {
        return isNil;
    }

    public void setIsNil(Boolean isNil) {
        this.isNil = isNil;
    }
}

