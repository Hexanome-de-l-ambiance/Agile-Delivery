package com.example.controller;

import com.example.model.Carte;
import com.example.model.Tournee;
import com.example.model.Intersection;
import com.example.model.Livraison;
import com.example.xml.CustomXMLParsingException;
import com.example.xml.XMLMaker;
import com.example.xml.XMLOpener;
import javafx.stage.Stage;

/**
 * Représente l'état lorsque la tournée est calculée.
 */
public class EtatTourneeCalculee implements Etat {

    /**
     * Ajoute une intersection à l'état d'ajout de destination après le calcul des tournées.
     *
     * @param c             Le contrôleur.
     * @param intersection  L'intersection à ajouter.
     */
    public void addIntersection(Controller c, Intersection intersection){
        c.setEtatCourant(c.etatAjoutDestination2);
        c.etatAjoutDestination2.addIntersection(c, intersection);
    }

    /**
     * Supprime une livraison à un certain index d'un coursier après le calcul des tournées.
     *
     * @param l             La liste de commandes.
     * @param numeroCoursier Le numéro du coursier.
     * @param livraison     La livraison à supprimer.
     * @param index         L'index de la livraison.
     * @param c             Le contrôleur.
     * @param carte         La carte.
     */
    public void deleteDelivery(ListeDeCommandes l, int numeroCoursier, Livraison livraison, int index, Controller c, Carte carte){
        l.addCommande(new CommandeSupprimerLivraisonApresCalcul(livraison, numeroCoursier, index, carte));
    }

    /**
     * Annule la dernière commande effectuée sur la liste de commandes.
     *
     * @param l La liste de commandes.
     */
    public void undo(ListeDeCommandes l){
        l.undo();
    }

    /**
     * Réexécute la dernière commande annulée sur la liste de commandes.
     *
     * @param l La liste de commandes.
     */
    public void redo(ListeDeCommandes l){
        l.redo();
    }

    /**
     * Réinitialise les tournées sur la carte.
     *
     * @param c     Le contrôleur.
     * @param carte La carte.
     */
    public void reset(Controller c, Carte carte){
        carte.resetTournee();
        c.setEtatCourant(c.etatCarteChargee);
    }

    /**
     * Enregistre les tournées dans un fichier XML.
     *
     * @param carte La carte concernée.
     * @param stage Le stage JavaFX.
     */
    @Override
    public void saveTour(Carte carte, Stage stage) {
        try {
            XMLMaker.getInstance().saveTourneeToXML(stage, carte);
        } catch (CustomXMLParsingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Génère une feuille de route HTML pour chaque tournées de la carte.
     *
     * @param c     Le contrôleur.
     * @param carte La carte concernée.
     */
    public void genererFeuilleRoute(Controller c, Carte carte){
        for (Tournee tournee : carte.getListeTournees().values())
        {
           tournee.genererFeuilleDeRouteHTML("feuilleDeRoute" + tournee.getCoursier() + ".html");
        }
    }

}
