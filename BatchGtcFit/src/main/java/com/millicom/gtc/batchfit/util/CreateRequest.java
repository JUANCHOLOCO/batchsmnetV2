package com.millicom.gtc.batchfit.util;

import java.util.function.Supplier;
import org.springframework.stereotype.Component;
import com.millicom.gtc.batchfit.dto.smnet.SmnetRequestDto;

@Component
public class CreateRequest {
	
	public String createdRequestSmnet(String id)throws Exception {
		Supplier<SmnetRequestDto> req = SmnetRequestDto::new;
		SmnetRequestDto request = req.get();
		request.setId(id);
		return request.toString();
		
	}
}
