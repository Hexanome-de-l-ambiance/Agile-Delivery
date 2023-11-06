package com.example.xml;

import com.example.model.Carte;
import com.example.model.Livraison;
import com.example.model.Tournee;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
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

            // Create root element <tournee>
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
                for (Livraison livraison : livraisonsList) {
                    Element livraisonElement = document.createElement("livraison");
                    livraisonsElement.appendChild(livraisonElement);

                    // Assuming that Livraison has an address ID and a delivery time
                    Element addressElement = document.createElement("address");
                    addressElement.setAttribute("id", String.valueOf(livraison.getDestination().getId())); // Assuming getAddressId() exists
                    livraisonElement.appendChild(addressElement);

                    Element heureLivraisonElement = document.createElement("heureLivraison");
                    heureLivraisonElement.setAttribute("heure", livraison.getCrenauHoraire().toString()); // Assuming getHeureLivraison() exists
                    livraisonElement.appendChild(heureLivraisonElement);
                }
            }

            // Save the XML content to the file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(file);

            transformer.transform(domSource, streamResult);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }
    }
}
