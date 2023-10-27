package com.example.controller;

import com.example.model.Carte;
import com.example.model.Intersection;
import com.example.model.Livraison;
import com.example.model.Tournee;

/**
 *
 */
public class CommandeAjouterLivraison implements Commande{

    private Intersection livraison;
    private Tournee tournee;

    private Carte carte;
    /**
     * Default constructor
     */
    public CommandeAjouterLivraison(Intersection livraison, Tournee tournee, Carte carte) {
        this.livraison = livraison;
        this.tournee = tournee;
        this.carte = carte;
    }

    @Override
    public void execute() {
        tournee.addLivraison(livraison);
    }

    @Override
    public void undo() {
        if (tournee.getLivraisons().size()>0) {
            tournee.removeLivraison(livraison);
        }
    }
}