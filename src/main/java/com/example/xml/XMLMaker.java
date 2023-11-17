package com.example.xml;

import com.example.model.Carte;
import com.example.model.Livraison;
import com.example.model.Tournee;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * Cette classe fournit des méthodes pour générer et sauvegarder des données de tournées au format XML.
 */
public class XMLMaker {

    private XMLMaker() {}

    private static class SingletonHelper {
        private static final XMLMaker INSTANCE = new XMLMaker();
    }

    /**
     * Récupère l'instance unique de XMLMaker selon le modèle de conception Singleton.
     *
     * @return L'instance unique de XMLMaker.
     */
    public static XMLMaker getInstance() {
        return XMLMaker.SingletonHelper.INSTANCE;
    }

    /**
     * Génère et sauvegarde les données de tournée au format XML.
     *
     * @param stage La fenêtre parente pour la boîte de dialogue de sauvegarde.
     * @param carte L'objet Carte contenant les informations de tournée à sauvegarder.
     * @throws CustomXMLParsingException En cas d'erreur lors de la génération ou de la sauvegarde du fichier XML.
     */
    public void saveTourneeToXML(Stage stage, Carte carte) throws CustomXMLParsingException {
        File file = XMLFilter.getInstance().open(stage, false);
        if (file == null) {
            carte.sendException(new CustomXMLParsingException("Pas de fichier sélectionné"));
            return;
        }

        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            // Créer <tournees> element
            Element tourneeElement = document.createElement("tournees");
            document.appendChild(tourneeElement);
            tourneeElement.setAttribute("nbCoursiers", String.valueOf(carte.getListeTournees().size()));

            for (HashMap.Entry<Integer, Tournee> tourneeEntry : carte.getListeTournees().entrySet()) {
                Element tourElement = document.createElement("tournee");
                tourneeElement.appendChild(tourElement);
                tourElement.setAttribute("numeroCoursier", String.valueOf(tourneeEntry.getKey()));
                Element livraisonsElement = document.createElement("livraisons");
                tourElement.appendChild(livraisonsElement);

                List<Livraison> livraisonsList = tourneeEntry.getValue().getListeLivraisons(); // Assuming getLivraisons() exists and returns List<Livraison>
                Long entrepotId = carte.getEntrepot().getId();
                for (Livraison livraison : livraisonsList) {
                    // Si la livraison est l'entrepot, ne pas l'ajouter
                    if (livraison.getDestination().getId().equals(entrepotId)) {
                        continue;
                    }

                    Element livraisonElement = document.createElement("livraison");
                    livraisonsElement.appendChild(livraisonElement);

                    Element addressElement = document.createElement("address");
                    addressElement.setAttribute("id", String.valueOf(livraison.getDestination().getId())); // Assuming getDestination() and getId() exist
                    livraisonElement.appendChild(addressElement);

                    Element heureLivraisonElement = document.createElement("creneauHoraire");

                    heureLivraisonElement.setTextContent(livraison.getCreneauHoraire().toString()); // Assuming getHeureLivraison() exists and returns a Time object
                    livraisonElement.appendChild(heureLivraisonElement);
                }
            }

            // Créer le fichier XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(file);

            transformer.transform(domSource, streamResult);
        } catch (ParserConfigurationException | TransformerException e) {
            throw new CustomXMLParsingException("Erreur lors de la génération du fichier XML", e);
        }
    }

}
