package com.example.controller;

import com.example.model.Carte;
import com.example.xml.CustomXMLParsingException;
import com.example.xml.XMLOpener;
import javafx.stage.Stage;

/**
 * Représente l'état initial du contrôleur.
 */
public class EtatInitial implements Etat {

    /**
     * Charge une carte à partir d'un fichier XML.
     *
     * @param c     Le contrôleur.
     * @param carte La carte à charger.
     * @param stage Le stage JavaFX.
     */
    @Override
    public void loadMap(Controller c, Carte carte, Stage stage){
        try{
            XMLOpener.getInstance().readFile(stage, carte);
            c.setEtatCourant(c.etatCarteChargee);
            System.out.printf("map loaded");
        } catch (CustomXMLParsingException e) {
        }
    }
}