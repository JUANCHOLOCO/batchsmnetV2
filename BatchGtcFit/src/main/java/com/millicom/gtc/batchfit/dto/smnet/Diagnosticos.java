package com.millicom.gtc.batchfit.dto.smnet;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import java.util.List;

@XmlType(propOrder = { "diagnostico" })
public class Diagnosticos {

    private List<Diagnostico> diagnostico;

    @XmlElement(name = "diagnostico", namespace = "http://respuestas.ws.smnet.ospinternational.com/xsd")
    public List<Diagnostico> getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(List<Diagnostico> diagnostico) {
        this.diagnostico = diagnostico;
    }
}
