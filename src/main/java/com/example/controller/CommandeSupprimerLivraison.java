package com.example.controller;

import com.example.model.Carte;
import com.example.model.Livraison;

/**
 * La classe CommandeSupprimerLivraison implémente l'interface Commande pour supprimer une livraison de la carte avant le calcul des tournées.
 */
public class CommandeSupprimerLivraison implements Commande{

    private Livraison livraison;
    private int numeroCoursier;
    private Carte carte;

    /**
     * Constructeur par défaut de la commande de suppression de livraison.
     *
     * @param livraison     La livraison à ajouter
     * @param numeroCoursier Le numéro du coursier
     * @param carte         La carte
     */
    public CommandeSupprimerLivraison(Livraison livraison, int numeroCoursier, Carte carte) {
        this.livraison = livraison;
        this.numeroCoursier = numeroCoursier;
        this.carte = carte;

    }

    /**
     * Exécuter la commande commande de suppression de livraison en supprimant la livraison à la carte.
     */
    @Override
    public void execute() {
        if (carte.getListeTournees().get(numeroCoursier).getListeLivraisons().size()>0) {
            carte.removeLivraison(numeroCoursier, livraison);

        }
    }

    /**
     * Annuler la commande d'ajout de suppression en ajoutant la livraison sur la carte.
     */
    @Override
    public void undo() {
        carte.addLivraison(numeroCoursier, livraison);
    }
}