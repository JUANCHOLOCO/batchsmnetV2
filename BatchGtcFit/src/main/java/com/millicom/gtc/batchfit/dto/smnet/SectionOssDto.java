package com.millicom.gtc.batchfit.dto.smnet;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import java.util.List;

@XmlRootElement(name = "SeccionOSSTO")
@XmlType(propOrder = { "ossPortafolioTecnologia", "ossClientesClienteServicios", "ossErrorCodigo", "ossErrorDescripcion" })
public class SectionOssDto {

    private String ossPortafolioTecnologia;
    private List<String> ossClientesClienteServicios;
    private String ossErrorCodigo;
    private String ossErrorDescripcion;

    // Default constructor
    public SectionOssDto() { }

    // Getters and Setters
    @XmlElement(name = "oss_portafolio_tecnologia")
    public String getOssPortafolioTecnologia() {
        return ossPortafolioTecnologia;
    }

    public void setOssPortafolioTecnologia(String ossPortafolioTecnologia) {
        this.ossPortafolioTecnologia = ossPortafolioTecnologia;
    }

    @XmlElement(name = "oss_clientes_cliente_servicios")
    public List<String> getOssClientesClienteServicios() {
        return ossClientesClienteServicios;
    }

    public void setOssClientesClienteServicios(List<String> ossClientesClienteServicios) {
        this.ossClientesClienteServicios = ossClientesClienteServicios;
    }

    @XmlElement(name = "oss_error_codigo")
    public String getOssErrorCodigo() {
        return ossErrorCodigo;
    }

    public void setOssErrorCodigo(String ossErrorCodigo) {
        this.ossErrorCodigo = ossErrorCodigo;
    }

    @XmlElement(name = "oss_error_descripcion")
    public String getOssErrorDescripcion() {
        return ossErrorDescripcion;
    }

    public void setOssErrorDescripcion(String ossErrorDescripcion) {
        this.ossErrorDescripcion = ossErrorDescripcion;
    }

    // Method to generate a partial HTML table for the OSS information
    public String createParcialTablaOSS(String newreponseSalesforce) {
        StringBuilder tabla = new StringBuilder();
        StringBuilder reponseSalesforce = new StringBuilder("\n\r\n\rINFORMACION OSS\n\r\n\r");

        if (ossClientesClienteServicios != null && !ossClientesClienteServicios.isEmpty()) {
            int count = 0;
            String titulo = "<tr class=\"tableizer-secondsrows\"><th colspan=\"3\">INFORMATION OSS</th></tr>";
            tabla.append(titulo);

            for (String servicio : ossClientesClienteServicios) {
                count++;
                tabla.append(String.format("<tr><th colspan=\"3\">Service %d</th></tr>", count))
                     .append(String.format("<tr><td>Product:</td><td>%s</td><td>&nbsp;</td></tr>", servicio))
                     .append(String.format("<tr><td>ID:</td><td>%s</td><td>&nbsp;</td></tr>", "NoData")); // Modify to extract actual data

                reponseSalesforce.append(String.format("Service %d", count))
                             .append("\n\r")
                             .append(String.format("Product:\t\t%s\t\t&nbsp;", servicio))
                             .append("\n\r")
                             .append("ID:\t\tNoData\t\t&nbsp;\n\r");  // Modify to extract actual data
            }

            if (ossErrorCodigo != null && ossErrorDescripcion != null) {
                tabla.append("<tr><th colspan=\"3\">Error</th></tr>")
                     .append(String.format("<tr><td>Error Code:</td><td>%s</td><td>&nbsp;</td></tr>", ossErrorCodigo))
                     .append(String.format("<tr><td>Error Description:</td><td>%s</td><td>&nbsp;</td></tr>", ossErrorDescripcion));

                reponseSalesforce.append("\n\r\n\rError\n\r")
                             .append(String.format("Error Code:\t\t%s\t\t&nbsp;", ossErrorCodigo))
                             .append("\n\r")
                             .append(String.format("Error Description:\t\t%s\t\t&nbsp;", ossErrorDescripcion))
                             .append("\n\r");
            }
        }

        newreponseSalesforce = reponseSalesforce.toString();
        return tabla.toString();
    }
}
