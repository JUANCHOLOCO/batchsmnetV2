package com.millicom.gtc.batchfit.util;

import java.util.function.Supplier;
import org.springframework.stereotype.Component;

import com.millicom.gtc.batchfit.dto.smnet.MessageSalesForceDto;
import com.millicom.gtc.batchfit.dto.smnet.SmnetRequestDto;
import com.millicom.gtc.batchfit.dto.smnet.SoapEnvelope;
import com.millicom.gtc.batchfit.dto.smnet.TestDataDto;
import com.millicom.gtc.batchfit.dto.smnet.TestResponseDto;

@Component
public class CreateRequest {
	
	public String createdRequestSmnet(String id)throws Exception {
		Supplier<SmnetRequestDto> req = SmnetRequestDto::new;
		SmnetRequestDto request = req.get();
		request.setId(id);
		return request.toString();
		
	}
	
	public TestResponseDto createdMesssageRequest(MessageSalesForceDto message)throws Exception {
			
			TestResponseDto request = new TestResponseDto();
			TestDataDto data = new TestDataDto();
			//log.generarArchivo("[CreateRequestMessageTest][createdMesssageRequest]-Inicia creacion request"); 
			request.setCode("");
			request.setMessage("");
			request.setStatus("");
			data.setCallID(message.getCallId());
			data.setUNETestAndDiagnoseID(message.getUneTestAndDiagnoseId());
			data.setUNEAction1Details(message.getUneAction1Details());
			data.setUNEAction1Parameter(message.getUneAction1Parameter());
			data.setUNEAction1Required(message.isUneAction1Required());
			data.setUNEAction2Details(message.getUneAction2Details());
			data.setUNEAction2Parameter(message.getUneAction2Parameter());
			data.setUNEAction2Required(message.isUneAction2Required());
			data.setUNEActionResult(message.getUneActionResult());
			data.setUNEActivateTestAndDiagnoseResult(message.getUneActivateTestAndDiagnoseResult());
			data.setUNEActivateTestAndDiagnoseResultDetails(message.getUneActivateTestAndDiagnoseResultDetails());
			request.setData(data);
			//log.generarArchivo("[CreateRequestMessageTest][createdMesssageRequest]-fin creacion request");
			return request;
				  
	}
}
