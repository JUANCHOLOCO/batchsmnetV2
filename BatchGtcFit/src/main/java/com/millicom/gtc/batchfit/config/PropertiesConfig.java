package com.millicom.gtc.batchfit.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.millicom.gtc.batchfit.dto.properties.PropDto;

@Configuration
@PropertySource(value = "file:${HOME}/propertiesFit.properties", encoding = "UTF-8")

public class PropertiesConfig {

	@Bean(name = "propertiesGtc")
	@ConfigurationProperties(prefix = "gtcfit.archivo.props")
	public PropDto prop() {
		return new PropDto();
	}
	
}
