package com.example.model;


/**
 * L'interface Visitor définit les méthodes pour les classes en dehors du package de visiter la carte et la tournée.
 */
public interface Visitor {

    /**
     * Affiche la carte.
     *
     * @param carte La carte à afficher.
     */
    public void display(Carte carte);

    /**
     * Affiche la tournée du coursier spécifié.
     *
     * @param numeroCoursier Le numéro du coursier.
     * @param tournee        La tournée à afficher.
     */
    public void display(int numeroCoursier, Tournee tournee);

}