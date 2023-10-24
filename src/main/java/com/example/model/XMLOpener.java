package com.example.model;

import java.io.File;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class XMLOpener{

    private XMLOpener() {}

    private static class SingletonHelper {
        private static final XMLOpener INSTANCE = new XMLOpener();
    }

    public static XMLOpener getInstance() {
        return SingletonHelper.INSTANCE;
    }

    public void ReadFile(Carte carte, String path) throws CustomXMLParsingException{
        File file = new File(path);

        if (file.length() == 0) {
            throw new CustomXMLParsingException("Fichier vide");
        }

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            DefaultHandler handler = new HandlerPlan(carte);
            saxParser.parse(new File(path), handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class HandlerPlan extends DefaultHandler {
        private Carte carte;
        public HandlerPlan(Carte carte){
            this.carte = carte;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if ("intersection".equals(qName)) {
                Long id = Long.valueOf(attributes.getValue("id"));
                double latitude = Double.parseDouble(attributes.getValue("latitude"));
                double longitude = Double.parseDouble(attributes.getValue("longitude"));
                carte.addIntersection(id, latitude, longitude);
            } else if ("segment".equals(qName)) {
                Long destination = Long.valueOf(attributes.getValue("destination"));
                double length = Double.parseDouble(attributes.getValue("length"));
                String name = attributes.getValue("name");
                Long origin = Long.valueOf(attributes.getValue("origin"));
                carte.addSegment(origin, destination, length, name);
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