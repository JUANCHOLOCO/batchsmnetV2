package com.millicom.gtc.batchfit.dto.smnet;


import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SectionCpeHFCDto {

    private String cpeCpmInformationCmClientName;
    private String cpeCpmInformationCmClientDescription;
    private String cpeCpmInformationCmFirmware;
    private String cpeCpmInformationCmIpNavigation;
    private String cpeCpmInformationWifiSsid;
    private String cpeCpmInformationWifiChannel;
    private String cpeCpmInformationEmtaIp;
    private List<String> cpeCpmInformationEmtaPorts;

    // Constructor
    public SectionCpeHFCDto() {}

    // Getters and Setters for each field
    public String getCpeCpmInformationCmClientName() {
        return cpeCpmInformationCmClientName;
    }

    public void setCpeCpmInformationCmClientName(String cpeCpmInformationCmClientName) {
        this.cpeCpmInformationCmClientName = cpeCpmInformationCmClientName;
    }

    public String getCpeCpmInformationCmClientDescription() {
        return cpeCpmInformationCmClientDescription;
    }

    public void setCpeCpmInformationCmClientDescription(String cpeCpmInformationCmClientDescription) {
        this.cpeCpmInformationCmClientDescription = cpeCpmInformationCmClientDescription;
    }

    public String getCpeCpmInformationCmFirmware() {
        return cpeCpmInformationCmFirmware;
    }

    public void setCpeCpmInformationCmFirmware(String cpeCpmInformationCmFirmware) {
        this.cpeCpmInformationCmFirmware = cpeCpmInformationCmFirmware;
    }

    public String getCpeCpmInformationCmIpNavigation() {
        return cpeCpmInformationCmIpNavigation;
    }

    public void setCpeCpmInformationCmIpNavigation(String cpeCpmInformationCmIpNavigation) {
        this.cpeCpmInformationCmIpNavigation = cpeCpmInformationCmIpNavigation;
    }

    public String getCpeCpmInformationWifiSsid() {
        return cpeCpmInformationWifiSsid;
    }

    public void setCpeCpmInformationWifiSsid(String cpeCpmInformationWifiSsid) {
        this.cpeCpmInformationWifiSsid = cpeCpmInformationWifiSsid;
    }

    public String getCpeCpmInformationWifiChannel() {
        return cpeCpmInformationWifiChannel;
    }

    public void setCpeCpmInformationWifiChannel(String cpeCpmInformationWifiChannel) {
        this.cpeCpmInformationWifiChannel = cpeCpmInformationWifiChannel;
    }

    public String getCpeCpmInformationEmtaIp() {
        return cpeCpmInformationEmtaIp;
    }

    public void setCpeCpmInformationEmtaIp(String cpeCpmInformationEmtaIp) {
        this.cpeCpmInformationEmtaIp = cpeCpmInformationEmtaIp;
    }

    public List<String> getCpeCpmInformationEmtaPorts() {
        return cpeCpmInformationEmtaPorts;
    }

    public void setCpeCpmInformationEmtaPorts(List<String> cpeCpmInformationEmtaPorts) {
        this.cpeCpmInformationEmtaPorts = cpeCpmInformationEmtaPorts;
    }

    // Create Partial Table method
    public String createPartialTableCpeHFC(StringBuilder responseClick) throws Exception {
        String title = "<tr class=\"tableizer-secondsrows\"><th colspan=\"3\">CPE INFORMATION (HFC)</th></tr>";
        responseClick.append("\n\rCPE INFORMATION (HFC)\n\r\n\rCable Modem Information\n\r");

        String table = "<tr><th colspan=\"3\">Cable Modem Information</th></tr>" +
                String.format("<tr><td>Client Name:</td><td>%s</td><td>&nbsp;</td></tr>", cpeCpmInformationCmClientName) +
                String.format("<tr><td>Client Description:</td><td>%s</td><td>&nbsp;</td></tr>", cpeCpmInformationCmClientDescription) +
                String.format("<tr><td>Firmware:</td><td>%s</td><td>&nbsp;</td></tr>", cpeCpmInformationCmFirmware) +
                String.format("<tr><td>IP Navigation:</td><td>%s</td><td>&nbsp;</td></tr>", cpeCpmInformationCmIpNavigation) +
                "<tr><th colspan=\"3\">WiFi Information</th></tr>" +
                String.format("<tr><td>SSID:</td><td>%s</td><td>&nbsp;</td></tr>", cpeCpmInformationWifiSsid) +
                String.format("<tr><td>Channel:</td><td>%s</td><td>&nbsp;</td></tr>", cpeCpmInformationWifiChannel) +
                "<tr><th colspan=\"3\">EMTA Information</th></tr>" +
                String.format("<tr><td>IP:</td><td>%s</td><td>&nbsp;</td></tr>", cpeCpmInformationEmtaIp);

        // Append to responseClick
        responseClick.append(String.format("Client Name:\t\t%s\t\t&nbsp;", cpeCpmInformationCmClientName))
                .append(String.format("\n\rClient Description:\t\t%s\t\t&nbsp;", cpeCpmInformationCmClientDescription))
                .append(String.format("\n\rFirmware:\t\t%s\t\t&nbsp;", cpeCpmInformationCmFirmware))
                .append(String.format("\n\rIP Navigation:\t\t%s\t\t&nbsp;", cpeCpmInformationCmIpNavigation))
                .append("\n\r\n\rWiFi Information\n\r")
                .append(String.format("\n\rSSID:\t\t%s\t\t&nbsp;", cpeCpmInformationWifiSsid))
                .append(String.format("\n\rChannel:\t\t%s\t\t&nbsp;", cpeCpmInformationWifiChannel))
                .append("\n\r\n\rEMTA Information\n\r")
                .append(String.format("\n\rIP:\t\t%s\t\t&nbsp;", cpeCpmInformationEmtaIp));

        // Parse EMTA ports if available
        if (cpeCpmInformationEmtaPorts != null) {
            Document xmlDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            int count = 0;
            for (String port : cpeCpmInformationEmtaPorts) {
                count++;
                Element puertoElement = xmlDoc.createElement("puerto");
                puertoElement.setTextContent(port);
                xmlDoc.appendChild(puertoElement);

                table += String.format("<tr><td>Port IP Registration Status %d:</td><td>%s</td><td>&nbsp;</td></tr>", count,
                        puertoElement.getElementsByTagName("estadoRegistroSIP").item(0) != null ? puertoElement.getElementsByTagName("estadoRegistroSIP").item(0).getTextContent() : "NoData") +
                        String.format("<tr><td>Phone Identifier Status %d:</td><td>%s</td><td>&nbsp;</td></tr>", count,
                                puertoElement.getElementsByTagName("identificadorTelefonico").item(0) != null ? puertoElement.getElementsByTagName("identificadorTelefonico").item(0).getTextContent() : "NoData");

                responseClick.append(String.format("\n\rPort IP Registration Status %d:\t\t%s\t\t&nbsp;", count,
                        puertoElement.getElementsByTagName("estadoRegistroSIP").item(0) != null ? puertoElement.getElementsByTagName("estadoRegistroSIP").item(0).getTextContent() : "NoData"))
                        .append(String.format("\n\rPhone Identifier Status %d:\t\t%s\t\t&nbsp;", count,
                                puertoElement.getElementsByTagName("identificadorTelefonico").item(0) != null ? puertoElement.getElementsByTagName("identificadorTelefonico").item(0).getTextContent() : "NoData"));
            }
        }

        return title + table;
    }
}
