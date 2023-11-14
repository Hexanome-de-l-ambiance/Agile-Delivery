package com.example.controller;

import com.example.model.Carte;
import com.example.model.Livraison;

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
    public void execute() {
        if (carte.getListeTournees().get(numeroCouriser).getListeLivraisons().size()>0) {
            carte.removeLivraison(numeroCouriser, livraison);
        }
    }

    @Override
    public void undo() {
        carte.addLivraison(numeroCouriser, livraison);
    }
}