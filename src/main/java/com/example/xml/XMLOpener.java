package com.example.xml;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;

import com.example.model.Carte;
import com.example.model.Intersection;
import com.example.model.Livraison;
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

    public void saveTour(Stage stage, Carte carte) throws CustomXMLParsingException {
        try {
            XMLMaker.getInstance().saveTourneeToXML(stage, carte);
        } catch (CustomXMLParsingException e) {
            throw new CustomXMLParsingException(e.getMessage());
        }

    }

    public void readTour(Stage stage, Carte carte) throws CustomXMLParsingException {
        File file = XMLFilter.getInstance().open(stage, true);
        if (file == null) {
            carte.sendException(new CustomXMLParsingException("File null"));
            throw new CustomXMLParsingException("File null");
        }
        carte.reset();
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            DefaultHandler handler = new HandlerTour(carte);
            saxParser.parse(file, handler);
            Path path = Paths.get(file.getAbsolutePath());
            String fileName = path.getFileName().toString();
            carte.readEnd(fileName);
            carte.initAdjacenceList();

        } catch (Exception e) {
            e.printStackTrace();
            carte.sendException(e);
        }
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
            carte.initAdjacenceList();

        } catch (Exception e) {
            e.printStackTrace();
            carte.sendException(e);
        }
    }

    public void readFile(Carte carte, String path) throws CustomXMLParsingException {
        File file = new File(path);

        if (file.length() == 0) {
            throw new CustomXMLParsingException("Fichier vide");
        }

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            DefaultHandler handler = new HandlerPlan(carte);
            saxParser.parse(file, handler);
            carte.initAdjacenceList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeFile(Carte carte) throws CustomXMLParsingException {

    }

    private static class HandlerTour extends DefaultHandler {
        private Carte carte;
        private Livraison currentLivraison;
        private Long currentAddressId;
        private StringBuilder charactersBuffer = new StringBuilder();
        private int numeroCoursier;

        public HandlerTour(Carte carte) {
            this.carte = carte;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            charactersBuffer.setLength(0); // Clear the characters buffer

            try {
                if ("tournee".equals(qName)) {
                    // If there are attributes specific to the "tournee" to handle, do it here.
                    // e.g., String courierId = attributes.getValue("coursier");
                    numeroCoursier = Integer.parseInt(attributes.getValue("coursier"));
                } else if ("livraison".equals(qName)) {
                    // Prepare to handle a new Livraison
                    currentLivraison = new Livraison();
                } else if ("address".equals(qName) && currentLivraison != null) {
                    currentAddressId = Long.valueOf(attributes.getValue("id"));
                } // Additional 'else if' blocks for other elements such as "chemin" would go here
            } catch (NumberFormatException e) {
                throw new SAXException("Number format exception encountered.", e);
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            // Accumulate text content in a StringBuilder
            charactersBuffer.append(ch, start, length);
        }

        @Override
        public void endElement(String uri, String localName, String qName) {
            String content = charactersBuffer.toString().trim(); // Trim whitespace from the accumulated text
            if ("livraison".equals(qName) && currentLivraison != null && currentAddressId != null) {
                // Assume that Livraison requires an Intersection object and a delivery time
                Intersection intersection = carte.getIntersection(currentAddressId);
                currentLivraison.setDestination(intersection);
                // Add the complete Livraison to the Carte
                carte.addLivraison(numeroCoursier, currentLivraison);
                // Reset the currentLivraison and currentAddressId for the next delivery
                currentLivraison = null;
                currentAddressId = null;
            } else if ("heureLivraison".equals(qName) && currentLivraison != null) {
                // Handle the heureLivraison if it's not directly in "livraison"
                LocalTime heureLivraison = LocalTime.parse(content);
                currentLivraison.setHeureLivraison(heureLivraison);
            } // Additional 'else if' blocks for handling end of "chemin" and its children would go here

            charactersBuffer.setLength(0); // Clear the buffer after handling the element's content
        }

        // Helper methods to add Livraison and other objects to the Carte would be defined outside of the endElement method
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
            } else if("warehouse".equals(qName)){
                Long id = Long.valueOf(attributes.getValue("address"));
                carte.setEntrepotId(id);
            }
        }
    }
}

