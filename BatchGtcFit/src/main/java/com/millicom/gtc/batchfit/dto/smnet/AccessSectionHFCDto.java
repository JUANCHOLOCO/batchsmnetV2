package com.millicom.gtc.batchfit.dto.smnet;


public class AccessSectionHFCDto {

    // Constructor
    public AccessSectionHFCDto() {}

    // Fields (Propiedades mapeadas de la clase original)
    private String downstreamBandwidthMax;
    private String upstreamInstantTraffic;
    private String downstreamInstantTraffic;
    private String downstreamSNR;
    private String downstreamPower;
    private String upstreamSNR;
    private String upstreamPower;

    // Getters and Setters (Mismos que en la versión original)
    public String getDownstreamBandwidthMax() {
        return downstreamBandwidthMax;
    }

    public void setDownstreamBandwidthMax(String downstreamBandwidthMax) {
        this.downstreamBandwidthMax = downstreamBandwidthMax;
    }

    public String getUpstreamInstantTraffic() {
        return upstreamInstantTraffic;
    }

    public void setUpstreamInstantTraffic(String upstreamInstantTraffic) {
        this.upstreamInstantTraffic = upstreamInstantTraffic;
    }

    public String getDownstreamInstantTraffic() {
        return downstreamInstantTraffic;
    }

    public void setDownstreamInstantTraffic(String downstreamInstantTraffic) {
        this.downstreamInstantTraffic = downstreamInstantTraffic;
    }

    public String getDownstreamSNR() {
        return downstreamSNR;
    }

    public void setDownstreamSNR(String downstreamSNR) {
        this.downstreamSNR = downstreamSNR;
    }

    public String getDownstreamPower() {
        return downstreamPower;
    }

    public void setDownstreamPower(String downstreamPower) {
        this.downstreamPower = downstreamPower;
    }

    public String getUpstreamSNR() {
        return upstreamSNR;
    }

    public void setUpstreamSNR(String upstreamSNR) {
        this.upstreamSNR = upstreamSNR;
    }

    public String getUpstreamPower() {
        return upstreamPower;
    }

    public void setUpstreamPower(String upstreamPower) {
        this.upstreamPower = upstreamPower;
    }

    // Método para crear la tabla HTML
    public String createPartialAccessHFCTable(StringBuilder newClickResponse) {
        String title = "<tr class=\"tableizer-secondsrows\"><th colspan=\"3\">ACCESS INFORMATION (HFC)</th></tr>";

        // Mejora Click Fase 3 - RF023 Respuesta Prueba SMNET en CS
        newClickResponse.append("\n\rACCESS INFORMATION (HFC)\n\r\n\r");

        String table = "<tr><th colspan=\"3\">Instantaneous Parameters</th></tr>" +
            "<tr class=\"tableizer-secondsrows\"><td>&nbsp;</td><td>Downstream</td><td>Upstream</td></tr>" +
            String.format("<tr><td>Signal-to-Noise Ratio (SNR) (db):</td><td>%s</td><td>%s</td></tr>", downstreamSNR, upstreamSNR) +
            String.format("<tr><td>Power (dBm):</td><td>%s</td><td>%s</td></tr>", downstreamPower, upstreamPower) +
            String.format("<tr><td>Instantaneous Traffic (db):</td><td>%s</td><td>%s</td></tr>", downstreamInstantTraffic, upstreamInstantTraffic) +
            String.format("<tr><td>Maximum Bandwidth (db):</td><td>%s</td><td>&nbsp;</td></tr>", downstreamBandwidthMax);

        // Mejora Click Fase 3 - RF023 Respuesta Prueba SMNET en CS
        newClickResponse.append("Instantaneous Parameters\n\r")
            .append("&nbsp;\t\tDownstream\t\tUpstream\n\r")
            .append(String.format("Signal-to-Noise Ratio (SNR) (db):\t\t%s\t\t%s", downstreamSNR, upstreamSNR))
            .append(String.format("\n\rPower (dBm):\t\t%s\t\t%s", downstreamPower, upstreamPower))
            .append(String.format("\n\rInstantaneous Traffic (db):\t\t%s\t\t%s", downstreamInstantTraffic, upstreamInstantTraffic))
            .append(String.format("\n\rMaximum Bandwidth (db):\t\t%s\t\t&nbsp;", downstreamBandwidthMax));

        return title + table;
    }
}
