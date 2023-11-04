package com.example.controller;

import com.example.model.Carte;
import com.example.model.Intersection;
import com.example.model.Livraison;
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
    protected final EtatAjoutDestination etatAjoutDestination = new EtatAjoutDestination();
    protected final EtatTourneeCalculee etatTourneeCalculee = new EtatTourneeCalculee();
    protected final EtatDemandeAjoutee etatDemandeAjoutee = new EtatDemandeAjoutee();

    protected void setEtatCourant(Etat etat){
        etatCourant = etat;
    }
    public Controller(Carte carte, Stage stage) {
        listeDeCommandes = new ListeDeCommandes();
        etatCourant = etatInitial;
        this.stage = stage;
        this.carte = carte;
    }

    public void addDestination(Intersection intersection) {
        etatCourant.addIntersection(this, intersection);
    }


    public void addDelivery(int numeroCoursier, int heure) {
        etatCourant.addDelivery(listeDeCommandes, heure, numeroCoursier, this, carte);
    }

    public void deleteDelivery(int numeroCoursier, Livraison livraison) {
        etatCourant.deleteDelivery(listeDeCommandes, numeroCoursier, livraison, this, carte);
    }

    public void calculateDelivery() {
        etatCourant.calculerLivraisons(this, carte);
    }
    public void modiferCoursiers(int nombre) {
        etatCourant.modiferCoursiers(this, carte, nombre);
        listeDeCommandes.reset();
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
