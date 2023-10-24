package com.example.controller;

import com.example.agiledelivery.Window;
import com.example.model.Carte;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 */
public class Controller {

    /**
     * Default constructor
     */
    private Carte carte;
    private Etat etatCourant;
    private Stage stage;
    private ListeDeCommandes listeDeCommandes;
    protected final EtatInitial etatInitial = new EtatInitial();
    protected final EtatCarteChargee etatCarteChargee = new EtatCarteChargee();

    public Controller(Stage stage) {
        listeDeCommandes = new ListeDeCommandes();
        etatCourant = etatInitial;
        this.stage = stage;
        carte = new Carte();
    }

    public void chargerCarte() {
        etatCourant.load(carte, listeDeCommandes, stage);
    }
}