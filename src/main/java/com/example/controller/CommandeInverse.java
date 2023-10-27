package com.example.controller;

import com.example.model.*;

/**
 * 
 */
public class CommandeInverse implements Commande{

    /**
     * Default constructor
     */
    private Commande commande;
    /**
     * Default constructor
     */
    public CommandeInverse(Commande commande){
        this.commande = commande;
    }

    @Override
    public void execute() {
        commande.undo();
    }

    @Override
    public void undo() {
        commande.execute();
    }

}