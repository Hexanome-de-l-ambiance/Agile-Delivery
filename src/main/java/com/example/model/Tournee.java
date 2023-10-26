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
public class Tournee {

    private LinkedList<Chemin> listeChemins;

    private int coursier;

    private double longueurTotale;

    /**
     * Default constructor
     */
    public Tournee() {
        listeChemins = new LinkedList<>();
    }

    public void calculerTournee(Carte carte, ArrayList<Intersection> livraisons) {
        TSP tsp = new TSP1();
        Graph g = new CompleteGraph(carte, livraisons);
        tsp.searchSolution(10000,g);

        Intersection entrepot = carte.getListeIntersections().get(carte.getEntrepot());
        Chemin chemin;// = Astar.calculChemin(carte, entrepot, livraisons.get(0));
        //listeChemins.add(chemin);
        for(int i=0; i < livraisons.size() -1; i++)
        {
            chemin = Astar.calculChemin(carte, livraisons.get(i), livraisons.get(i+1));
            listeChemins.add(chemin);
        }
        chemin = Astar.calculChemin(carte, livraisons.get(livraisons.size()-1), entrepot);
        listeChemins.add(chemin);
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