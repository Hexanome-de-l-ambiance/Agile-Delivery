package com.example.controller;

import com.example.model.Carte;
import com.example.model.Livraison;
import com.example.model.Tournee;


/**
 * 
 */
public class CommandeInverse implements Commande{

    private Commande commande;

    public CommandeInverse(Commande commande){
        this.commande = commande;
    }

    @Override
    public boolean execute() {
        commande.undo();
        return true;
    }


    @Override
    public void undo() {
        commande.execute();
    }
}