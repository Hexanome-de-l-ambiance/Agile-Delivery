package com.example.controller;

import com.example.model.Carte;
import com.example.model.Intersection;
import com.example.model.Livraison;
import com.example.model.Tournee;

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
    public boolean execute() {
        return carte.addLivraison(numeroCouriser, livraison);
    }

    @Override
    public void undo() {
        if (carte.getListeTournees().get(numeroCouriser).getLivraisons().size()>0) {
            carte.removeLivraison(numeroCouriser, livraison);
        }
    }
}