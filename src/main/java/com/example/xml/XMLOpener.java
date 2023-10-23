package com.example.xml;

import java.io.File;
import java.io.IOException;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class XMLOpener {
    // singleton pour la sécurité de thread
    private XMLOpener() {}

    private static class SingletonHelper {
        private static final XMLOpener INSTANCE = new XMLOpener();
    }

    public static XMLOpener getInstance() {
        return SingletonHelper.INSTANCE;
    }

    public void readFile(String path) throws CustomXMLParsingException {
        File file = new File(path);
        
        if (file.length() == 0) {
            throw new CustomXMLParsingException("Fichier vide");
        }
        
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            DefaultHandler handler = new HandlerPlan();
            saxParser.parse(new File(path), handler);
        } catch (SAXException | ParserConfigurationException | IOException e) {
            throw new CustomXMLParsingException("Error parsing XML", e);
        }
    }

    private static class HandlerPlan extends DefaultHandler {
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if ("intersection".equals(qName)) {
                String id = attributes.getValue("id");
                String latitude = attributes.getValue("latitude");
                String longitude = attributes.getValue("longitude");
                System.out.println("Intersection - ID: " + id + ", Latitude: " + latitude + ", Longitude: " + longitude);
            } else if ("segment".equals(qName)) {
                String destination = attributes.getValue("destination");
                String length = attributes.getValue("length");
                String name = attributes.getValue("name");
                String origin = attributes.getValue("origin");
                System.out.println("Segment - Destination: " + destination + ", Length: " + length + ", Name: " + name + ", Origin: " + origin);
            }
        }
    }
}
class CustomXMLParsingException extends Exception {
    public CustomXMLParsingException(String message) {
        super(message);
    }
    public CustomXMLParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}