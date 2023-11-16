package com.example.controller;

import com.example.model.Carte;
import com.example.model.Intersection;
import com.example.model.Livraison;

import java.time.LocalTime;
/**
 * La classe implémentant l'interface représente l'état pour l'ajout d'une destination après le calcul des tournées.
 */
public class EtatAjoutDestination2 implements Etat{
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
     * @param index          L'index de la livraison.
     * @param c              Le contrôleur.
     * @param carte          La carte.
     */
    public void addDelivery(ListeDeCommandes l, LocalTime heure, int numeroCoursier, int index, Controller c, Carte carte) {
        l.addCommande(new CommandeAjouterLivraisonApresCalcul(new Livraison(intersection, heure), index, numeroCoursier, carte));
        c.setEtatCourant(c.etatTourneeCalculee);
    }

    /**
     * Désélectionne une intersection de l'état actuel.
     *
     * @param c Le contrôleur associé.
     */
    public void unselectIntersection(Controller c){c.setEtatCourant(c.etatTourneeCalculee);}

}
