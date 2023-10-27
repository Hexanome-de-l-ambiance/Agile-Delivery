package com.example.controller;

import java.util.LinkedList;

/**
 * 
 */
public class ListeDeCommandes {

    private LinkedList<Commande> listeDeCommandes;
    private int index;
    /**
     * Default constructor
     */
    public ListeDeCommandes() {
        index = -1;
        listeDeCommandes = new LinkedList<Commande>();
    }

    public void addCommande(Commande commande) {
        index++;
        listeDeCommandes.add(index, commande);
        commande.execute();
    }

    public void undo(){
        if (index>= 0){
            listeDeCommandes.get(index).undo();
            index--;
        }
    }

    public void redo(){
        index++;
        listeDeCommandes.get(index).execute();
    }
}