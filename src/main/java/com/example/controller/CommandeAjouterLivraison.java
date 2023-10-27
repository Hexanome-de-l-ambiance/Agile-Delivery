package com.example.controller;

import com.example.model.Carte;
import com.example.model.Livraison;
import com.example.model.Tournee;

/**
 *
 */
public class CommandeAjouterLivraison implements Commande{

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