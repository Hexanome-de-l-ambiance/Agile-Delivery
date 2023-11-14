package com.example.controller;

import com.example.model.Carte;
import com.example.model.Livraison;

/**
 *
 */
public class CommandeAjouterLivraison implements Commande{

    private Livraison livraison;
    private int numeroCouriser;
    private Carte carte;
    /**
     * Default constructor
     */
    public CommandeAjouterLivraison(Livraison livraison, int numeroCoursier, Carte carte) {
        this.livraison = livraison;
        this.numeroCouriser = numeroCoursier;
        this.carte = carte;
    }


    @Override
    public void execute() {
        carte.addLivraison(numeroCouriser, livraison);
    }

    @Override
    public void undo() {
        if (carte.getListeTournees().get(numeroCouriser).getListeLivraisons().size()>0) {
            carte.removeLivraison(numeroCouriser, livraison);
        }
    }
}