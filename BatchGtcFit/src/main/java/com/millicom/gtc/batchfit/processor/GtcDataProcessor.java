package com.millicom.gtc.batchfit.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.millicom.gtc.batchfit.dto.smnet.*;
import com.millicom.gtc.batchfit.entity.TestPlan;
import com.millicom.gtc.batchfit.service.IntegrationService;
import com.millicom.gtc.batchfit.service.impl.IntegrationServiceImpl;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.millicom.gtc.batchfit.dto.smnet.ProcessResultDto;
import org.springframework.jdbc.core.JdbcTemplate;

@Component
public class GtcDataProcessor implements ItemProcessor<TestPlan, TestPlanUpdateDto> {

    private static final Logger logger = LoggerFactory.getLogger(GtcDataProcessor.class);
  
	 private final DataSource dataSource;
	    // Inyección del DataSource en el constructor
	    @Autowired
	    public GtcDataProcessor(DataSource dataSource) {
	        this.dataSource = dataSource;
	    }

    @Override
    public TestPlanUpdateDto process(TestPlan item) {
    	 IntegrationService service = new IntegrationServiceImpl();
        SoapEnvelope response= null;
        PruebaIntegrada prueba;
        boolean finalizoPruebaSMNET = false;
        int maxAttempts = 3;
        int attempt = 0;
        String id = item.diagnosticId();
        String newStatus = null;

        logger.info("[GtcDataProcessor][process] Status: " + item.status());
        logger.info("[GtcDataProcessor][process] ID: " + id);

        while (attempt < maxAttempts && !finalizoPruebaSMNET) {
            attempt++;
            logger.info("[GtcDataProcessor][process] Attempt: " + attempt);

            response = service.sendRequestedSmnet(id);
            prueba = response.getBody().getResponse().getReturnValue();

            String estado = prueba.getInformacionPrueba().getEstado();
            if ("FINALIZADA".equals(estado) || "ERROR".equals(estado)) {
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
        TestResultResponse(response);

        if (!finalizoPruebaSMNET) {
            logger.info("[GtcDataProcessor][process] Time out. No se encontró el estado 'FINALIZADA' o 'ERROR' después de 3 intentos.");
            newStatus = "TIME_OUT";
        }

        return new TestPlanUpdateDto(id, newStatus);
    }

    public void TestResultResponse(SoapEnvelope testResult) {
        logger.info("[DataProcessor][process] TestResultResponse: entra al metodo");
	IntegrationService service = new IntegrationServiceImpl();


        String htmlTableFooter = "</table></body></html>";
        String actionStatus = "ERROR";
        String htmlCDATA = "<![CDATA[<h2>NoData</h2>]]>";
        PruebaIntegrada prueba = testResult.getBody().getResponse().getReturnValue();

        if (prueba != null && prueba.getInformacionPrueba() != null) {
            htmlCDATA = "<![CDATA[<h2>Check Parameters: " + prueba.getInformacionPrueba().getDiagnostico() + "</h2>]]>";

            if ("TIMEOUT".equals(prueba.getInformacionPrueba().getEstado())) {
                actionStatus = "TIMEOUT";
                htmlCDATA = "<![CDATA[<h2>Timeout expired!!</h2>]]>";
               } 
 
            else if ("FINALIZADA".equals(prueba.getInformacionPrueba().getEstado())) {
            	  logger.info("[DataProcessor][process] TestResultResponse: entra al metodo al elseIF");
                actionStatus = "FINALIZADA";
                processUnitaryTests(prueba,actionStatus);
            }
        }
    }

    private void processUnitaryTests(PruebaIntegrada prueba, String status) {
    	 IntegrationService service = new IntegrationServiceImpl();
         HFCResultProcessor hfcResultProcessor = new HFCResultProcessor(dataSource); 
        SectionOssDto sectionOss = null;
        List<PruebaUnitaria> listaPruebasUnitarias = prueba.getPruebasUnitarias().getPruebaUnitaria();
        ProcessResultDto result = null;

        if (listaPruebasUnitarias != null && !listaPruebasUnitarias.isEmpty()) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                String json = objectMapper.writeValueAsString(listaPruebasUnitarias);
                logger.info("[DataProcessor][process] listaPruebasUnitarias: " + json);
            } catch (Exception e) {
                e.printStackTrace();
            }

            for (PruebaUnitaria unitaryTest : listaPruebasUnitarias) {
                List<Resultado> resultados = unitaryTest.getResultados().getResultado();
                if (resultados != null && !resultados.isEmpty()) {
                    try {
                        String json = objectMapper.writeValueAsString(resultados);
                        logger.info("[DataProcessor][process] Resultados: " + json);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    for (Resultado testResultItem : resultados) {
                        if ("OSS".equals(testResultItem.getCategoria())) {
                            String xmlData = testResultItem.getDatos();
                            try {
                            	 logger.info("[DataProcessor][process] TestResultResponse: entra al parserado del xml");
                                sectionOss = XMLParserUtility.parseXML(xmlData, SectionOssDto.class);
                                String tecnologia = sectionOss.getOssPortafolioTecnologia();
                                String json = objectMapper.writeValueAsString(sectionOss);
                                logger.info("[DataProcessor][process] Resultados: " + json);
                                logger.info("[DataProcessor][process] TestResultResponse: tecnologia"+tecnologia);
                                if("HFC".equals(tecnologia)){
                                    logger.info("[DataProcessor][process] TestResultResponse: entra al if de tecnologia");
                                	result= hfcResultProcessor.processResultsForHFC(sectionOss,resultados,status);
                                	logger.info("[DataProcessor][process] TestResultResponse: el result es"+result.getHtmlCdata());
                                	logger.info("[DataProcessor][process] TestResultResponse: el result es"+result.getConsolidatedResponse());
                                }
                            } catch (Exception e) {
                                logger.error("Error parsing XML: " + e.getMessage());
                            }
                        }
							MessageSalesForceDto msg = new MessageSalesForceDto();
					        msg.setCallId("");
					        msg.setUneAction1Details("");
					        service.processMessage(msg) ;  
                    }
                }
            }
        }
    }

}
