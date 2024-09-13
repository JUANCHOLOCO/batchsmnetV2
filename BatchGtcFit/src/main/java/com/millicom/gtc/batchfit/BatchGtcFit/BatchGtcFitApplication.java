package com.millicom.gtc.batchfit.BatchGtcFit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
//@ComponentScan(basePackages = "com.millicom.gtc.batchfit.processor")
public class BatchGtcFitApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatchGtcFitApplication.class, args);
	}

}
