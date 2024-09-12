package com.millicom.gtc.batchfit.service.impl;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.millicom.gtc.batchfit.dto.smnet.SoapEnvelope;
import com.millicom.gtc.batchfit.service.IntegrationService;
import com.millicom.gtc.batchfit.util.CreateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.StringReader;

@Service
public class IntegrationServiceImpl implements IntegrationService{
	
	@Value("${gtcfit.url}")
    private String url = "http://10.159.167.16:8080/smServices/services/SmNET.SmNETHttpSoap12Endpoint/";
	
	
	@Value("${gtcfit.urlaction}")
    private String urlaction = "urn:consultarPrueba";
	
	private static final Logger logger = LoggerFactory.getLogger(IntegrationServiceImpl.class);
	
	@Override
	public String sendRequestedSmnet(String id) {
		
        
		CreateRequest create = new CreateRequest();
		String response = null;
		try {
			String request = create.createdRequestSmnet(id);
			logger.info("[IntegrationServiceImpl][sendRequestedSmnet]-request= "+request);
			response = invokeSmnet(request);
			logger.info("[IntegrationServiceImpl][sendRequestedSmnet]-response= "+response);
		} catch (Exception e) {
			logger.info("[IntegrationServiceImpl][sendRequestedSmnet]-Exception= "+e.getMessage());
			e.printStackTrace();
		}
		
		return response;
	}

	private String invokeSmnet(String Payload) {
		
		try {
			logger.info("[IntegrationServiceImpl][invokeSmnet]-UrlSmnet="+url);
			logger.info("[IntegrationServiceImpl][invokeSmnet]-soapAction="+urlaction);
			
		} catch (Exception e) {
			logger.info("[IntegrationServiceImpl][invokeSmnet]-Exception= "+e.getMessage());
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
		SoapEnvelope pruebaIntegrada = null;
		ObjectMapper objectMapper = new ObjectMapper();
	
		try {
			logger.info("[IntegrationServiceImpl][invokeService]-RequestGtcFit= "+request);
			logger.info("[IntegrationServiceImpl][invokeService]-url= "+url);
			result = restTemplate.postForObject(url, entity, String.class);
			logger.info("[IntegrationServiceImpl][invokeService]-result= "+result);
			pruebaIntegrada = deserializeXML(result);
			  String json = objectMapper.writeValueAsString(pruebaIntegrada);
			    logger.info("[IMPRIMITEESTO] " + json);
			
			
			
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
        // Crear el contexto JAXB con la clase raíz (SoapEnvelope)
        JAXBContext jaxbContext = JAXBContext.newInstance(SoapEnvelope.class);
        
        // Crear un deserializador (Unmarshaller)
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        
        // Deserializar el String XML a un objeto SoapEnvelope
        SoapEnvelope soapEnvelope = (SoapEnvelope) unmarshaller.unmarshal(new StringReader(xmlResponse));
        
        return soapEnvelope;  
    }
   
 

}
