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
        listeDeCommandes = new LinkedList<>();
    }

    public void addCommande(Commande commande) {
        for(int i = 1; i < listeDeCommandes.size()-index; i++){
            listeDeCommandes.remove(index+1);
        }
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
        if(index < listeDeCommandes.size()-1){
            index++;
            listeDeCommandes.get(index).execute();
        }

    }

    public void reset(){
        index = -1;
        listeDeCommandes = new LinkedList<>();
    }
}