package com.example.controller;

import com.example.model.Carte;
import com.example.model.Livraison;

/**
 * La classe CommandeSupprimerLivraisonApresCalcul implémente l'interface Commande pour supprimer une livraison d'une carte après le calcul.
 */
public class CommandeSupprimerLivraisonApresCalcul implements Commande{
    private Livraison livraison;
    private int index;
    private int numeroCouriser;
    private Carte carte;

    /**
     * Constructeur de la commande de suppression de livraison après le calcul.
     *
     * @param livraison      La livraison à supprimer
     * @param numeroCouriser Le numéro du coursier associé à la livraison
     * @param index          L'indice de la livraison à supprimer
     * @param carte          La carte
     */
    public CommandeSupprimerLivraisonApresCalcul(Livraison livraison, int numeroCouriser, int index, Carte carte) {
        this.livraison = livraison;
        this.index = index;
        this.numeroCouriser = numeroCouriser;
        this.carte = carte;
    }

    /**
     * Exécuter la commande de suppression de livraison après le calcul en retirant la livraison de la carte à l'indice spécifié.
     */
    @Override
    public void execute() {
        if (carte.getListeTournees().get(numeroCouriser).getListeLivraisons().size() > index) {
            carte.removeLivraison(numeroCouriser, index);
        }
    }

    /**
     * Annuler la commande de suppression de livraison après le calcul en ajoutant la livraison à la carte à l'indice spécifié.
     */
    @Override
    public void undo() {
        carte.addLivraison(numeroCouriser, livraison, index);
    }
}
