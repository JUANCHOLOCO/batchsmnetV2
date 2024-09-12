package com.millicom.gtc.batchfit.enums;

public enum IdentificadorPropertiesEnum implements Enumerador{

	PROPS("PROPS","props");
	
	String value;
	String etiqueta;
	
	private IdentificadorPropertiesEnum(String value, String etiqueta) {
		
		this.value = value;
		this.etiqueta = etiqueta;
	}

	@Override
	public String getEtiqueta() {
		return etiqueta;
	}

	@Override
	public String getValue() {
		return value;
	}
	
	public static IdentificadorPropertiesEnum obteenrEnumXCodigo(String codigo) {
		for(IdentificadorPropertiesEnum estadoEnum : IdentificadorPropertiesEnum.values()) {
			if (estadoEnum.getValue().equals(codigo)) {
				return estadoEnum;
			}
		}
		throw new IllegalArgumentException("No se encontro una URL con codigo["+ codigo +"].");
	}
	
	@Override
	public String toString() {
		return etiqueta;
	}
}
