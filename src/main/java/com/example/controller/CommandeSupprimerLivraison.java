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
    private int numeroCouriser;
    private Carte carte;

    public CommandeSupprimerLivraison(Livraison livraison, int numeroCouriser, Carte carte) {
        this.livraison = livraison;
        this.numeroCouriser = numeroCouriser;
        this.carte = carte;

    }

    @Override
    public boolean execute() {
        if (carte.getListeTournees().get(numeroCouriser).getLivraisons().size()>0) {
            carte.removeLivraison(numeroCouriser, livraison);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void undo() {
        carte.addLivraison(numeroCouriser, livraison);
    }
}