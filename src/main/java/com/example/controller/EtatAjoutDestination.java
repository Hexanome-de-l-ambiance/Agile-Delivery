package com.example.controller;

import com.example.model.Carte;
import com.example.model.Intersection;
import com.example.model.Livraison;

import java.time.LocalTime;
/**
 * La classe implémentant l'interface représente l'état pour l'ajout d'une destination avant le calcul des tournées.
 */
public class EtatAjoutDestination implements Etat{
    Intersection intersection;

    /**
     * Ajoute une intersection à l'état actuel.
     *
     * @param c             Le contrôleur associé.
     * @param intersection  L'intersection à ajouter.
     */
    public void addIntersection(Controller c, Intersection intersection) {
        this.intersection = intersection;
    }

    /**
     * Ajoute une commande d'ajout à la liste de commandes.
     *
     * @param l              La liste de commandes.
     * @param heure          L'heure de livraison.
     * @param numeroCoursier Le numéro du coursier.
     * @param c              Le contrôleur.
     * @param carte          La carte.
     */
    public void addDelivery(ListeDeCommandes l, LocalTime heure, int numeroCoursier, Controller c, Carte carte) {

        l.addCommande(new CommandeAjouterLivraison(new Livraison(intersection, heure), numeroCoursier, carte));
        c.setEtatCourant(c.etatCarteChargee);
    }

    /**
     * Désélectionne l'intersection de l'état actuel.
     *
     * @param c Le contrôleur associé.
     */
    public void unselectIntersection(Controller c){c.setEtatCourant(c.etatCarteChargee);}

}
