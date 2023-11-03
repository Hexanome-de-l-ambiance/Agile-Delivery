package com.example.controller;
import com.example.model.*;

import com.example.model.Carte;
import com.example.model.Intersection;
import com.example.model.Livraison;
import com.example.model.Tournee;

/**
 * 
 */
public class CommandeSupprimerLivraison implements Commande{

    private Livraison livraison;

    private Tournee tournee;

    private Carte carte;

    public CommandeSupprimerLivraison(Livraison livraison, Tournee tournee, Carte carte) {
        this.livraison = livraison;
        this.tournee = tournee;
        this.carte = carte;

    }

    @Override
    public void execute() {
        tournee.removeLivraison(livraison);
    }

    @Override
    public void undo() {
        tournee.addLivraison(livraison);
    }
}