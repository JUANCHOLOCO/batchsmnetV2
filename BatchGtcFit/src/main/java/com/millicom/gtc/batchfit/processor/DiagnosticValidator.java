package com.millicom.gtc.batchfit.processor;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class DiagnosticValidator {

	@Autowired
    private static JdbcTemplate jdbcTemplate;

    @Autowired
    public DiagnosticValidator(JdbcTemplate jdbcTemplate) {
        DiagnosticValidator.jdbcTemplate = jdbcTemplate;
    }

    public boolean validateDiagnosticCodeSMNet(String diagnosticCode, String manualTestState, String unitTestState, String category) {
        // Ensure unitTestState is not null or empty
        unitTestState = (unitTestState == null || unitTestState.isEmpty()) ? "" : unitTestState;

        // SQL query to fetch the category from the database based on the diagnostic code
        String query = "SELECT categoria FROM public.codigodiagnosticos WHERE codigover = ?";

        // Execute query using JdbcTemplate and fetch the category
        String dbCategory = jdbcTemplate.queryForObject(query, new Object[]{diagnosticCode}, String.class);

        if (dbCategory != null) {
            // Validate that the database category is not "OK" and that unitTestState is not equal to manualTestState
            if (!"OK".equals(dbCategory) && !unitTestState.equals(manualTestState)) {
                return true;
            }
        } else {
            // If the code is not found in the database, validate using the category provided
            if (!"OK".equals(category) && category != null && !unitTestState.equals(manualTestState)) {
                return true;
            }
        }
        
        return false;
    }
    
    public String calculateSMNetDiagnosticCode(String codigoVer) {
        String sql = "SELECT categoria FROM codigodiagnosticos WHERE codigover = ?";
        String category;

        try {
            category = jdbcTemplate.queryForObject(sql, new Object[]{codigoVer}, String.class);
        } catch (Exception e) {
            // If there's no result in the database, return the original code
            category = codigoVer;
        }

        return category;
    }
}

