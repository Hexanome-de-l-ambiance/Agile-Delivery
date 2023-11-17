package com.example.model;

import com.example.tsp.*;
import com.example.utils.Astar;
import com.example.xml.DirectoryMaker;

import java.time.Duration;
import java.time.LocalTime;
import java.util.*;
import java.time.format.DateTimeFormatter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static java.lang.Math.round;

/**
 * Représente une tournée effectuée par un coursier, composée de livraisons et de chemins.
 */
public class Tournee{

    /**
     * La liste des chemins de la tournée.
     */
    private LinkedList<Chemin> listeChemins;

    /**
     * La liste des livraisons de la tournée.
     */
    private ArrayList<Livraison> listeLivraisons;

    /**
     * Le numéro du coursier qui effectue la tournée.
     */
    private Integer coursier;

    /**
     * L'heure de fin de la tounrée
     */
    private LocalTime heureFinTournee;

    /**
     * Constructeur par défaut pour une tournée.
     *
     * @param c Le numéro du coursier associé à cette tournée.
     */
    public Tournee(int c) {
        listeChemins = new LinkedList<>();
        listeLivraisons = new ArrayList<>();
        heureFinTournee = Livraison.DEBUT_TOURNEE;
        coursier = c;
    }

    public int getCoursier(){
        return coursier;
    }

    /**
     * Ajoute une livraison à la liste de livraisons avant le calcul de la tournée.
     * @param livraison à ajouter à la tournée.
     */
    public void addLivraison(Livraison livraison) {
        listeLivraisons.add(livraison);
    }

    /**
     * Ajoute une livraison à la tournée après le calcul des tournées et met à jour les horaires de livraison suivants sans recalculer la tournée.
     * @param carte sur laquelle se trouve la livraison.
     * @param livraison a ajoutée à la tournée.
     * @param index indice de la livraison dans la tournée. Si <code>index</code> vaut <code>livraisons.size()</code> alors la livraison est ajoutée à la fin de la tournée.
     * @return <code>true</code> si la livraison a pu être ajoutée à la tournée, <code>false</code> sinon.
     */
    public boolean addLivraison(Carte carte, Livraison livraison, int index) {
        if(index < 0 || index > listeLivraisons.size()) {
            return false;
        }

        Chemin cheminPrecedent;
        Chemin cheminSuivant;
        LocalTime heureArrivee;
        if(listeChemins.isEmpty() && listeLivraisons.size() > 0) return false;

        if(index == 0)
        {
            cheminPrecedent = Astar.calculChemin(carte, carte.getEntrepot(), livraison.getDestination());
            if(cheminPrecedent == null) {
                return false;
            }
            heureArrivee = Livraison.DEBUT_TOURNEE.plusMinutes(cheminPrecedent.getDuree().toMinutes());
        } else {
            Livraison livraisonPrecedente = listeLivraisons.get(index-1);
            cheminPrecedent = Astar.calculChemin(carte, livraisonPrecedente.getDestination(), livraison.getDestination());
            if(cheminPrecedent == null) {
                return false;
            }
            heureArrivee = livraisonPrecedente.getHeureLivraison().plusMinutes(cheminPrecedent.getDuree().toMinutes() + Livraison.DUREE_LIVRAISON.toMinutes());
        }

        listeLivraisons.add(index, livraison);
        if(listeChemins.isEmpty()) {
            listeChemins.add(cheminPrecedent);
        } else {
            listeChemins.remove(index);
            listeChemins.add(index, cheminPrecedent);
        }


        if(index == listeLivraisons.size()-1)
        {
            cheminSuivant = Astar.calculChemin(carte, livraison.getDestination(), carte.getEntrepot());
            listeChemins.addLast(cheminSuivant);
        } else {
            cheminSuivant = Astar.calculChemin(carte, livraison.getDestination(), listeLivraisons.get(index + 1).getDestination());
            listeChemins.add(index + 1, cheminSuivant);
        }

        for(int i = index; i< listeLivraisons.size(); i++) {
            listeLivraisons.get(i).setHeureLivraison(heureArrivee);
            cheminSuivant = listeChemins.get(i+1);
            heureArrivee = listeLivraisons.get(i).getHeureLivraison().plusMinutes(cheminSuivant.getDuree().toMinutes() + Livraison.DUREE_LIVRAISON.toMinutes());
        }
        heureFinTournee = heureArrivee;
        return true;
    }

    /**
     * Supprime une livraison de la liste de livraisons avant le calcul de la tournée.
     * @param livraison
     */
    public void removeLivraison(Livraison livraison) {
        listeLivraisons.remove(livraison);}

