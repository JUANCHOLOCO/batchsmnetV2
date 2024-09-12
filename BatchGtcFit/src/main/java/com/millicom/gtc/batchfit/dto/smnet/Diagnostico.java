package com.millicom.gtc.batchfit.dto.smnet;


import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "accion", "categoria", "codigo", "descripcion" })
public class Diagnostico {

    private String accion;
    private String categoria;
    private String codigo;
    private String descripcion;

    @XmlElement(name = "accion", namespace = "http://detalle.respuestas.ws.smnet.ospinternational.com/xsd")
    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    @XmlElement(name = "categoria", namespace = "http://detalle.respuestas.ws.smnet.ospinternational.com/xsd")
    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @XmlElement(name = "codigo", namespace = "http://detalle.respuestas.ws.smnet.ospinternational.com/xsd")
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @XmlElement(name = "descripcion", namespace = "http://detalle.respuestas.ws.smnet.ospinternational.com/xsd")
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}

