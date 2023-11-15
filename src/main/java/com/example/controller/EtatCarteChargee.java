package com.example.controller;

import com.example.model.Carte;
import com.example.model.Intersection;
import com.example.model.Livraison;
import com.example.xml.CustomXMLParsingException;
import com.example.xml.XMLOpener;
import javafx.stage.Stage;

/**
 * Cette classe représente l'état du contrôleur lorsque la carte est chargée.
 */
public class EtatCarteChargee implements Etat {

    /**
     * Charge une carte à partir d'un fichier XML.
     *
     * @param c     Le contrôleur.
     * @param carte La carte à charger.
     * @param stage Le stage JavaFX.
     */
    public void loadMap(Controller c, Carte carte, Stage stage) {
        try{
            XMLOpener.getInstance().readFile(stage, carte);
        } catch (CustomXMLParsingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Ajoute une intersection à l'état actuel.
     *
     * @param c             Le contrôleur associé.
     * @param intersection  L'intersection à ajouter.
     */
    public void addIntersection(Controller c, Intersection intersection){
        c.setEtatCourant(c.etatAjoutDestination);
        c.etatAjoutDestination.addIntersection(c, intersection);
    }

    /**
     * Calcule les tournées à partir de la carte.
     *
     * @param l     La liste de commandes.
     * @param c     Le contrôleur.
     * @param carte La carte.
     */
    public void calculerLivraisons(ListeDeCommandes l, Controller c, Carte carte){
        carte.calculerTournees();
        c.setEtatCourant(c.etatTourneeCalculee);
        l.reset();
    }

    /**
     * Supprime une livraison d'un coursier en ajoutant une commande de suppression à la liste de commandes.
     *
     * @param l             La liste de commandes.
     * @param numeroCoursier Le numéro du coursier.
     * @param livraison     La livraison à supprimer.
     * @param c             Le contrôleur.
     * @param carte         La carte concernée.
     */
    public void deleteDelivery(ListeDeCommandes l, int numeroCoursier, Livraison livraison, Controller c, Carte carte){
        l.addCommande(new CommandeSupprimerLivraison(livraison, numeroCoursier, carte));
    };

    /**
     * Modifie le nombre de coursiers sur la carte.
     *
     * @param c      Le contrôleur.
     * @param carte  La carte.
     * @param nombre Le nouveau nombre de coursiers.
     */
    public void modifierCoursiers(Controller c, Carte carte, int nombre) {
        carte.setNbCoursiers(nombre);
    }

    /**
     * Charge les tournées à partir d'un fichier XML.
     *
     * @param carte La carte.
     * @param stage Le stage JavaFX.
     */
    public void loadTour(Carte carte, Stage stage) {
        try{
            XMLOpener.getInstance().loadTour(stage, carte);
        } catch (CustomXMLParsingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Enregistre les tournées dans un fichier XML.
     *
     * @param carte La carte concernée.
     * @param stage Le stage JavaFX.
     */
    public void saveTour(Carte carte, Stage stage) {
        try {
            XMLOpener.getInstance().saveTour(stage, carte);
        } catch (CustomXMLParsingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Annule la dernière commande effectuée sur la liste de commandes.
     *
     * @param l La liste de commandes.
     */
    public void undo(ListeDeCommandes l) {
        l.undo();
    }

    /**
     * Réexécute la dernière commande annulée sur la liste de commandes.
     *
     * @param l La liste de commandes.
     */
    public void redo(ListeDeCommandes l){
        l.redo();
    }

    /**
     * Réinitialise les tournées sur la carte.
     *
     * @param c     Le contrôleur.
     * @param carte La carte.
     */
    public void reset(Controller c, Carte carte){
        carte.resetTournee();
    }
}
