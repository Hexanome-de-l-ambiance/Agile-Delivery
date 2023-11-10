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
            return true;
        }

        HashMap<Long, Livraison> livraisonsMap = new HashMap<>();
        for(Livraison livraison : livraisons) {
            livraisonsMap.put(livraison.getDestination().getId(), livraison);
        }

        Graph graph = new CompleteGraph(carte, livraisons);
        int nbVertices = graph.getNbVertices();
        int startIndex = nbVertices - 1;

        TSP tsp = new TSP2(startIndex);
        if(!tsp.searchSolution(10000, graph)) {
            System.out.println("No solution found");
            return false;
        }


        Chemin chemin;
        LocalTime heureArrivee;

        for(int i=0; i < nbVertices -1; i++){
            chemin = Astar.calculChemin(carte, carte.getIntersection(tsp.getSolution(i)), carte.getIntersection(tsp.getSolution(i+1)));
            if(i == 0) {
                heureArrivee = Livraison.DEBUT_TOURNEE.plusMinutes(chemin.getDuree().toMinutes());
            } else {
                heureArrivee = listeChemins.get(i-1).getHeureArrivee().plusMinutes(chemin.getDuree().toMinutes() + Livraison.DUREE_LIVRAISON.toMinutes());
            }

            LocalTime creaneauHoraire = livraisonsMap.get(tsp.getSolution(i+1)).getCrenauHoraire();
            if(heureArrivee.isBefore(creaneauHoraire)) {
                chemin.setHeureArrivee(creaneauHoraire);
            }else {
                chemin.setHeureArrivee(heureArrivee);
            }
            listeChemins.add(chemin);
        }
        chemin = Astar.calculChemin(carte, carte.getIntersection(tsp.getSolution(nbVertices-1)), carte.getEntrepot());
        heureArrivee = listeChemins.get(nbVertices-2).getHeureArrivee().plusMinutes(chemin.getDuree().toMinutes() + Livraison.DUREE_LIVRAISON.toMinutes());
        chemin.setHeureArrivee(heureArrivee);
        listeChemins.add(chemin);

        System.out.println("Solution found in  " + (System.currentTimeMillis() - start) + " ms");
        printTournee();
        return true;
    }

    public void printTournee()
    {
        for(int i=0 ; i<listeChemins.size(); i++)
        {
            System.out.println("Chemin " + i + " : " + listeChemins.get(i).getOrigin().getId() + " -> " + listeChemins.get(i).getDestination().getId() + " (" + listeChemins.get(i).getDuree().toMinutes() + " min)");
            System.out.println("Heure d'arrivée : " + listeChemins.get(i).getHeureArrivee());
            System.out.println();
        }
    }

    public double getLongueurTotale() {
    	return longueurTotale;
    }

    public void clearListeChemins(){
        listeChemins.clear();
    }
}