package com.millicom.gtc.batchfit.dto.smnet;


import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import java.util.List;

@XmlType(propOrder = { "resultado" })
public class Resultados {

    private List<Resultado> resultado;

    @XmlElement(name = "resultado", namespace = "http://respuestas.ws.smnet.ospinternational.com/xsd")
    public List<Resultado> getResultado() {
        return resultado;
    }

    public void setResultado(List<Resultado> resultado) {
        this.resultado = resultado;
    }
}
