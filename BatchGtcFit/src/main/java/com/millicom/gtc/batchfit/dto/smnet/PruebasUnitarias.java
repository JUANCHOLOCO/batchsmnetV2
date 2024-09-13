package com.millicom.gtc.batchfit.dto.smnet;



import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlType;
import java.util.List;

@XmlType(propOrder = { "pruebaUnitaria" })
public class PruebasUnitarias {
	@XmlElementWrapper(name = "pruebasUnitarias") 
    @XmlElement(name = "pruebaUnitaria", namespace = "http://respuestas.ws.smnet.ospinternational.com/xsd")
    private List<PruebaUnitaria> pruebaUnitaria;


    public List<PruebaUnitaria> getPruebaUnitaria() {
        return pruebaUnitaria;
    }

    public void setPruebaUnitaria(List<PruebaUnitaria> pruebaUnitaria) {
        this.pruebaUnitaria = pruebaUnitaria;
    }
}
