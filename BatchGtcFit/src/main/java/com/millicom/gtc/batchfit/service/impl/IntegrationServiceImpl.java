package com.millicom.gtc.batchfit.service.impl;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.millicom.gtc.batchfit.dto.smnet.MessageSalesForceDto;
import com.millicom.gtc.batchfit.dto.smnet.SoapEnvelope;
import com.millicom.gtc.batchfit.dto.smnet.TestResponseDto;
import com.millicom.gtc.batchfit.dto.smnet.UpdateStatusResponseDto;
import com.millicom.gtc.batchfit.service.IntegrationService;
import com.millicom.gtc.batchfit.util.CreateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.ParameterizedTypeReference;

import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

@Service
public class IntegrationServiceImpl implements IntegrationService{
	
	@Value("${gtcfit.url}")
    private String url = "http://10.159.167.16:8080/smServices/services/SmNET.SmNETHttpSoap12Endpoint/";
	
	
	@Value("${gtcfit.urlaction}")
    private String urlaction = "urn:consultarPrueba";
	
	private static final Logger logger = LoggerFactory.getLogger(IntegrationServiceImpl.class);
	
	@Override
	public SoapEnvelope sendRequestedSmnet(String id) {
		
        
		CreateRequest create = new CreateRequest();
		SoapEnvelope response = null;
		try {
			String request = create.createdRequestSmnet(id);
			logger.info("[IntegrationServiceImpl][sendRequestedSmnet]-request= "+request);
			response = deserializeXML(invokeSmnet(request));
			logger.info("[IMPRIMITEESTO] " + response.getBody().getResponse().getReturnValue().getInformacionPrueba().getIdPrueba());
			logger.info("[IntegrationServiceImpl][sendRequestedSmnet]-response= "+response);
		} catch (Exception e) {
			logger.info("[IntegrationServiceImpl][sendRequestedSmnet]-Exception= "+e.getMessage());
			e.printStackTrace();
		}
		
		return response;
	}

	private String invokeSmnet(String Payload) {
		
		try {
			//logger.info("[IntegrationServiceImpl][invokeSmnet]-UrlSmnet="+url);
			//logger.info("[IntegrationServiceImpl][invokeSmnet]-soapAction="+urlaction);
			
		} catch (Exception e) {
			//logger.info("[IntegrationServiceImpl][invokeSmnet]-Exception= "+e.getMessage());
			e.printStackTrace();
		}
		return invokeService(createRequestSmnetEnvelope(Payload));
	}
	
	private String createRequestSmnetEnvelope(String payload) {
		return "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:ws=\"http://ws.smnet.ospinternational.com\" xmlns:xsd=\"http://parametros.ws.smnet.ospinternational.com/xsd\">\r\n"
				+ "   <soap:Header/>\r\n" + "   <soap:Body>\r\n" + payload + "   </soap:Body>\r\n"
				+ "</soap:Envelope>";
	}
	
	private String invokeService(String request) {
        
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/soap+xml; charset=utf-8");
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> entity = new HttpEntity<>(request, headers);
		String result = null;

	
		try {
			logger.info("[IntegrationServiceImpl][invokeService]-RequestGtcFit= "+request);
			logger.info("[IntegrationServiceImpl][invokeService]-url= "+url);
			result = restTemplate.postForObject(url, entity, String.class);
			logger.info("[IntegrationServiceImpl][invokeService]-result= "+result);		
			
		} catch (RestClientException e) {
			logger.info("[IntegrationServiceImpl][invokeService]-RestClientException= "+e.getMessage());
			
			e.printStackTrace();
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("[IntegrationServiceImpl][invokeService]-Exception= "+e.getMessage());
			logger.error("Error serializing object to JSON", e);
		}
		
		
		
		return result;
	}
    public static SoapEnvelope deserializeXML(String xmlResponse) throws JAXBException {
    
        JAXBContext jaxbContext = JAXBContext.newInstance(SoapEnvelope.class);    
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        SoapEnvelope soapEnvelope = (SoapEnvelope) unmarshaller.unmarshal(new StringReader(xmlResponse));
        
        return soapEnvelope;  
    }
   
