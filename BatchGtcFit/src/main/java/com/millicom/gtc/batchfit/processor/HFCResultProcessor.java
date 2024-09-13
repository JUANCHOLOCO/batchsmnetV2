package com.millicom.gtc.batchfit.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.millicom.gtc.batchfit.dto.smnet.AccessSectionHFCDto;
import com.millicom.gtc.batchfit.dto.smnet.SectionOssDto;
import com.millicom.gtc.batchfit.dto.smnet.SectionCpeHFCDto;
import com.millicom.gtc.batchfit.dto.smnet.Resultado;
import com.millicom.gtc.batchfit.dto.smnet.Diagnostico;
import com.millicom.gtc.batchfit.dto.smnet.MessageSalesForceDto;
import com.millicom.gtc.batchfit.dto.smnet.ProcessResultDto;
import java.util.List;
import java.util.ArrayList;
import java.util.AbstractMap;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component

public class HFCResultProcessor {
	 private final DataSource dataSource;
	
	    @Autowired
	    public HFCResultProcessor(DataSource dataSource) {
	        this.dataSource = dataSource;
	    }
	private static final Logger logger = LoggerFactory.getLogger(GtcDataProcessor.class);
	  
	public ProcessResultDto processResultsForHFC(SectionOssDto sectionOss, List<Resultado> resultados, String status,String workOrderId,String id ) {
		
		ObjectMapper objectMapper = new ObjectMapper();
		DiagnosticValidator diagnosticValidator = new DiagnosticValidator(dataSource);
	    AccessSectionHFCDto accessSectionHFC = null;
	    SectionCpeHFCDto cpeSectionHFC = null;
	    List<Map.Entry<String, String>> hfcDiagnosticsList = new ArrayList<>();
	    boolean partialTestError = false;
	 	logger.info("[DataProcessor][process] paso declaracion de variables");

	        for (Resultado testResult : resultados) {
	        	logger.info("[DataProcessor][process] entro al for");
	        	List<Diagnostico> diagnostics = testResult.getDiagnosticos().getDiagnostico();
	            if (diagnostics != null) {
	               
                    try {
                        String json = objectMapper.writeValueAsString(diagnostics);
                        logger.info("[DataProcessor][process] Resultados: " + json);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
	                for (Diagnostico diagnostic : diagnostics) {
	                    hfcDiagnosticsList.add(new AbstractMap.SimpleEntry<>(diagnostic.getCodigo(), diagnostic.getDescripcion()));
	                    if (!partialTestError && diagnosticValidator.validateDiagnosticCodeSMNet(diagnostic.getCodigo(), "manual", status, diagnostic.getCategoria())) {
	                    	logger.info("[DataProcessor][process] hizo la validacion");
	                        partialTestError = true;
	                    }
	                }
	            }

	            try {
	            	logger.info("[DataProcessor][process] TestResultResponse: entra al try del parceo");
	                if ("ACCESO".equals(testResult.getCategoria())) {
	                    accessSectionHFC = XMLParserUtility.parseXML(testResult.getDatos(), AccessSectionHFCDto.class);
	                } else if ("CPE".equals(testResult.getCategoria())) {
	                    cpeSectionHFC = XMLParserUtility.parseXML(testResult.getDatos(), SectionCpeHFCDto.class);
	                }
	            } catch (Exception e) {
	                logger.error("Error parsing XML: " + e.getMessage());
	            }
	        }
	    
	    return processHFCResults(accessSectionHFC, cpeSectionHFC, hfcDiagnosticsList, workOrderId, id );
	}

    
	public ProcessResultDto processHFCResults(AccessSectionHFCDto accessSectionHFC, SectionCpeHFCDto cpeSectionHFC, List<Map.Entry<String, String>> hfcDiagnosticsList,String workOrderId,String id ) {
	    StringBuilder htmlCdata = new StringBuilder();
	    StringBuilder consolidatedResponse = new StringBuilder();
	    DiagnosticValidator diagnosticValidator = new DiagnosticValidator(dataSource);
	    String htmlTableHeader = "<html><head><style type=\"text/css\">" +
	            "table.tableizer-table {font-size: 12px; border: 1px solid #CCC; font-family: Arial, Helvetica, sans-serif;}" +
	            ".tableizer-table td {padding: 4px; margin: 3px; border: 1px solid #ccc;}" +
	            ".tableizer-table th {background-color: #FFFFFF; color: #000; font-weight: bold;}" +
	            ".tableizer-firstrow th {background-color: #000000; color: #FFF; font-weight: bold;}" +
	            "</style></head><body><table style=\"width:100%\" class=\"tableizer-table\">" +
	            "<tr class=\"tableizer-firstrow\"><th colspan=\"7\">Electrical Test Result</th></tr>";

	    String htmlCdataContent = "<![CDATA[" + htmlTableHeader;

	    if (accessSectionHFC != null) {
	        htmlCdata.append(accessSectionHFC.createPartialAccessHFCTable(consolidatedResponse));
	        htmlCdataContent += "<tr class=\"tableizer-secondsrows\"><th colspan=\"3\">TECNOLOGIA HFC</th></tr>" + htmlCdata.toString();
	    }

	    if (!hfcDiagnosticsList.isEmpty()) {
	        htmlCdata.append("<tr><th colspan=\"3\">Diagnostics</th></tr>");
	        consolidatedResponse.append("\n\rDiagnostics\n\r");
	        for (Map.Entry<String, String> diagnostic : hfcDiagnosticsList) {
	            String diagnosticCode = diagnosticValidator.calculateSMNetDiagnosticCode(diagnostic.getKey());
	            String diagnosticDescription = diagnostic.getValue();

	            htmlCdata.append(String.format("<tr><td>Code:</td><td>%s</td><td>&nbsp;</td></tr>", diagnosticCode))
	                     .append(String.format("<tr><td>Description:</td><td>%s</td><td>&nbsp;</td></tr>", diagnosticDescription));

	            consolidatedResponse.append(String.format("Code:\t\t%s\t\t&nbsp;", diagnosticCode))
	                                 .append(String.format("\n\rDescription:\t\t%s\t\t&nbsp;", diagnosticDescription));
	        }
	    }

	    if (cpeSectionHFC != null) {
	        try {
	            htmlCdata.append(cpeSectionHFC.createPartialTableCpeHFC(consolidatedResponse));
	        } catch (Exception e) {
	            logger.error("Error parsing XML: " + e.getMessage());
	        }
	    }

	    htmlCdataContent += htmlCdata.toString() + "</table></body></html>]]>";
	   
	    return new ProcessResultDto(consolidatedResponse.toString(), htmlCdataContent);
	}
	

}

