package com.example.controller;

import com.example.agiledelivery.ButtonListener;
import com.example.agiledelivery.GraphicalView;
import com.example.agiledelivery.MouseListener;
import com.example.agiledelivery.TextualView;
import com.example.model.Carte;
import com.example.model.Intersection;
import com.example.model.Livraison;
import javafx.stage.Stage;
import java.time.LocalTime;
/**
 * Le contrôleur (Controller) gère les interactions entre les états.
 */
public class Controller {



    private Carte carte;
    private Etat etatCourant;
    private Stage stage;
    private ListeDeCommandes listeDeCommandes;
    protected final EtatInitial etatInitial = new EtatInitial();
    protected final EtatCarteChargee etatCarteChargee = new EtatCarteChargee();
    protected final EtatAjoutDestination etatAjoutDestination = new EtatAjoutDestination();
    protected final EtatTourneeCalculee etatTourneeCalculee = new EtatTourneeCalculee();
    protected final EtatAjoutDestination2 etatAjoutDestination2 = new EtatAjoutDestination2();

    /**
     * Définit l'état courant du contrôleur.
     *
     * @param etat L'état courant du contrôleur
     */
    protected void setEtatCourant(Etat etat){
        etatCourant = etat;
    }

    /**
     * Obtient l'état courant du contrôleur.
     *
     * @return L'état courant du contrôleur
     */
    public Etat getEtatCourant() {
        return etatCourant;
    }

    /**
     * Constructeur du contrôleur.
     *
     * @param carte La carte utilisée dans l'application
     * @param stage La fenêtre principale de l'application
     */
    public Controller(Carte carte, Stage stage) {
        listeDeCommandes = new ListeDeCommandes();
        etatCourant = etatInitial;
        this.stage = stage;
        this.carte = carte;

    }

    /**
     * Ajouter une intersection pour l'ajout d'une livraison dans l'état courant du contrôleur.
     *
     * @param intersection L'intersection à ajouter
     */
    public void addDestination(Intersection intersection) {
        etatCourant.addIntersection(this,intersection);
    }

    /**
     * Désélectionner une intersection pour l'ajout de la livraison dans l'état courant du contrôleur.
     */
    public void unselectIntersection() { etatCourant.unselectIntersection(this);}

    /**
     * Ajoute une livraison à un coursier avant le calcul des tournées avec l'heure spécifiée dans l'état courant du contrôleur.
     *
     * @param numeroCoursier Le numéro du coursier
     * @param heure          L'heure de livraison
     */
    public void addDelivery(int numeroCoursier, int heure) {
        etatCourant.addDelivery(listeDeCommandes, LocalTime.of(heure,0,0), numeroCoursier, this, carte);
    }

    /**
     * Supprimer une livraison d'un coursier avant le calcul des tournées dans l'état courant du contrôleur.
     *
     * @param numeroCoursier Le numéro du coursier
     * @param livraison      La livraison à supprimer
     */
    public void deleteDelivery(int numeroCoursier, Livraison livraison) {
        etatCourant.deleteDelivery(listeDeCommandes, numeroCoursier, livraison, this, carte);
    }

    /**
     * Ajouter une livraison à un coursier après le calcul des tournées avec l'heure et l'index spécifiés dans l'état courant du contrôleur.
     *
     * @param numeroCoursier Le numéro du coursier
     * @param heure          L'heure de livraison
     * @param index          L'indice de la livraison
     */
    public void addDelivery(int numeroCoursier, int heure, int index) {
        etatCourant.addDelivery(listeDeCommandes, LocalTime.of(heure,0,0), numeroCoursier, index, this, carte);
    }

    /**
     * Supprimer une livraison d'un coursier après le calcul des tournées à l'indice spécifié dans l'état courant du contrôleur.
     *
     * @param numeroCoursier Le numéro du coursier
     * @param livraison      La livraison à supprimer
     * @param index          L'indice de la livraison à supprimer
     */
    public void deleteDelivery(int numeroCoursier, Livraison livraison, int index) {
        etatCourant.deleteDelivery(listeDeCommandes, numeroCoursier, livraison, index, this, carte);
    }

    /**
     * Calculer les tournées dans l'état courant du contrôleur.
     */
    public void calculateDelivery() {
        etatCourant.calculerLivraisons(listeDeCommandes,this, carte);
    }

    /**
     * Modifier le nombre de coursiers dans l'état courant du contrôleur et réinitialise la liste des commandes.
     *
     * @param nombre Le nouveau nombre de coursiers
     */
    public void modifierCoursiers(int nombre) {
        etatCourant.modifierCoursiers(this, carte, nombre);
        listeDeCommandes.reset();
    }

    /**
     * Charger la tournée dans l'état courant du contrôleur.
     */
    public void loadTour() {
        etatCourant.loadTour(this, carte, stage);
    }

    /**
     * Enregistrer la tournée dans l'état courant du contrôleur.
     */
    public void saveTour() {
        etatCourant.saveTour(this, carte, stage);
    }

    /**
     * Annuler la dernière commande dans l'état courant du contrôleur.
     */
    public void undo() {
        etatCourant.undo(listeDeCommandes);
    }

    /**
     * Refaire la dernière commande annulée dans l'état courant du contrôleur.
     */
    public void redo() {
        etatCourant.redo(listeDeCommandes);
    }

    /**
     * Charger la carte dans l'état courant du contrôleur.
     */
    public void load() {
        etatCourant.loadMap(this, carte, stage);
    }

    /**
     * Réinitialiser la carte dans l'état courant du contrôleur.
     */
    public void reset() { etatCourant.reset(this, carte);};

    /**
     * Généner la feuille de route des chemins dans l'état courant du contrôleur.
     */
    public void genererFeuilleRoute(){ etatCourant.genererFeuilleRoute(this, carte);}


}
