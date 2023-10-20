package model;

import java.io.File;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class XMLOpener {

    private static XMLOpener instance = null;

    private XMLOpener(){}

    protected static XMLOpener getInstance(){
        if(instance == null) instance = new XMLOpener();
        return instance;
    }

    public void ReadFile(String path) throws Exception {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            DefaultHandler handler = new HandlerPlan();
            saxParser.parse(new File(path), handler);
        } catch (Exception e) {
            e.printStackTrace();
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