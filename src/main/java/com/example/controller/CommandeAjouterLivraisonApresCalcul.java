package com.example.controller;

import com.example.model.Carte;
import com.example.model.Livraison;

public class CommandeAjouterLivraisonApresCalcul implements Commande{
    private Livraison livraison;
    private int index;
    private int numeroCouriser;
    private Carte carte;

    public CommandeAjouterLivraisonApresCalcul(Livraison livraison, int index, int numeroCouriser, Carte carte, Controller c) {
        this.livraison = livraison;
        this.index = index;
        this.numeroCouriser = numeroCouriser;
        this.carte = carte;
    }

    @Override
    public void execute() {
        carte.addLivraison(numeroCouriser, livraison, index);
    }

    @Override
    public void undo() {
        if (carte.getListeTournees().get(numeroCouriser).getLivraisons().size()>index) {
            carte.removeLivraison(numeroCouriser, index);
        }
    }
}
