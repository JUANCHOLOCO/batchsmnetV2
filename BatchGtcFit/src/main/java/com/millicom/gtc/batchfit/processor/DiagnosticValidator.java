package com.millicom.gtc.batchfit.processor;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class DiagnosticValidator {
    private static final Logger logger = LoggerFactory.getLogger(DiagnosticValidator.class);

    private final DataSource dataSource;

    // Inyectar el DataSource
    @Autowired
    public DiagnosticValidator(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Método para validar el código de diagnóstico
    public boolean validateDiagnosticCodeSMNet(String diagnosticCode, String manualTestState, String unitTestState, String category) {
        unitTestState = (unitTestState == null || unitTestState.isEmpty()) ? "" : unitTestState;
        logger.info("[DataProcessor][process] entrando a la BD con DataSource");

        String query = "SELECT categoria FROM public.codigodiagnosticos WHERE codigover = ?";
        String dbCategory = null;

        // Usar el DataSource para obtener una conexión y ejecutar la consulta SQL
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Asignar el valor al parámetro de la consulta
            preparedStatement.setString(1, diagnosticCode);

            // Ejecutar la consulta y procesar el resultado
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    dbCategory = resultSet.getString("categoria");
                    logger.info("[DataProcessor][process] Resultado de la BD: " + dbCategory);
                }
            }

        } catch (SQLException e) {
            logger.error("Error al ejecutar la consulta: " + e.getMessage(), e);
            return false;
        }

        // Validar el resultado obtenido de la BD
        if (dbCategory != null) {
            if (!"OK".equals(dbCategory) && !unitTestState.equals(manualTestState)) {
                return true;
            }
        } else {
            if (!"OK".equals(category) && category != null && !unitTestState.equals(manualTestState)) {
                return true;
            }
        }

        return false;
    }

    // Método para calcular el código de diagnóstico
    public String calculateSMNetDiagnosticCode(String codigoVer) {
        String sql = "SELECT categoria FROM codigodiagnosticos WHERE codigover = ?";
        String category = null;

        // Usar el DataSource para obtener una conexión y ejecutar la consulta SQL
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Asignar el valor al parámetro de la consulta
            preparedStatement.setString(1, codigoVer);

            // Ejecutar la consulta y obtener el resultado
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    category = resultSet.getString("categoria");
                }
            }

        } catch (SQLException e) {
            logger.error("Error al ejecutar la consulta: " + e.getMessage(), e);
            return codigoVer;  // Si hay un error o no hay resultado, devuelve el código original
        }

        return category != null ? category : codigoVer;
    }
}
