package com.example.controller;

import com.example.model.Carte;
import com.example.model.Intersection;
import com.example.xml.CustomXMLParsingException;
import com.example.xml.XMLOpener;
import javafx.stage.Stage;

/**
 * 
 */
public class EtatCarteChargee implements Etat {

    /**
     * Default constructor
     */
    public EtatCarteChargee() {
    }

    @Override
    public void loadMap(Controller c, Carte carte, ListeDeCommandes l, Stage stage) {
        try{
            XMLOpener.getInstance().readFile(stage, carte);
        } catch (CustomXMLParsingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void modiferCoursiers(Controller c, Carte carte, int nombre) {
        carte.setNbCoursiers(nombre);
    }

    public void addIntersection(Controller c, Intersection intersection){
        c.setEtatCourant(c.etatAjoutDestination);
        c.etatAjoutDestination.addIntersection(c, intersection);
    }

    /**
     * Charger des demandes.
     * @param c Controller instance.
     * // TODO: Add other necessary parameters if needed.
     */
    public void chargerDesDemandes(Controller c) {
        // Implement the logic to load demands.
        // If loading is successful, you may want to transition to a new state.
    }

    /**
     * Modifier le nombre de coursiers.
     * @param c Controller instance.
     * @param numberOfCouriers The new number of couriers.
     */
    public void modifierNombreDeCoursiers(Controller c, int numberOfCouriers) {
        // Implement the logic to modify the number of couriers.
    }

    /**
     * Créer une nouvelle demande.
     * @param c Controller instance.
     * // TODO: Add other necessary parameters if needed.
     */
    public void creerUneDemande(Controller c) {
        // Implement the logic to create a new request.
        // After creating a request, you may want to transition to the "Demande ajoutée" state.
    }

    // You can add other methods or transitions based on the logic you need.
}