    @Override
	public String processMessage(MessageSalesForceDto message) {
		
		CreateRequest create = new CreateRequest();
		try {
				logger.info("[SoapServiceImpl][processMessage] "+message.getCallId());
				TestResponseDto msg = create.createdMesssageRequest(message);
				//log.generarArchivo("[SoapServiceImpl] "+msg.getMessage());
				UpdateStatusResponseDto response = sendMsgTest(msg);
				logger.info("[SoapServiceImpl] "+response.code);
				/*
				 * if(response.getCode()!=ConstantValues.COD200) {
				 * responseMessage.setCode(response.getCode());
				 * responseMessage.setStatus(ConstantValues.ERROR);
				 * responseMessage.setMessage(response.getMessage()); } else {
				 * responseMessage.setCode(response.getCode());
				 * responseMessage.setStatus(ConstantValues.SUCESS);
				 * responseMessage.setMessage(response.getMessage()); }
				 */
	        
		} catch (Exception e) {
			
			/*
			 * responseMessage.setCode(ConstantValues.COD500);
			 * responseMessage.setStatus(ConstantValues.ERROR);
			 * responseMessage.setMessage(e.getMessage());
			 */
		}
		
        return "Melo";
	}
    
 
	public UpdateStatusResponseDto sendMsgTest(TestResponseDto payload) throws Exception {
		
		//String url = this.env.getRequiredProperty("url.send.message.test");
		//JsonResponse url = functions.getDataToJsonByValues(ConstantValues.CREDENTIALS_SOAP, ConstantValues.URLBOTRESPONSE);
		//JsonResponse operation = functions.getDataToJsonByValues(ConstantValues.CREDENTIALS_SOAP, ConstantValues.OPERATION);
		String url = "http://54.80.84.169:8082/responseservice/api/millicom/sf/TestAndDiagnosis/update";
		String operation = "co_b2c";
		
		//Gson g = new Gson();
		UpdateStatusResponseDto response = new UpdateStatusResponseDto();
		
		try {
			//log.generarArchivo("[CllClaseLogicaLocalImpl][sendMsgTest]-urlsendMsgTest= "+url);
			RestTemplate restTemplate = new RestTemplate();
			CloseableHttpClient httpClient = HttpClients.createDefault();
			restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
			
			Charset utf8 = StandardCharsets.UTF_8;
			MediaType mediaType = new MediaType("application", "json", utf8);
			HttpHeaders headers = new HttpHeaders();
			headers.add("X-TENANT-ID", operation);
			headers.setContentType(mediaType);
			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(payload);
			//String json = functions.dtoToJson(payload);
			//log.generarArchivo("[CllClaseLogicaLocalImpl][sendMsgTest]- request= "+json);
			
			HttpEntity<String> entity = new HttpEntity<>(json, headers);
			ResponseEntity<String> orderResponse = restTemplate.exchange(url, HttpMethod.PATCH, entity,new ParameterizedTypeReference<String>() {});
			logger.info("[CllClaseLogicaLocalImpl][sendMsgTest]-orderResponse= "+orderResponse.getBody());
			if (orderResponse.getStatusCode() == HttpStatus.OK) {
				response.setCode("200");
				response.setMessage("Mensaje enviado");
				response.setSuccess(true);
				response.setData(orderResponse.getBody());
			}else {
				response.setCode("400");
				response.setMessage("Fallo el envio del mensaje");
				response.setSuccess(false);
				response.setData(orderResponse);
			}
			
		} catch (Exception e) {
			logger.info("[CllClaseLogicaLocalImpl][sendMsgTest]-Exception= "+e.getMessage());
		}	
		return response;
	}
      

}
