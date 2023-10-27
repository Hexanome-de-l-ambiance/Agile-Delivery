package com.example.model;

import com.example.tsp.CompleteGraph;
import com.example.tsp.Graph;
import com.example.tsp.TSP;
import com.example.tsp.TSP1;
import com.example.utils.Astar;

import java.util.*;

/**
 * 
 */
public class Tournee{

    private LinkedList<Chemin> listeChemins;
    private ArrayList<Intersection> livraisons;

    private int coursier;

    private double longueurTotale;

    /**
     * Default constructor
     */
    public Tournee() {
        listeChemins = new LinkedList<>();
        livraisons = new ArrayList<>();
    }

    public void addLivraison(Intersection livraison) {
        livraisons.add(livraison);
    }

    public LinkedList<Chemin> getListeChemins() {
        return listeChemins;
    }

    public void calculerTournee(Carte carte) {
        Intersection entrepot = carte.getListeIntersections().get(carte.getEntrepot());
        livraisons.add(0, entrepot);

        long start = System.currentTimeMillis();
        Graph g = new CompleteGraph(carte, livraisons);
        System.out.println("Graph created in " + (System.currentTimeMillis() - start) + " ms");

        TSP tsp = new TSP1();
        start = System.currentTimeMillis();
        tsp.searchSolution(10000,g);
        System.out.println("TSP solved in " + (System.currentTimeMillis() - start) + " ms");

        Chemin chemin;
        start = System.currentTimeMillis();
        for(int i=0; i < livraisons.size() -1; i++)
        {
            chemin = Astar.calculChemin(carte, livraisons.get(i), livraisons.get(i+1));
            listeChemins.add(chemin);
        }
        chemin = Astar.calculChemin(carte, livraisons.get(livraisons.size()-1), entrepot);
        listeChemins.add(chemin);
        System.out.println("Astar solved in " + (System.currentTimeMillis() - start) + " ms");
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