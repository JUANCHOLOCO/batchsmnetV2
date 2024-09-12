package com.millicom.gtc.batchfit.dto.smnet;


import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "activo", "descripcionError", "diagnostico", "estado", "fechaFin", "fechaInicio", "idPrueba", "permiteReprueba", "sistemaOrigen", "usuarioOrigen" })
public class InformacionPrueba {

    private Boolean activo;
    private String descripcionError;
    private String diagnostico;
    private String estado;
    private String fechaFin;
    private String fechaInicio;
    private String idPrueba;
    private Boolean permiteReprueba;
    private String sistemaOrigen;
    private String usuarioOrigen;

    @XmlElement(name = "activo", namespace = "http://detalle.respuestas.ws.smnet.ospinternational.com/xsd")
    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    @XmlElement(name = "descripcionError", namespace = "http://detalle.respuestas.ws.smnet.ospinternational.com/xsd")
    public String getDescripcionError() {
        return descripcionError;
    }

    public void setDescripcionError(String descripcionError) {
        this.descripcionError = descripcionError;
    }

    @XmlElement(name = "diagnostico", namespace = "http://detalle.respuestas.ws.smnet.ospinternational.com/xsd")
    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    @XmlElement(name = "estado", namespace = "http://detalle.respuestas.ws.smnet.ospinternational.com/xsd")
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @XmlElement(name = "fechaFin", namespace = "http://detalle.respuestas.ws.smnet.ospinternational.com/xsd")
    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    @XmlElement(name = "fechaInicio", namespace = "http://detalle.respuestas.ws.smnet.ospinternational.com/xsd")
    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    @XmlElement(name = "idPrueba", namespace = "http://detalle.respuestas.ws.smnet.ospinternational.com/xsd")
    public String getIdPrueba() {
        return idPrueba;
    }

    public void setIdPrueba(String idPrueba) {
        this.idPrueba = idPrueba;
    }

    @XmlElement(name = "permiteReprueba", namespace = "http://detalle.respuestas.ws.smnet.ospinternational.com/xsd")
    public Boolean getPermiteReprueba() {
        return permiteReprueba;
    }

    public void setPermiteReprueba(Boolean permiteReprueba) {
        this.permiteReprueba = permiteReprueba;
    }

    @XmlElement(name = "sistemaOrigen", namespace = "http://detalle.respuestas.ws.smnet.ospinternational.com/xsd")
    public String getSistemaOrigen() {
        return sistemaOrigen;
    }

    public void setSistemaOrigen(String sistemaOrigen) {
        this.sistemaOrigen = sistemaOrigen;
    }

    @XmlElement(name = "usuarioOrigen", namespace = "http://detalle.respuestas.ws.smnet.ospinternational.com/xsd")
    public String getUsuarioOrigen() {
        return usuarioOrigen;
    }

    public void setUsuarioOrigen(String usuarioOrigen) {
        this.usuarioOrigen = usuarioOrigen;
    }
}
