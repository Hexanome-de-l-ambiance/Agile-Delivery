package com.example.model;

import com.example.tsp.*;
import com.example.utils.Astar;

import java.time.LocalTime;
import java.util.*;

/**
 * 
 */
public class Tournee{

    private LinkedList<Chemin> listeChemins;
    private ArrayList<Livraison> livraisons;

    private int coursier;
    private LocalTime finTournee;
    private double longueurTotale;

    /**
     * Default constructor
     */
    public Tournee() {
        listeChemins = new LinkedList<>();
        livraisons = new ArrayList<>();
    }



    public void addLivraison(Livraison livraison) {
        livraisons.add(livraison);
    }

    /**
     * Ajoute une livraison à la tournée et recalcule les horaires de livraison mais ne recalcule pas la tournée.
     * @param carte
     * @param livraison
     * @param index
     */
    public void addLivraison(Carte carte, Livraison livraison, int index) {
        if(!listeChemins.isEmpty()) {
            Livraison livraisonPrecedente = livraisons.get(index-1);
            Livraison livraisonSuivante = livraisons.get(index);

            Chemin cheminPrecedent = Astar.calculChemin(carte, livraisonPrecedente.getDestination(), livraison.getDestination());
            Chemin cheminSuivant = Astar.calculChemin(carte, livraison.getDestination(), livraisonSuivante.getDestination());
            LocalTime heureArrivee = livraisonPrecedente.getHeureLivraison().plusMinutes(cheminPrecedent.getDuree().toMinutes() + Livraison.DUREE_LIVRAISON.toMinutes());
            if(heureArrivee.isBefore(livraison.getCrenauHoraire())) {
                livraison.setHeureLivraison(heureArrivee);
            }else {
                livraison.setHeureLivraison(livraison.getCrenauHoraire());
            }

            LocalTime heureArriveeSuivante = heureArrivee.plusMinutes(cheminSuivant.getDuree().toMinutes() + Livraison.DUREE_LIVRAISON.toMinutes());
            if(heureArriveeSuivante.isBefore(livraisonSuivante.getCrenauHoraire())) {
                livraisonSuivante.setHeureLivraison(livraisonSuivante.getCrenauHoraire());
            }else {
                livraisonSuivante.setHeureLivraison(heureArriveeSuivante);
            }
        }
    }

    public void removeLivraison(Livraison livraison) {livraisons.remove(livraison);}

    /**
     * Supprime une livraison de la tournée et recalcule les horaires de livraison mais ne recalcule pas la tournée.
     * @param carte
     * @param index
     */
    public void removeLivraison(Carte carte, int index) {
        if(!listeChemins.isEmpty()) {
            Livraison livraisonPrecedente = livraisons.get(index-1);
            Livraison livraisonSuivante = livraisons.get(index+1);

            Chemin nouveaucChemin = Astar.calculChemin(carte, livraisonPrecedente.getDestination(), livraisonSuivante.getDestination());
            LocalTime heureArrivee = livraisonPrecedente.getHeureLivraison().plusMinutes(nouveaucChemin.getDuree().toMinutes() + Livraison.DUREE_LIVRAISON.toMinutes());
            if(heureArrivee.isBefore(livraisonSuivante.getCrenauHoraire())) {
                livraisonSuivante.setHeureLivraison(livraisonSuivante.getCrenauHoraire());
            }else {
                livraisonSuivante.setHeureLivraison(heureArrivee);
            }
        }
    }

    public ArrayList<Livraison> getLivraisons() {return livraisons;}
    public LinkedList<Chemin> getListeChemins() {
        return listeChemins;
    }
    public boolean calculerTournee(Carte carte) {
        long start = System.currentTimeMillis();
        listeChemins.clear();
        if(livraisons.size() == 0) {
            return false;
        }

        Graph graph = new CompleteGraph(carte, livraisons);
        int nbVertices = graph.getNbVertices();
        int startIndex = nbVertices - 1;

        TSP tsp = new TSP2(startIndex);
        if(!tsp.searchSolution(10000, graph)) {
            System.out.println("No solution found");
            return false;
        }

        Chemin chemin = Astar.calculChemin(carte, carte.getEntrepot(), carte.getIntersection(tsp.getSolution(1)));
        LocalTime heureArrivee = Livraison.DEBUT_TOURNEE.plusMinutes(chemin.getDuree().toMinutes());
        if(heureArrivee.isBefore(livraisons.get(0).getCrenauHoraire())) {
            livraisons.get(0).setHeureLivraison(livraisons.get(0).getCrenauHoraire());
        }else {
            livraisons.get(0).setHeureLivraison(heureArrivee);
        }
        listeChemins.add(chemin);
        longueurTotale += chemin.getLongueur();

        for(int i= 0 ; i < livraisons.size() -1; i++)
        {
            chemin = Astar.calculChemin(carte, carte.getIntersection(tsp.getSolution(i+1)), carte.getIntersection(tsp.getSolution(i+2)));
            heureArrivee = livraisons.get(i).getHeureLivraison().plusMinutes(chemin.getDuree().toMinutes() + Livraison.DUREE_LIVRAISON.toMinutes());

            if(heureArrivee.isBefore(livraisons.get(i+1).getCrenauHoraire())) {
                livraisons.get(i+1).setHeureLivraison(livraisons.get(i+1).getCrenauHoraire());
            }else {
                livraisons.get(i+1).setHeureLivraison(heureArrivee);
            }
            listeChemins.add(chemin);
            longueurTotale += chemin.getLongueur();
        }
        chemin = Astar.calculChemin(carte, carte.getIntersection(tsp.getSolution(livraisons.size())), carte.getEntrepot());
        this.finTournee = livraisons.get(livraisons.size()-1).getHeureLivraison().plusMinutes(chemin.getDuree().toMinutes() + Livraison.DUREE_LIVRAISON.toMinutes());
        listeChemins.add(chemin);
        longueurTotale += chemin.getLongueur();

        System.out.println("Solution found in  " + (System.currentTimeMillis() - start) + " ms");
        return true;
    }

    public void printTournee()
    {
        for(int i=0 ; i<listeChemins.size(); i++)
        {
            System.out.println("Chemin " + i + " : " + listeChemins.get(i).getOrigin().getId() + " -> " + listeChemins.get(i).getDestination().getId() + " (" + listeChemins.get(i).getDuree().toMinutes() + " min)");
            System.out.println("Heure d'arrivée : " + this.finTournee);
            System.out.println();
        }
    }

    public double getLongueurTotale() {
    	return longueurTotale;
    }


}