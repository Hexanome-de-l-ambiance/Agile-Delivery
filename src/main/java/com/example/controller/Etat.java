package com.example.controller;

import com.example.model.Carte;
import com.example.model.Intersection;
import com.example.model.Livraison;
import javafx.stage.Stage;

import java.time.LocalTime;

/**
 * L'interface Etat définit les méthodes disponibles pour chaque état du contrôleur.
 */
public interface Etat {

    /**
     * Ajoute une livraison à un coursier avec une heure spécifiée, avant le calcul des tournées.
     *
     * @param l             La liste de commandes
     * @param heure         L'heure de livraison
     * @param numeroCoursier Le numéro du coursier
     * @param c             Le contrôleur
     * @param carte         La carte
     */
    public default void addDelivery(ListeDeCommandes l, LocalTime heure, int numeroCoursier, Controller c, Carte carte){}

    /**
     * Ajoute une livraison à un coursier avec une heure et un index spécifiés, après le calcul des tournées.
     *
     * @param l             La liste de commandes
     * @param heure         L'heure de livraison
     * @param numeroCoursier Le numéro du coursier
     * @param index         L'index de la livraison
     * @param c             Le contrôleur
     * @param carte         La carte
     */
    public default void addDelivery(ListeDeCommandes l, LocalTime heure, int numeroCoursier, int index, Controller c, Carte carte){}

    /**
     * Ajoute une intersection pour l'ajout d'une livraison.
     *
     * @param c             Le contrôleur.
     * @param intersection  L'intersection à ajouter.
     */
    public default void addIntersection(Controller c, Intersection intersection) {}

    /**
     * Supprime une livraison d'un coursier avant le calcul des tournées.
     *
     * @param l             La liste de commandes.
     * @param numeroCoursier Le numéro du coursier.
     * @param livraison     La livraison à supprimer.
     * @param c             Le contrôleur.
     * @param carte         La carte.
     */
    public default void deleteDelivery(ListeDeCommandes l, int numeroCoursier, Livraison livraison, Controller c, Carte carte){}

    /**
     * Supprime une livraison d'un coursier à un index spécifié, après le calcul des tournées.
     *
     * @param l             La liste de commandes.
     * @param numeroCoursier Le numéro du coursier.
     * @param livraison     La livraison à supprimer.
     * @param index         L'index de la livraison.
     * @param c             Le contrôleur.
     * @param carte         La carte.
     */
    public default void deleteDelivery(ListeDeCommandes l, int numeroCoursier, Livraison livraison, int index, Controller c, Carte carte){}

    /**
     * Calcule les livraisons.
     *
     * @param l     La liste de commandes.
     * @param c     Le contrôleur.
     * @param carte La carte.
     */
    public default void calculerLivraisons(ListeDeCommandes l, Controller c, Carte carte) {}

    /**
     * Modifie le nombre de coursiers.
     *
     * @param c      Le contrôleur.
     * @param carte  La carte.
     * @param nombre Le nouveau nombre de coursiers.
     */
    public default void modifierCoursiers(Controller c, Carte carte, int nombre) {}

    /**
     * Annule la dernière commande effectuée.
     *
     * @param l La liste de commandes.
     */
    public default void undo(ListeDeCommandes l){}

    /**
     * Refaire la commande précédemment annulée.
     *
     * @param l La liste de commandes.
     */
    public default void redo(ListeDeCommandes l){}

    /**
     * Charge une carte.
     *
     * @param c     Le contrôleur.
     * @param carte La carte à charger.
     * @param stage Le stage JavaFX.
     */
    public default void loadMap(Controller c, Carte carte, Stage stage){}

    /**
     * Réinitialise la carte.
     *
     * @param c     Le contrôleur.
     * @param carte La carte à réinitialiser.
     */
    public default void reset(Controller c, Carte carte) {}

    /**
     * Charge une tournée.
     *
     * @param carte La carte.
     * @param stage Le stage JavaFX.
     */
    public default void loadTour(Carte carte, Stage stage){}

    /**
     * Enregistre une tournée.
     *
     * @param carte La carte.
     * @param stage Le stage JavaFX.
     */
    public default void saveTour(Carte carte, Stage stage){}

    /**
     * Désélectionne une intersection pour l'ajout.
     *
     * @param c Le contrôleur.
     */
    public default void unselectIntersection(Controller c){}

    /**
     * Génère une feuille de route.
     *
     * @param c     Le contrôleur.
     * @param carte La carte.
     * @param stage Le stage JavaFX.
     */
    public default void genererFeuilleRoute(Controller c, Carte carte, Stage stage){}

}