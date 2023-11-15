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
    public default void addDelivery(ListeDeCommandes l, LocalTime heure, int numeroCoursier, Controller c, Carte carte){};

    /**
     * Ajoute une livraison à un coursier avec une heure et un index spécifiés, après le calcul des tournées..
     *
     * @param l             La liste de commandes
     * @param heure         L'heure de livraison
     * @param numeroCoursier Le numéro du coursier
     * @param index         L'index de la livraison
     * @param c             Le contrôleur
     * @param carte         La carte
     */
    public default void addDelivery(ListeDeCommandes l, LocalTime heure, int numeroCoursier, int index, Controller c, Carte carte){};

    /**
     * Ajouter une intersection pour l'ajout d'une livraison.
     *
     * @param intersection L'intersection à ajouter
     */
    public default void addIntersection(Controller c, Intersection intersection) {};
    public default void deleteDelivery(ListeDeCommandes l, int numeroCoursier, Livraison livraison, Controller c, Carte carte){};
    public default void deleteDelivery(ListeDeCommandes l, int numeroCoursier, Livraison livraison, int index, Controller c, Carte carte){};
    public default void calculerLivraisons(ListeDeCommandes l, Controller c, Carte carte) {}
    public default void modifierCoursiers(Controller c, Carte carte, int nombre) {}
    public default void undo(ListeDeCommandes l){};
    public default void redo(ListeDeCommandes l){};
    public default void loadMap(Controller c, Carte carte, ListeDeCommandes l, Stage stage){};
    public default void reset(Controller c, Carte carte) {};
    public default void loadTour(Controller c, Carte carte, Stage stage){};
    public default void saveTour(Controller c, Carte carte, Stage stage){};
    public default void unselectIntersection(Controller c){};
    public default void genererFeuilleRoute(Controller c, Carte carte){};

}