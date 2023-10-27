package com.example.controller;
import com.example.model.*;

/**
 * 
 */
public class CommandeSupprimerLivraison implements Commande{

    /**
     * Default constructor
     */
    private Livraison livraison;

    private Tournee tournee;

    private Carte carte;
    /**
     * Default constructor
     */
    public CommandeSupprimerLivraison(Livraison livraison, Tournee tournee, Carte carte) {
        this.livraison = livraison;
        this.tournee = tournee;
        this.carte = carte;
    }

    @Override
    public void execute() {
    }

    @Override
    public void undo() {
    }

}