package com.example.xml;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.example.model.Carte;
import com.example.model.Intersection;
import javafx.stage.Stage;
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

    public void readFile(Stage stage, Carte carte) throws CustomXMLParsingException {
        File file = XMLFilter.getInstance().open(stage, true);
        if (file == null) {
            carte.sendException(new CustomXMLParsingException("File null"));
            throw new CustomXMLParsingException("File null");
        }
        carte.reset();
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            DefaultHandler handler = new HandlerPlan(carte);
            saxParser.parse(file, handler);
            Path path = Paths.get(file.getAbsolutePath());
            String fileName = path.getFileName().toString();
            carte.readEnd(fileName);
        } catch (Exception e) {
            e.printStackTrace();
            carte.sendException(e);
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

