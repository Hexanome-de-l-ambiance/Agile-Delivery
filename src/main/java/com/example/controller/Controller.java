package com.example.controller;

import com.example.model.Carte;
import com.example.model.Intersection;
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

    protected final EtatTourneeCalculee etatTourneeCalculee = new EtatTourneeCalculee();

    protected final EtatDemandeAjoutee etatDemandeAjoutee = new EtatDemandeAjoutee();

    protected void setEtatCourant(Etat etat){
        etatCourant = etat;
    }
    public Controller(Stage stage) {
        listeDeCommandes = new ListeDeCommandes();
        etatCourant = etatInitial;
        this.stage = stage;
        carte = new Carte();
    }

    public void addDelivery() {
        etatCourant.addDelivery(this, stage);
    }

    public void deleteDelivery() {
        etatCourant.deleteDelivery(this, stage);
    }

    public void calculateDelivery() {
        etatCourant.calculateDelivery(this, stage);
    }

    public void undo() {
        etatCourant.undo(listeDeCommandes);
    }

    public void redo() {
        etatCourant.redo(listeDeCommandes);
    }

    public void load() {
        etatCourant.loadMap(this, carte, listeDeCommandes, stage);
    }

    public void mouseMoved(Intersection intersection) {
        etatCourant.mouseMoved(this, carte, intersection);
    }


}