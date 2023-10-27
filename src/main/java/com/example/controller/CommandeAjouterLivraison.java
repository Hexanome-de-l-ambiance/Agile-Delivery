package com.example.controller;
import com.example.model.*;

/**
 * 
 */
public class CommandeAjouterLivraison implements Commande{

    /**
     * Default constructor
     */
    private Livraison livraison;
    private Tournee tournee;

    private Carte carte;
    /**
     * Default constructor
     */
    public CommandeAjouterLivraison(Livraison livraison, Tournee tournee, Carte carte) {
        this.livraison = livraison;
        this.tournee = tournee;
        this.carte = carte;
    }

    @Override
    public void execute() {
        carte.addLivraison(livraison, tournee);
    }

    @Override
    public void undo() {
        carte.removeLivraison(livraison, tournee);
    }

}