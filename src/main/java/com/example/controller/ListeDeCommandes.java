package com.example.controller;

import java.util.LinkedList;

/**
 * Une liste de commandes permettant de gérer l'historique des commandes pour l'annulation et la réfaite.
 */
public class ListeDeCommandes {

    private LinkedList<Commande> listeDeCommandes;
    private int index;
    /**
     * Constructeur par défaut initialisant la liste de commandes.
     */
    public ListeDeCommandes() {
        index = -1;
        listeDeCommandes = new LinkedList<>();
    }

    /**
     * Ajoute une commande à la liste et l'exécute, effaçant les commandes futures.
     *
     * @param commande La commande à ajouter et à exécuter.
     */
    public void addCommande(Commande commande) {
        for(int i = 1; i < listeDeCommandes.size()-index; i++){
            listeDeCommandes.remove(index+1);
        }
        index++;
        listeDeCommandes.add(index, commande);
        commande.execute();
    }

    /**
     * Annule la dernière commande exécutée dans la liste.
     */
    public void undo(){
        if (index>= 0){
            listeDeCommandes.get(index).undo();
            index--;
        }
    }

    /**
     * Réexécute la dernière commande annulée dans la liste.
     */
    public void redo(){
        if(index < listeDeCommandes.size()-1){
            index++;
            listeDeCommandes.get(index).execute();
        }

    }

    /**
     * Réinitialise la liste de commandes, effaçant l'historique.
     */
    public void reset(){
        index = -1;
        listeDeCommandes = new LinkedList<>();
    }
}