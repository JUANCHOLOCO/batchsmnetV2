package com.millicom.gtc.batchfit.processor;

import org.springframework.batch.item.ItemProcessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.millicom.gtc.batchfit.dto.smnet.*;
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

      
        String htmlTableHeader = "<html><head><style type=\"text/css\">" +
                "table.tableizer-table {font-size: 12px; border: 1px solid #CCC; font-family: Arial, Helvetica, sans-serif;}" +
                ".tableizer-table td {padding: 4px; margin: 3px; border: 1px solid #ccc;}" +
                ".tableizer-table th {background-color: #FFFFFF; color: #000; font-weight: bold;}" +
                ".tableizer-firstrow th {background-color: #000000; color: #FFF; font-weight: bold;}" +
                ".tableizer-secondsrows th {background-color: #AFAFAF; color: #000; font-weight: bold;}" +
                ".tableizer-secondsrows td {background-color: #FFFFFF; color: #000; font-weight: bold;}" +
                "</style></head><body><table style=\"width:100%\" class=\"tableizer-table\">" +
                "<tr class=\"tableizer-firstrow\"><th colspan=\"7\">Electrical Test Result</th></tr>";

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
 
            else if ("FINALIZADO".equals(prueba.getInformacionPrueba().getEstado())) {
            	  logger.info("[DataProcessor][process] TestResultResponse: entra al metodo al elseIF");
                actionStatus = "FINALIZADO";
                processUnitaryTests(prueba);
            }
            logger.info("[DataProcessor][process] TestResultResponse: )"+prueba.getInformacionPrueba().getEstado());
        }
    }

    private void processUnitaryTests(PruebaIntegrada prueba) {
        SectionOssDto sectionOss = null;
        List<PruebaUnitaria> listaPruebasUnitarias = prueba.getPruebasUnitarias().getPruebaUnitaria();

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
                            // Procesar resultados para OSS
                        }
                    }
                }
            }
        }
    }
}
