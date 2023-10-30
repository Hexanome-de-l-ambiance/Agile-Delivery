package com.example.controller;

import com.example.model.Carte;
import com.example.model.Intersection;
import javafx.stage.Stage;

/**
 *
 */
public interface Etat {

    public default void addDelivery(ListeDeCommandes l, int numeroCoursier, Controller c, Carte carte){};

    public default void addIntersection(Controller c, Intersection intersection) {};

    public default void deleteDelivery(Controller c, Stage stage){};

    public default void calculerLivraisons(Controller c, Carte carte) {}

    public default void undo(ListeDeCommandes l){};

    public default void redo(ListeDeCommandes l){};

    public default void loadMap(Controller c, Carte carte, ListeDeCommandes l, Stage stage){};

    public default void mouseMoved(Controller c, Carte carte, Intersection intersection){};

}