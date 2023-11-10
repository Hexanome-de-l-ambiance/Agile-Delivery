package com.example.controller;

import com.example.model.Carte;
import com.example.model.Livraison;

public class CommandeSupprimerLivraisonApresCalcul implements Commande{
    private Livraison livraison;
    private int index;
    private int numeroCouriser;
    private Carte carte;

    public CommandeSupprimerLivraisonApresCalcul(Livraison livraison, int numeroCouriser, int index, Carte carte) {
        this.livraison = livraison;
        this.index = index;
        this.numeroCouriser = numeroCouriser;
        this.carte = carte;
    }

    @Override
    public void execute() {
        System.out.println();
        if (carte.getListeTournees().get(numeroCouriser).getLivraisons().size() > index) {
            carte.removeLivraison(numeroCouriser, index);
        }
    }

    @Override
    public void undo() {
        carte.addLivraison(numeroCouriser, livraison, index);
    }
}
