package com.millicom.gtc.batchfit.dto.smnet;


import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;


@XmlRootElement(name = "return", namespace = "http://ws.smnet.ospinternational.com")
@XmlType(propOrder = { "informacionPrueba", "parametrosPrueba", "pruebasUnitarias" })
public class PruebaIntegrada {

    private InformacionPrueba informacionPrueba;
    private ParametrosPrueba parametrosPrueba;
    private PruebasUnitarias pruebasUnitarias;

    @XmlElement(name = "informacionPrueba", namespace = "http://respuestas.ws.smnet.ospinternational.com/xsd")
    public InformacionPrueba getInformacionPrueba() {
        return informacionPrueba;
    }

    public void setInformacionPrueba(InformacionPrueba informacionPrueba) {
        this.informacionPrueba = informacionPrueba;
    }

    @XmlElement(name = "parametrosPrueba", namespace = "http://respuestas.ws.smnet.ospinternational.com/xsd")
    public ParametrosPrueba getParametrosPrueba() {
        return parametrosPrueba;
    }

    public void setParametrosPrueba(ParametrosPrueba parametrosPrueba) {
        this.parametrosPrueba = parametrosPrueba;
    }

    @XmlElement(name = "pruebasUnitarias", namespace = "http://respuestas.ws.smnet.ospinternational.com/xsd")
    public PruebasUnitarias getPruebasUnitarias() {
        return pruebasUnitarias;
    }

    public void setPruebasUnitarias(PruebasUnitarias pruebasUnitarias) {
        this.pruebasUnitarias = pruebasUnitarias;
    }
}
