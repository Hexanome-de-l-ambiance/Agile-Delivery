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

    public void removeLivraison(Livraison livraison) {livraisons.remove(livraison);}

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

        Livraison entrepot = new Livraison(carte.getEntrepot(), Livraison.DEBUT_TOURNEE);
        if(livraisons.get(0).getDestination() != entrepot.getDestination()) livraisons.add(0, entrepot);

        Graph g = new CompleteGraph(carte, livraisons);

        TSP tsp = new TSP2();
        if(!tsp.searchSolution(10000,g)) {
            System.out.println("No solution found");
            return false;
        }

        Chemin chemin;
        for(int i=0 ; i<livraisons.size()-1 ; i++)
        {
            chemin = Astar.calculChemin(carte, carte.getIntersection(tsp.getSolution(i)), carte.getIntersection(tsp.getSolution(i+1)));
            LocalTime heureArrivee;
            if(i == 0) {
                heureArrivee = Livraison.DEBUT_TOURNEE.plusMinutes(chemin.getDuree().toMinutes());
            }else {
                heureArrivee = livraisons.get(i).getHeureLivraison().plusMinutes(chemin.getDuree().toMinutes() + Livraison.DUREE_LIVRAISON.toMinutes());
            }

            if(heureArrivee.isBefore(livraisons.get(i+1).getCrenauHoraire())) {
                livraisons.get(i+1).setHeureLivraison(livraisons.get(i+1).getCrenauHoraire());
            }else {
                livraisons.get(i+1).setHeureLivraison(heureArrivee);
            }
            listeChemins.add(chemin);
            longueurTotale += chemin.getLongueur();
        }
        chemin = Astar.calculChemin(carte, carte.getIntersection(tsp.getSolution(livraisons.size()-1)), entrepot.getDestination());
        entrepot.setHeureLivraison(livraisons.get(livraisons.size()-1).getHeureLivraison().plusMinutes(chemin.getDuree().toMinutes() + Livraison.DUREE_LIVRAISON.toMinutes()));
        listeChemins.add(chemin);
        longueurTotale += chemin.getLongueur();

        System.out.println("Solution found in  " + (System.currentTimeMillis() - start) + " ms");
        return true;
    }

    public void printTournee()
    {
        for(int i=0 ; i<listeChemins.size() ; i++)
        {
            System.out.println("Chemin " + i + " : " + listeChemins.get(i).getOrigin().getId() + " -> " + listeChemins.get(i).getDestination().getId() + " (" + listeChemins.get(i).getDuree().toMinutes() + " min)");
            System.out.println("Heure d'arrivée : " + livraisons.get((i+1)%listeChemins.size()).getHeureLivraison());
            System.out.println();
        }
    }

    public double getLongueurTotale() {
    	return longueurTotale;
    }


}