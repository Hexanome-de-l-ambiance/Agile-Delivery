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

public class XMLMaker {

    private XMLMaker() {}

    private static class SingletonHelper {
        private static final XMLMaker INSTANCE = new XMLMaker();
    }

    public static XMLMaker getInstance() {
        return XMLMaker.SingletonHelper.INSTANCE;
    }



    public void saveTourneeToXML(Stage stage, Carte carte) throws CustomXMLParsingException {
        File file = XMLFilter.getInstance().open(stage, false);
        if (file == null) {
            // User cancelled the operation
            return;
        }

        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            // Create the root <tournee> element
            Element tourneeElement = document.createElement("tournee");
            document.appendChild(tourneeElement);

            // Assume each courier has a Tournee, and we save each Tournee in its own XML
            for (HashMap.Entry<Integer, Tournee> tourneeEntry : carte.getListeTournees().entrySet()) {
                // Set attribute courser to tournee element
                tourneeElement.setAttribute("coursier", tourneeEntry.getKey().toString());

                // Create <livraisons> element
                Element livraisonsElement = document.createElement("livraisons");
                tourneeElement.appendChild(livraisonsElement);

                // Iterate through each Livraison and add to <livraisons>
                List<Livraison> livraisonsList = tourneeEntry.getValue().getLivraisons(); // Assuming getLivraisons() exists and returns List<Livraison>
                Long entrepotId = carte.getEntrepot().getId();
                for (Livraison livraison : livraisonsList) {
                    // Check if the Livraison is the entrepot; if so, skip it
                    if (livraison.getDestination().getId().equals(entrepotId)) {
                        continue; // Skip the entrepot
                    }

                    // Create <livraison> element for non-entrepot addresses
                    Element livraisonElement = document.createElement("livraison");
                    livraisonsElement.appendChild(livraisonElement);

                    // Create and append <address> element
                    Element addressElement = document.createElement("address");
                    addressElement.setAttribute("id", String.valueOf(livraison.getDestination().getId())); // Assuming getDestination() and getId() exist
                    livraisonElement.appendChild(addressElement);

                    // Create and append <heureLivraison> element
                    Element heureLivraisonElement = document.createElement("heureLivraison");
                    heureLivraisonElement.setTextContent(livraison.getHeureLivraison().toString()); // Assuming getHeureLivraison() exists and returns a Time object
                    livraisonElement.appendChild(heureLivraisonElement);
                }
            }

            // Write the XML content to the file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(file);

            transformer.transform(domSource, streamResult);
        } catch (ParserConfigurationException | TransformerException e) {
            // Handle exceptions properly here
            throw new CustomXMLParsingException("Error saving tournee to XML", e);
        }
    }

}