    /**
     * Supprime une livraison de la tournée après le calcul des tournées et recalcule les horaires de livraison suivants sans recalculer la tournée.
     * @param carte sur laquelle se trouve la livraison.
     * @param index indice de la livraison dans la tournée à retirer.
     */
    public void removeLivraison(Carte carte, int index) {
        if(listeChemins.isEmpty() || index < 0 || index >= listeLivraisons.size()) {
            return;
        }

        if(listeLivraisons.size() == 1) {
            listeLivraisons.clear();
            listeChemins.clear();
            return;
        }
        Chemin nouveauChemin;
        LocalTime heureArrivee;
        if(index == listeLivraisons.size() - 1) {
            nouveauChemin = Astar.calculChemin(carte, listeLivraisons.get(index - 1).getDestination(), carte.getEntrepot());
            heureFinTournee = listeLivraisons.get(index - 1).getHeureLivraison().plusMinutes(nouveauChemin.getDuree().toMinutes() + Livraison.DUREE_LIVRAISON.toMinutes());
            listeLivraisons.remove(index);
            listeChemins.removeLast();
            listeChemins.set(listeChemins.size()-1, nouveauChemin);
        } else {
            if(index == 0) {
                nouveauChemin = Astar.calculChemin(carte, carte.getEntrepot(), listeLivraisons.get(1).getDestination());
                heureArrivee = Livraison.DEBUT_TOURNEE.plusMinutes(nouveauChemin.getDuree().toMinutes());
            }else {
                nouveauChemin = Astar.calculChemin(carte, listeLivraisons.get(index - 1).getDestination(), listeLivraisons.get(index + 1).getDestination());
                heureArrivee = listeLivraisons.get(index - 1).getHeureLivraison().plusMinutes(nouveauChemin.getDuree().toMinutes() + Livraison.DUREE_LIVRAISON.toMinutes());
            }
            listeLivraisons.remove(index);
            listeChemins.remove(index);
            listeChemins.set(index, nouveauChemin);

            Chemin cheminSuivant;
            for(int i = index; i< listeLivraisons.size(); i++) {
                listeLivraisons.get(i).setHeureLivraison(heureArrivee);
                cheminSuivant = listeChemins.get(i+1);
                heureArrivee = listeLivraisons.get(i).getHeureLivraison().plusMinutes(cheminSuivant.getDuree().toMinutes() + Livraison.DUREE_LIVRAISON.toMinutes());
            }
            heureFinTournee = heureArrivee;
        }
    }

    /**
     * Calcule la tournée pour les livraisons.
     *
     * @param carte La carte où se déroule la tournée.
     * @return <code>true</code> si le calcul de la tournée s'est déroulé avec succès, sinon <code>false</code>.
     */
    public boolean calculerTournee(Carte carte) {
        long start = System.currentTimeMillis();
        listeChemins.clear();
        if(listeLivraisons.size() == 0) {
            return true;
        }

        Graph graph = new CompleteGraph(carte, listeLivraisons);
        int nbVertices = graph.getNbVertices();
        int startIndex = nbVertices - 1;

        TSP tsp = new TSP2(startIndex);
        if(!tsp.searchSolution(10000, graph)) {
            return false;
        }
        updateLivraisonsOrder(tsp.getSolutions());

        Chemin chemin = Astar.calculChemin(carte, carte.getEntrepot(), listeLivraisons.get(0).getDestination());
        LocalTime heureArrivee = Livraison.DEBUT_TOURNEE.plusMinutes(chemin.getDuree().toMinutes());
        if(heureArrivee.isBefore(listeLivraisons.get(0).getCreneauHoraire())) {
            listeLivraisons.get(0).setHeureLivraison(listeLivraisons.get(0).getCreneauHoraire());
            listeLivraisons.get(0).setEtat(Livraison.Etat.EN_AVANCE);
        }else {
            listeLivraisons.get(0).setHeureLivraison(heureArrivee);
        }
        listeChemins.add(chemin);

        for(int i = 0; i < listeLivraisons.size() -1; i++){
            chemin = Astar.calculChemin(carte, listeLivraisons.get(i).getDestination(), listeLivraisons.get(i+1).getDestination());
            heureArrivee = listeLivraisons.get(i).getHeureLivraison().plusMinutes(chemin.getDuree().toMinutes() + Livraison.DUREE_LIVRAISON.toMinutes());
            listeLivraisons.get(i+1).setHeureLivraison(heureArrivee);
            listeChemins.add(chemin);
        }
        chemin = Astar.calculChemin(carte, listeLivraisons.get(listeLivraisons.size()-1).getDestination(), carte.getEntrepot());
        heureArrivee = listeLivraisons.get(listeLivraisons.size()-1).getHeureLivraison().plusMinutes(chemin.getDuree().toMinutes() + Livraison.DUREE_LIVRAISON.toMinutes());
        this.heureFinTournee = heureArrivee;
        listeChemins.add(chemin);

        System.out.println("Solution found in  " + (System.currentTimeMillis() - start) + " ms");
        System.out.println("Heure de fin: " + heureFinTournee);
        return true;
    }

    /**
     * Met à jour l'ordre des livraisons dans la liste des livraisons en fonction des identifiants fournis.
     *
     * @param livraisonsId Liste d'identifiants de livraisons décrivant le nouvel ordre.
     */
    private void updateLivraisonsOrder(ArrayList<Long> livraisonsId) {
    	ArrayList<Livraison> newLivraisons = new ArrayList<>(listeLivraisons.size());
    	for(int i=1; i < livraisonsId.size(); i++)
        {
            for(Livraison livraison : listeLivraisons)
            {
                if(Objects.equals(livraison.getDestination().getId(), livraisonsId.get(i)))
                {
                    newLivraisons.add(livraison);
                    break;
                }
            }
        }
    	listeLivraisons = newLivraisons;
    }

    public ArrayList<Livraison> getListeLivraisons() {
        return listeLivraisons;
    }

    public LinkedList<Chemin> getListeChemins() {
        return listeChemins;
    }

    public LocalTime getHeureFinTournee() {
        return heureFinTournee;
    }



}