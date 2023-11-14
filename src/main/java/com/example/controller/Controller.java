package com.example.controller;

import com.example.model.Carte;
import com.example.model.Intersection;
import com.example.model.Livraison;
import javafx.stage.Stage;

import java.time.LocalTime;
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
    protected final EtatAjoutDestination2 etatAjoutDestination2 = new EtatAjoutDestination2();
    protected void setEtatCourant(Etat etat){
        etatCourant = etat;
    }




    public Etat getEtatCourant() {
        return etatCourant;
    }


    public Controller(Carte carte, Stage stage) {
        listeDeCommandes = new ListeDeCommandes();
        etatCourant = etatInitial;
        this.stage = stage;
        this.carte = carte;
    }

    public void addDestination(Intersection intersection) {
        etatCourant.addIntersection(this,intersection);
    }


    public void addDelivery(int numeroCoursier, int heure) {
        etatCourant.addDelivery(listeDeCommandes, LocalTime.of(heure,0,0), numeroCoursier, this, carte);
    }

    public void deleteDelivery(int numeroCoursier, Livraison livraison) {
        etatCourant.deleteDelivery(listeDeCommandes, numeroCoursier, livraison, this, carte);
    }
    public void addDelivery(int numeroCoursier, int heure, int index) {
        etatCourant.addDelivery(listeDeCommandes, LocalTime.of(heure,0,0), numeroCoursier, index, this, carte);
    }

    public void deleteDelivery(int numeroCoursier, Livraison livraison, int index) {
        etatCourant.deleteDelivery(listeDeCommandes, numeroCoursier, livraison, index, this, carte);
    }
    public void calculateDelivery() {
        etatCourant.calculerLivraisons(listeDeCommandes,this, carte);
    }
    public void modifierCoursiers(int nombre) {
        etatCourant.modifierCoursiers(this, carte, nombre);
        listeDeCommandes.reset();
    }
    public void loadTour() {
        etatCourant.loadTour(this, carte, stage);
    }

    public void saveTour() {
        etatCourant.saveTour(this, carte, stage);
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

    public void reset() { etatCourant.reset(this, carte);};

    public void unselectIntersection() { etatCourant.unselectIntersection(this);}

    public void genererFeuilleRoute(){ etatCourant.genererFeuilleRoute(this, carte);}


}
