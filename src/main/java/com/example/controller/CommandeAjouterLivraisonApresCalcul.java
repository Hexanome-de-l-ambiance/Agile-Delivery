package com.example.controller;

import com.example.model.Carte;
import com.example.model.Livraison;

/**
 * La classe CommandeAjouterLivraisonApresCalcul implémente l'interface Commande pour ajouter une livraison à une carte après le calcul des tournées..
 */
public class CommandeAjouterLivraisonApresCalcul implements Commande{
    private Livraison livraison;
    private int index;
    private int numeroCouriser;
    private Carte carte;
    private boolean val = true;

    /**
     * Constructeur de la commande d'ajout de livraison après le calcul.
     *
     * @param livraison      La livraison à ajouter
     * @param index          L'indice où la livraison doit être ajoutée
     * @param numeroCouriser Le numéro du coursier
     * @param carte          La carte
     */
    public CommandeAjouterLivraisonApresCalcul(Livraison livraison, int index, int numeroCouriser, Carte carte) {
        this.livraison = livraison;
        this.index = index;
        this.numeroCouriser = numeroCouriser;
        this.carte = carte;
    }

    /**
     * Exécuter la commande d'ajout de livraison à l'indice spécifié.
     */
    @Override
    public void execute() {
        val = carte.addLivraison(numeroCouriser, livraison, index);
    }

    /**
     * Annuler la commande d'ajout de livraison en retirant la livraison de l'indice spécifié.
     */
    @Override
    public void undo() {
        if (val && carte.getListeTournees().get(numeroCouriser).getListeLivraisons().size()>index) {
            carte.removeLivraison(numeroCouriser, index);
        }
    }
}
