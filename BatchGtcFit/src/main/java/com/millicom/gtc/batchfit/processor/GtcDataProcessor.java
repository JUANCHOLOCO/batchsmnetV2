package com.millicom.gtc.batchfit.processor;

import org.springframework.batch.item.ItemProcessor;

import com.millicom.gtc.batchfit.dto.smnet.SoapEnvelope;
import com.millicom.gtc.batchfit.dto.smnet.TestPlanUpdateDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.millicom.gtc.batchfit.dto.smnet.PruebaIntegrada;
import com.millicom.gtc.batchfit.dto.smnet.PruebaUnitaria;
import com.millicom.gtc.batchfit.dto.smnet.Resultado;
import com.millicom.gtc.batchfit.dto.smnet.SectionOssDto;
import com.millicom.gtc.batchfit.entity.TestPlan;
import com.millicom.gtc.batchfit.service.IntegrationService;
import com.millicom.gtc.batchfit.service.impl.IntegrationServiceImpl;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class GtcDataProcessor implements ItemProcessor<TestPlan, TestPlanUpdateDto> {

	private static final Logger logger = LoggerFactory.getLogger(GtcDataProcessor.class);

	@Override
	public TestPlanUpdateDto process(TestPlan item) {
		IntegrationService service = new IntegrationServiceImpl();
		SoapEnvelope response = null;
		PruebaIntegrada prueba = null;
		boolean finalizoPruebaSMNET = false;
		int maxAttempts = 3; 
		int attempt = 0; 
		String id = item.diagnosticId();
		String newStatus = null;

		logger.info("[GtcDataProcessor][process] Status " + item.status());
		logger.info("[GtcDataProcessor][process] id " + id);

		while (attempt < maxAttempts && !finalizoPruebaSMNET) {
			attempt++;
			logger.info("[GtcDataProcessor][process] Intento " + attempt);

			response = service.sendRequestedSmnet(id);
			prueba = response.getBody().getResponse().getReturnValue();

			String estado = prueba.getInformacionPrueba().getEstado();
			if (estado.equals("FINALIZADA") || estado.equals("ERROR")) {
				finalizoPruebaSMNET = true;
				newStatus = "COMPLETED";
				logger.info("[DataProcessor][process] Estado encontrado: " + estado + " en intento " + attempt);
			} else {
				logger.info("[DataProcessor][process] Estado no encontrado en intento " + attempt + ". Estado actual: " + estado);
			}

			if (!finalizoPruebaSMNET) {
				try {
					Thread.sleep(1000); 
				} catch (InterruptedException e) {
					logger.error("Error en la pausa entre intentos: " + e.getMessage());
					Thread.currentThread().interrupt();
				}
			}
		}

		if (!finalizoPruebaSMNET) {
			logger.info("[GtcDataProcessor][process] Time out. No se encontró el estado 'FINALIZADA' o 'ERROR' después de 3 intentos.");
			newStatus = "TIME_OUT";
		}

		return new TestPlanUpdateDto(id, newStatus);
	}
		public void TestResultResponse(SoapEnvelope testResult, String exception) {
			// HTML header for the creation of tables for Electrical Test result response for Click
			String htmlTableHeader = "<html><head><style type=\"text/css\">" +
					"table.tableizer-table {" +
					"font-size: 12px;" +
					"border: 1px solid #CCC; font-family: Arial, Helvetica, sans-serif;}" +

		            ".tableizer-table td {" +
		            "padding: 4px;" +
		            "margin: 3px;" +
		            "border: 1px solid #ccc;}" +

		            ".tableizer-table th {" +
		            "background-color: #FFFFFF;" +
		            "color: #000;" +
		            "font-weight: bold;}" +

		            ".tableizer-firstrow th {" +
		            "background-color: #000000;" +
		            "color: #FFF;" +
		            "font-weight: bold;}" +

		            ".tableizer-secondsrows th {" +
		            "background-color: #AFAFAF;" +
		            "color: #000;" +
		            "font-weight: bold;}" +

		            ".tableizer-secondsrows td {" +
		            "background-color: #FFFFFF;" +
		            "color: #000;" +
		            "font-weight: bold;}" +

		            "</style></head>" +
		            "<body><table style=\"width:100%\" class=\"tableizer-table\">" +
		            "<tr class=\"tableizer-firstrow\"><th colspan=\"7\">Electrical Test Result</th></tr>";

			// HTML footer for the creation of tables for Electrical Test result response for Click
			String htmlTableFooter = "</table></body></html>";
			String actionStatus = "ERROR";
			String htmlCDATA = "<![CDATA[<h2>NoData</h2>]]>";
			PruebaIntegrada prueba = testResult.getBody().getResponse().getReturnValue();

			if (testResult != null && prueba.getInformacionPrueba() != null) {
				htmlCDATA = "<![CDATA[<h2>Check Parameters: " + prueba.getInformacionPrueba().getDiagnostico() + "</h2>]]>";

				if (prueba.getInformacionPrueba().getEstado().equals("TIMEOUT")) {
					actionStatus = "TIMEOUT";
					htmlCDATA = "<![CDATA[<h2>Timeout expired!!</h2>]]>";
				} else if (prueba.getInformacionPrueba().getEstado().equals("FINALIZADO")) {
					actionStatus = "FINALIZADO";

					SectionOssDto sectionOss = null;
					//ElectricalTestSMNETTO electricalTest = null;

					// Filtering unitary tests
					List<PruebaUnitaria> listaPruebasUnitarias = prueba.getPruebasUnitarias().getPruebaUnitaria();
					if (listaPruebasUnitarias != null && !listaPruebasUnitarias.isEmpty()) {
					 	ObjectMapper objectMapper = new ObjectMapper();
			        	   try {
			                   // Aquí debes reemplazar `resultados` por tu objeto que quieres convertir a JSON
			                   String json = objectMapper.writeValueAsString(listaPruebasUnitarias);
			                   logger.info("[DataProcessor][process] listaPruebasUnitarias: " +json);
			               } catch (Exception e) {
			                   e.printStackTrace();
			               }
					    for (PruebaUnitaria unitaryTest : listaPruebasUnitarias) {

					        // Verificamos que getResultados() no sea null y que sea iterable
					        List<Resultado> resultados = unitaryTest.getResultados().getResultado();
					        if (resultados != null && !resultados.isEmpty()) {  // Verificamos que haya resultados
					        	ObjectMapper objectMapper = new ObjectMapper();
					        	   try {
					                   // Aquí debes reemplazar `resultados` por tu objeto que quieres convertir a JSON
					                   String json = objectMapper.writeValueAsString(resultados);
					                   logger.info("[DataProcessor][process] Resultados: " +json);
					               } catch (Exception e) {
					                   e.printStackTrace();
					               }
				        		
					            for (Resultado testResultItem : resultados) {
					            	
					                if (testResultItem.getCategoria() != null && testResultItem.getCategoria().equals("OSS")) {
					        			
					                }

					                if (sectionOss != null && sectionOss.getOssPortafolioTecnologia() != null &&
					                        (sectionOss.getOssPortafolioTecnologia().contains("REDCO") || sectionOss.getOssPortafolioTecnologia().contains("HFC"))) {

					                    if (testResultItem.getCategoria() != null && testResultItem.getCategoria().contains("ACCESS")) {
					                        // electricalTest = parseXML(ElectricalTestSMNETTO.class, testResultItem.getData());
					                    }
					                }
					            }
					        } else if (!exception.isEmpty()) {
						actionStatus = "ERROR";
						htmlCDATA = "<![CDATA[<h2>" + exception + "</h2>]]>";
					}

					//aca va el servicio yo me encargo de mandarl el resultado solo dejelo mapeado(htmlCDATA, actionNumber, actionStatus, actionRequest.getPetition().getTask().getCallID(), actionRequest.getPetition().getTask().getUNEPedido(), isSalesforce);
				}
			}
		}
	}
	}
	
}
	
