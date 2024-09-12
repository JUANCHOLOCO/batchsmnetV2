package com.millicom.gtc.batchfit.dto.smnet;

public class TransactionDto {
	
	@Override
	public String toString() {
		return "<ws:transaccion>".concat(destination()).concat(date()).concat(idReference())
				.concat(name()).concat(origin()).concat(type())
				.concat("</ws:transaccion>");
	}
 
	private String destination() {
		return "<xsd:destino>" + "HTD" + "</xsd:destino>";
	}
 
	private String date() {
		return "<xsd:fecha>" + "" + "</xsd:fecha>";
	}
	
	private String idReference() {
		return "<xsd:idReferencia>" + "1" + "</xsd:idReferencia>";
	}
	
	private String name() {
		return "<xsd:nombre>" + "ConsultarPrueba" + "</xsd:nombre>";
	}
	
	private String origin() {
		return "<xsd:origen>" + "CLICK" + "</xsd:origen>";
	}
	
	private String type() {
		return "<xsd:tipo>" + "RESPUESTA" + "</xsd:tipo>";
	}

}
