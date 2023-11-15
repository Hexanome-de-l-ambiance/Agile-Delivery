package com.example.controller;

import com.example.model.Carte;
import com.example.model.Livraison;

/**
 * La classe CommandeAjouterLivraison implémente l'interface Commande pour ajouter une livraison à une carte avant le calcul des tournées.
 */
public class CommandeAjouterLivraison implements Commande{

    private Livraison livraison;
    private int numeroCouriser;
    private Carte carte;

    /**
     * Constructeur par défaut de la commande d'ajout de livraison.
     *
     * @param livraison     La livraison à ajouter
     * @param numeroCoursier Le numéro du coursier
     * @param carte         La carte
     */
    public CommandeAjouterLivraison(Livraison livraison, int numeroCoursier, Carte carte) {
        this.livraison = livraison;
        this.numeroCouriser = numeroCoursier;
        this.carte = carte;
    }

    /**
     * Exécuter la commande d'ajout de livraison en ajoutant la livraison à la carte.
     */
    @Override
    public void execute() {
        carte.addLivraison(numeroCouriser, livraison);
    }

    /**
     * Annuler la commande d'ajout de livraison en retirant la livraison
     */
    @Override
    public void undo() {
        if (carte.getListeTournees().get(numeroCouriser).getListeLivraisons().size()>0) {
            carte.removeLivraison(numeroCouriser, livraison);
        }
    }
}