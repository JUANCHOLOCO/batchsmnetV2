package com.millicom.gtc.batchfit.processor;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;

public class XMLParserUtility {

    public static <T> T parseXML(String strXml, Class<T> clazz) throws Exception {
        if (strXml == null || strXml.isEmpty())
            return null;

        T objRef = clazz.getDeclaredConstructor().newInstance();

        Document documentResponse = loadXMLFromString(strXml);

 
        switch (clazz.getSimpleName()) {
            case "SectionOssDto":
                String tagValue = "";
                List<String> ls = new ArrayList<>();

                tagValue = getTagXML(documentResponse, "//oss/portafolio/tecnologia");
                setProperty(objRef, "ossPortafolioTecnologia", tagValue);

                ls = new ArrayList<>();
                getTagsXML(documentResponse, "//oss/clientes/cliente/servicios/servicio", ls);
                setProperty(objRef, "ossClientesClienteServicios", ls);

                tagValue = getTagXML(documentResponse, "//oss/error/codigo");
                setProperty(objRef, "ossErrorCodigo", tagValue);

                tagValue = getTagXML(documentResponse, "//oss/error/descripcion");
                setProperty(objRef, "ossErrorDescripcion", tagValue);

                break;  
        }

        return objRef;
    }

    public static String getTagXML(Document xml, String xpathExpression) throws XPathExpressionException {
        XPath xpath = XPathFactory.newInstance().newXPath();
        return xpath.evaluate(xpathExpression, xml);
    }

    public static void getTagsXML(Document xml, String xpathExpression, List<String> listaItems) throws XPathExpressionException {
        XPath xpath = XPathFactory.newInstance().newXPath();
        NodeList nodes = (NodeList) xpath.evaluate(xpathExpression, xml, javax.xml.xpath.XPathConstants.NODESET);
        
        for (int i = 0; i < nodes.getLength(); i++) {
            listaItems.add(nodes.item(i).getTextContent());
        }
    }

    public static void setProperty(Object objRef, String propertyName, Object value) throws Exception {
        PropertyDescriptor pd = new PropertyDescriptor(propertyName, objRef.getClass());
        Method setter = pd.getWriteMethod();
        if (setter != null) {
            setter.invoke(objRef, value);
        }
    }

    public static Document loadXMLFromString(String xml) throws Exception {
        InputSource is = new InputSource(new StringReader(xml));
        Document doc = javax.xml.parsers.DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .parse(is);
        return doc;
    }
}
