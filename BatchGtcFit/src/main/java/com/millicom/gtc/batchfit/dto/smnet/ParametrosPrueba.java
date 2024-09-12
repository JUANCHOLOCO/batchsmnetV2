package com.millicom.gtc.batchfit.dto.smnet;



import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "atributos", "corregirServicio", "idPruebaRelacionada", "parametroID", "tipoPrueba", "usuario" })
public class ParametrosPrueba {

    private String atributos;
    private Boolean corregirServicio;
    private String idPruebaRelacionada;
    private ParametroID parametroID;
    private String tipoPrueba;
    private String usuario;

    @XmlElement(name = "atributos", namespace = "http://parametros.ws.smnet.ospinternational.com/xsd", nillable = true)
    public String getAtributos() {
        return atributos;
    }

    public void setAtributos(String atributos) {
        this.atributos = atributos;
    }

    @XmlElement(name = "corregirServicio", namespace = "http://parametros.ws.smnet.ospinternational.com/xsd")
    public Boolean getCorregirServicio() {
        return corregirServicio;
    }

    public void setCorregirServicio(Boolean corregirServicio) {
        this.corregirServicio = corregirServicio;
    }

    @XmlElement(name = "idPruebaRelacionada", namespace = "http://parametros.ws.smnet.ospinternational.com/xsd", nillable = true)
    public String getIdPruebaRelacionada() {
        return idPruebaRelacionada;
    }

    public void setIdPruebaRelacionada(String idPruebaRelacionada) {
        this.idPruebaRelacionada = idPruebaRelacionada;
    }

    @XmlElement(name = "parametroID", namespace = "http://parametros.ws.smnet.ospinternational.com/xsd")
    public ParametroID getParametroID() {
        return parametroID;
    }

    public void setParametroID(ParametroID parametroID) {
        this.parametroID = parametroID;
    }

    @XmlElement(name = "tipoPrueba", namespace = "http://parametros.ws.smnet.ospinternational.com/xsd")
    public String getTipoPrueba() {
        return tipoPrueba;
    }

    public void setTipoPrueba(String tipoPrueba) {
        this.tipoPrueba = tipoPrueba;
    }

    @XmlElement(name = "usuario", namespace = "http://parametros.ws.smnet.ospinternational.com/xsd")
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
