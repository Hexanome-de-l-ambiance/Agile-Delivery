package com.example.model;

import com.example.tsp.*;
import com.example.utils.Astar;

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

    public ArrayList<Livraison> getLivraisons(){return livraisons;}
    public LinkedList<Chemin> getListeChemins() {
        return listeChemins;
    }

    public void calculerTournee(Carte carte) {
        listeChemins.clear();
        Intersection entrepot = carte.getEntrepot();
        if(livraisons.size() == 0) return;

        livraisons.add(0, new Livraison(entrepot, Livraison.DEBUT_TOURNEE));

        long start = System.currentTimeMillis();
        Graph g = new CompleteGraph(carte, livraisons);
        System.out.println("Graph created in " + (System.currentTimeMillis() - start) + " ms");

        TSP tsp = new TSP2();
        start = System.currentTimeMillis();
        tsp.searchSolution(10000,g);
        System.out.println("TSP solved in " + (System.currentTimeMillis() - start) + " ms");

        Chemin chemin;
        start = System.currentTimeMillis();
        for(int i=0 ; i<livraisons.size()-1 ; i++)
        {
            chemin = Astar.calculChemin(carte, carte.getIntersection(tsp.getSolution(i)), carte.getIntersection(tsp.getSolution(i+1)));
            listeChemins.add(chemin);
        }
        chemin = Astar.calculChemin(carte, carte.getIntersection(tsp.getSolution(livraisons.size()-1)), entrepot);
        listeChemins.add(chemin);

        System.out.println("Chemins solved in " + (System.currentTimeMillis() - start) + " ms");
        longueurTotale = tsp.getSolutionCost();
    }

    public void printTournee()
    {
        for(Chemin chemin : listeChemins)
        {
            System.out.println("Chemin : ");
            for(Segment segment : chemin.getListeSegments())
            {
                System.out.println(segment.getOrigin().getId() + " -> " + segment.getDestination().getId());
            }
        }
    }

    public double getLongueurTotale() {
    	return longueurTotale;
    }


}