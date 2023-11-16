package com.example.controller;

/**
 * L'interface Commande définit les méthodes nécessaires pour exécuter et annuler une action.
 */
public interface Commande {

    /**
     * Exécuter la commande correspondante.
     */
    public void execute();

    /**
     * Annuler la commande précédemment exécutée.
     */
    public void undo();

}