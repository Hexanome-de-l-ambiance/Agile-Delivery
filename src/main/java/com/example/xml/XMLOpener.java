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

/**
 * Cette classe gère la lecture des fichiers XML pour charger des données dans une carte.
 */
public class XMLOpener{

    private XMLOpener() {}

    private static class SingletonHelper {
        private static final XMLOpener INSTANCE = new XMLOpener();
    }

    /**
     * Récupère l'instance unique de XMLOpener selon le modèle de conception Singleton.
     *
     * @return L'instance unique de XMLOpener.
     */
    public static XMLOpener getInstance() {
        return SingletonHelper.INSTANCE;
    }

    /**
     * Charge les données d'une tournée à partir d'un fichier XML.
     *
     * @param stage La fenêtre parente pour la boîte de dialogue de chargement.
     * @param carte L'objet Carte dans lequel charger les données de la tournée depuis le fichier XML.
     * @throws CustomXMLParsingException En cas d'erreur lors du chargement des données de la tournée à partir du fichier XML.
     */
    public void loadTour(Stage stage, Carte carte) throws CustomXMLParsingException {
        File file = XMLFilter.getInstance().open(stage, true);

        if (file == null) {
            carte.sendException(new CustomXMLParsingException("Pas de fichier sélectionné"));
            throw new CustomXMLParsingException("Pas de fichier sélectionné");
        }

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            DefaultHandler handler = new HandlerTour(carte);
            saxParser.parse(file, handler);
        } catch (Exception e) {
            e.printStackTrace();
            carte.sendException(e);
        }
    }

    /**
     * Lit et analyse le fichier XML pour charger les intersections et les segments de la carte.
     *
     * @param stage La fenêtre parente pour la boîte de dialogue de chargement.
     * @param carte L'objet Carte dans lequel charger les données depuis le fichier XML.
     * @throws CustomXMLParsingException En cas d'erreur lors de la lecture ou du chargement des données de tournée à partir du fichier XML.
     */
    public void readFile(Stage stage, Carte carte) throws CustomXMLParsingException {
        File file = XMLFilter.getInstance().open(stage, true);
        if (file == null) {
            carte.sendException(new CustomXMLParsingException("Pas de fichier sélectionné"));
            throw new CustomXMLParsingException("Pas de fichier sélectionné");
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

    /**
     * Lit et analyse un fichier XML à partir du chemin spécifié pour initialiser les intersections et les segments de la carte.
     *
     * @param carte L'objet Carte dans lequel initialiser les intersections et les segments depuis le fichier XML.
     * @param path  Le chemin du fichier XML à lire.
     * @throws CustomXMLParsingException En cas d'erreur lors de la lecture ou de l'initialisation des données du plan à partir du fichier XML.
     */
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

    /**
     * Gestionnaire SAX pour la lecture des éléments liés aux tournées dans un fichier XML.
     */
    private static class HandlerTour extends DefaultHandler {
        private final Carte carte;
        private Livraison currentLivraison;
        private Long currentAddressId;
        private StringBuilder charactersBuffer = new StringBuilder();
        private int numeroCoursier;
        private int nbCoursiers;

        public HandlerTour(Carte carte) {
            this.carte = carte;
        }


        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            charactersBuffer.setLength(0);

            if ("tournees".equals(qName)) {
                String nbCoursiersValue = attributes.getValue("nbCoursiers");
                if (nbCoursiersValue != null) {
                    nbCoursiers = Integer.parseInt(nbCoursiersValue);
                    carte.setNbCoursiers(nbCoursiers);
                }
            } else if ("tournee".equals(qName)) {
                String coursierIdValue = attributes.getValue("numeroCoursier");
                if (coursierIdValue != null) {
                    numeroCoursier = Integer.parseInt(coursierIdValue);
                }
            } else if ("livraison".equals(qName)) {
                currentLivraison = new Livraison();
            } else if ("address".equals(qName) && currentLivraison != null) {
                currentAddressId = Long.valueOf(attributes.getValue("id"));
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            charactersBuffer.append(ch, start, length);
        }

        @Override
        public void endElement(String uri, String localName, String qName) {
            if ("creneauHoraire".equals(qName) && currentLivraison != null) {
                LocalTime creneauHoraire = LocalTime.parse(charactersBuffer.toString().trim());
                currentLivraison.setCreneauHoraire(creneauHoraire);
            } else if ("address".equals(qName) && currentLivraison != null) {
                Intersection intersection = carte.getIntersection(currentAddressId);
                currentLivraison.setDestination(intersection);
                currentAddressId = null;
            } else if ("livraison".equals(qName) && currentLivraison != null) {
                carte.addLivraison(numeroCoursier, currentLivraison);
                currentLivraison = null;
            }
            charactersBuffer.setLength(0);
        }
    }


    /**
     * Gestionnaire SAX pour la lecture des éléments liés à la carte dans un fichier XML.
     */
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

