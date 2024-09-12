package com.millicom.gtc.batchfit.dto.smnet;

import java.util.function.Supplier;

public class SmnetRequestDto {
	
	private String Id;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	
	@Override
	public String toString() {
		return "<ws:consultarPrueba>\r\n"
		  		+         trans()
		  		+ "         <ws:idPrueba>"+Id+"</ws:idPrueba>\r\n"
		  		+ "         <ws:idPruebaUnitaria>"+""+"</ws:idPruebaUnitaria>\r\n"
		  		+ "      </ws:consultarPrueba>\r\n";
	}
	
	private String trans() {
		Supplier<TransactionDto> req = TransactionDto::new;
		TransactionDto request = req.get();
		return request.toString();
	}
}
