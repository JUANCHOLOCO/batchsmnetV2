package com.millicom.gtc.batchfit.dto.smnet;



import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "acciones", "informacionPrueba", "resultados" })
public class PruebaUnitaria {

    private Acciones acciones;
    private InformacionPrueba informacionPrueba;
    private Resultados resultados;

    @XmlElement(name = "acciones", namespace = "http://respuestas.ws.smnet.ospinternational.com/xsd")
    public Acciones getAcciones() {
        return acciones;
    }

    public void setAcciones(Acciones acciones) {
        this.acciones = acciones;
    }

    @XmlElement(name = "informacionPrueba", namespace = "http://respuestas.ws.smnet.ospinternational.com/xsd")
    public InformacionPrueba getInformacionPrueba() {
        return informacionPrueba;
    }

    public void setInformacionPrueba(InformacionPrueba informacionPrueba) {
        this.informacionPrueba = informacionPrueba;
    }

    @XmlElement(name = "resultados", namespace = "http://respuestas.ws.smnet.ospinternational.com/xsd")
    public Resultados getResultados() {
        return resultados;
    }

    public void setResultados(Resultados resultados) {
        this.resultados = resultados;
    }
}

