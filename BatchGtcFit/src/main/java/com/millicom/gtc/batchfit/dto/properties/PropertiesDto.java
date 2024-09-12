package com.millicom.gtc.batchfit.dto.properties;

import java.util.Map;

import com.millicom.gtc.batchfit.enums.IdentificadorPropertiesEnum;


public class PropertiesDto {

	private Map<IdentificadorPropertiesEnum, PropDto> archivo;

	public Map<IdentificadorPropertiesEnum, PropDto> getArchivo() {
		return archivo;
	}
	
	
}
