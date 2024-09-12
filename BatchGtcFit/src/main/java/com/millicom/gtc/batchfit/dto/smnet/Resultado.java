package com.millicom.gtc.batchfit.dto.smnet;



import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "categoria", "datos", "diagnosticos" })
public class Resultado {

    private String categoria;
    private String datos;
    private Diagnosticos diagnosticos;

    @XmlElement(name = "categoria", namespace = "http://detalle.respuestas.ws.smnet.ospinternational.com/xsd")
    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @XmlElement(name = "datos", namespace = "http://detalle.respuestas.ws.smnet.ospinternational.com/xsd")
    public String getDatos() {
        return datos;
    }

    public void setDatos(String datos) {
        this.datos = datos;
    }

    @XmlElement(name = "diagnosticos", namespace = "http://detalle.respuestas.ws.smnet.ospinternational.com/xsd")
    public Diagnosticos getDiagnosticos() {
        return diagnosticos;
    }

    public void setDiagnosticos(Diagnosticos diagnosticos) {
        this.diagnosticos = diagnosticos;
    }
}
