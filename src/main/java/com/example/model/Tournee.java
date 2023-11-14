package com.example.model;

import com.example.tsp.*;
import com.example.utils.Astar;

import java.time.Duration;
import java.time.LocalTime;
import java.util.*;
import java.time.format.DateTimeFormatter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static java.lang.Math.round;


/**
 * 
 */
public class Tournee{

    private LinkedList<Chemin> listeChemins;
    private ArrayList<Livraison> livraisons;

    private int coursier;
    private LocalTime heureFinTournee;
    private double longueurTotale;

    /**
     * Default constructor
     */
    public Tournee(int c) {
        listeChemins = new LinkedList<>();
        livraisons = new ArrayList<>();
        heureFinTournee = Livraison.DEBUT_TOURNEE;
        coursier = c;
    }

    public int getCoursier(){
        return coursier;
    }



    public void addLivraison(Livraison livraison) {
        livraisons.add(livraison);
    }

    /**
     * Ajoute une livraison à la tournée et met à jour les horaires de livraison suivants sans recalculer la tournée.
     * @param carte sur laquelle se trouve la livraison.
     * @param livraison a ajoutée à la tournée.
     * @param index indice de la livraison dans la tournée. Si <code>index</code> vaut <code>livraisons.size()</code> alors la livraison est ajoutée à la fin de la tournée.
     */
    public boolean addLivraison(Carte carte, Livraison livraison, int index) {
        if(listeChemins.isEmpty() || index < 0 || index > livraisons.size()) {
            return false;
        }
        boolean success = true;

        livraisons.add(index, livraison);
        Chemin cheminPrecedent;
        Chemin cheminSuivant;
        LocalTime heureArrivee;

        if(index == 0)
        {
            cheminPrecedent = Astar.calculChemin(carte, carte.getEntrepot(), livraison.getDestination());
            heureArrivee = Livraison.DEBUT_TOURNEE.plusMinutes(cheminPrecedent.getDuree().toMinutes());
        } else {
            Livraison livraisonPrecedente = livraisons.get(index - 1);
            cheminPrecedent = Astar.calculChemin(carte, livraisonPrecedente.getDestination(), livraison.getDestination());
            heureArrivee = livraisonPrecedente.getHeureLivraison().plusMinutes(cheminPrecedent.getDuree().toMinutes() + Livraison.DUREE_LIVRAISON.toMinutes());
        }
        listeChemins.remove(index);
        listeChemins.add(index, cheminPrecedent);

        if(index == livraisons.size()-1)
        {
            cheminSuivant = Astar.calculChemin(carte, livraison.getDestination(), carte.getEntrepot());
            listeChemins.addLast(cheminSuivant);
        } else {
            cheminSuivant = Astar.calculChemin(carte, livraison.getDestination(), livraisons.get(index + 1).getDestination());
            listeChemins.add(index + 1, cheminSuivant);
        }

        for(int i=index; i< livraisons.size(); i++) {
            if(heureArrivee.isBefore(livraisons.get(i).getCreneauHoraire())) {
                livraisons.get(i).setHeureLivraison(livraisons.get(i).getCreneauHoraire());
            }else {
                livraisons.get(i).setHeureLivraison(heureArrivee);
            }
            if(heureArrivee.isAfter(livraisons.get(i).getCreneauHoraire())) {
                success = false;
            }
            cheminSuivant = listeChemins.get(i+1);
            heureArrivee = livraisons.get(i).getHeureLivraison().plusMinutes(cheminSuivant.getDuree().toMinutes() + Livraison.DUREE_LIVRAISON.toMinutes());
        }
        heureFinTournee = heureArrivee;
        if(heureFinTournee.isAfter(Livraison.FIN_TOURNEE)) {
            success = false;
        }
        return success;
    }

    public void removeLivraison(Livraison livraison) {livraisons.remove(livraison);}

    /**
     * Supprime une livraison de la tournée et recalcule les horaires de livraison suivants sans recalculer la tournée.
     * @param carte sur laquelle se trouve la livraison.
     * @param index indice de la livraison dans la tournée à retirer.
     */
    public void removeLivraison(Carte carte, int index) {
        if(listeChemins.isEmpty() || index < 0 || index >= livraisons.size()) {
            return;
        }

        if(livraisons.size() == 1) {
            livraisons.clear();
            listeChemins.clear();
            return;
        }
        Chemin nouveauChemin;
        LocalTime heureArrivee;
        if(index == livraisons.size() - 1) {
            nouveauChemin = Astar.calculChemin(carte, livraisons.get(index - 1).getDestination(), carte.getEntrepot());
            heureFinTournee = livraisons.get(index - 1).getHeureLivraison().plusMinutes(nouveauChemin.getDuree().toMinutes() + Livraison.DUREE_LIVRAISON.toMinutes());
            livraisons.remove(index);
            listeChemins.removeLast();
            listeChemins.set(listeChemins.size()-1, nouveauChemin);
        } else {
            if(index == 0) {
                nouveauChemin = Astar.calculChemin(carte, carte.getEntrepot(), livraisons.get(1).getDestination());
                heureArrivee = Livraison.DEBUT_TOURNEE.plusMinutes(nouveauChemin.getDuree().toMinutes());
            }else {
                nouveauChemin = Astar.calculChemin(carte, livraisons.get(index - 1).getDestination(), livraisons.get(index + 1).getDestination());
                heureArrivee = livraisons.get(index - 1).getHeureLivraison().plusMinutes(nouveauChemin.getDuree().toMinutes() + Livraison.DUREE_LIVRAISON.toMinutes());
            }
            livraisons.remove(index);
            listeChemins.remove(index);
            listeChemins.set(index, nouveauChemin);

            Chemin cheminSuivant;
            for(int i=index; i< livraisons.size(); i++) {
                if(heureArrivee.isBefore(livraisons.get(i).getCreneauHoraire())) {
                    livraisons.get(i).setHeureLivraison(livraisons.get(i).getCreneauHoraire());
                }else {
                    livraisons.get(i).setHeureLivraison(heureArrivee);
                }
                cheminSuivant = listeChemins.get(i+1);
                heureArrivee = livraisons.get(i).getHeureLivraison().plusMinutes(cheminSuivant.getDuree().toMinutes() + Livraison.DUREE_LIVRAISON.toMinutes());
            }
            heureFinTournee = heureArrivee;
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

        Graph graph = new CompleteGraph(carte, livraisons);
        int nbVertices = graph.getNbVertices();
        int startIndex = nbVertices - 1;

        TSP tsp = new TSP2(startIndex);
        if(!tsp.searchSolution(10000, graph)) {
            System.out.println("No solution found");
            return false;
        }
        updateLivraisonsOrder(tsp.getSolutions());

        Chemin chemin = Astar.calculChemin(carte, carte.getEntrepot(), livraisons.get(0).getDestination());
        LocalTime heureArrivee = Livraison.DEBUT_TOURNEE.plusMinutes(chemin.getDuree().toMinutes());
        if(heureArrivee.isBefore(livraisons.get(0).getCreneauHoraire())) {
            livraisons.get(0).setHeureLivraison(livraisons.get(0).getCreneauHoraire());
        }else {
            livraisons.get(0).setHeureLivraison(heureArrivee);
        }
        listeChemins.add(chemin);

        for(int i=0; i < livraisons.size() -1; i++){
            chemin = Astar.calculChemin(carte, livraisons.get(i).getDestination(), livraisons.get(i+1).getDestination());
            heureArrivee = livraisons.get(i).getHeureLivraison().plusMinutes(chemin.getDuree().toMinutes() + Livraison.DUREE_LIVRAISON.toMinutes());
            if(heureArrivee.isBefore(livraisons.get(i+1).getCreneauHoraire())) {
                livraisons.get(i+1).setHeureLivraison(livraisons.get(i+1).getCreneauHoraire());
            }else {
                livraisons.get(i+1).setHeureLivraison(heureArrivee);
            }
            listeChemins.add(chemin);
        }
        chemin = Astar.calculChemin(carte, livraisons.get(livraisons.size()-1).getDestination(), carte.getEntrepot());
        heureArrivee = livraisons.get(livraisons.size()-1).getHeureLivraison().plusMinutes(chemin.getDuree().toMinutes() + Livraison.DUREE_LIVRAISON.toMinutes());
        this.heureFinTournee = heureArrivee;
        listeChemins.add(chemin);

        System.out.println("Solution found in  " + (System.currentTimeMillis() - start) + " ms");
        return true;
    }

    public void printTournee()
    {
        for(int i=0 ; i< livraisons.size(); i++)
        {
            System.out.println("Chemin " + i + " : " + listeChemins.get(i).getOrigin().getId() + " -> " + listeChemins.get(i).getDestination().getId() + " (" + listeChemins.get(i).getDuree().toMinutes() + " min)");
            System.out.println("Heure d'arrivée : " + livraisons.get(i).getHeureLivraison());
            System.out.println();
        }
        System.out.println("Chemin " + (listeChemins.size()-1) + " : " + listeChemins.get(listeChemins.size()-1).getOrigin().getId() + " -> " + listeChemins.get(listeChemins.size()-1).getDestination().getId() + " (" + listeChemins.get(listeChemins.size()-1).getDuree().toMinutes() + " min)");
        System.out.println("Heure d'arrivée : " + heureFinTournee);
    }

    public void updateLivraisonsOrder(ArrayList<Long> livraisonsId) {
    	ArrayList<Livraison> newLivraisons = new ArrayList<>(livraisons.size());
    	for(int i=1; i < livraisonsId.size(); i++)
        {
            for(Livraison livraison : livraisons)
            {
                if(Objects.equals(livraison.getDestination().getId(), livraisonsId.get(i)))
                {
                    newLivraisons.add(livraison);
                    break;
                }
            }
        }
    	livraisons = newLivraisons;
    }

    public double getLongueurTotale() {
    	return longueurTotale;
    }


    public LocalTime getHeureFinTournee() {
        return heureFinTournee;
    }

    public void genererFeuilleDeRouteHTML(String fileName) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime heureActuelle = LocalTime.of(8, 0, 0);

        System.out.println(System.getProperty("user.dir"));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "/data/feuillesDeRoute/"+ fileName))) {
            writer.write("<html>");
            writer.write("<head>");
            writer.write("<title>Feuille de route</title>");
            writer.write("<style>");
            writer.write("body { font-family: 'Arial', sans-serif; margin: 20px; background-color: #f4f4f4; color: #333; }");
            writer.write("h1 { color: #007bff; }");
            writer.write("h2 { color: #555; margin-top: 15px; }");
            writer.write("p { color: #777; }");
            writer.write(".segment { border: 1px solid #ddd; padding: 10px; margin: 5px; background-color: #fff; }");
            writer.write(".arrival-time { font-weight: bold; color: #009688; }");
            writer.write("</style>");
            writer.write("</head>");
            writer.write("<body>");
            writer.write("<h1>Feuille de route pour la tournée du coursier " + coursier + "</h1>");

            if(!listeChemins.isEmpty()){

                if(livraisons.get(0).getCreneauHoraire().isAfter(heureActuelle)){
                    writer.write("<p>Heure de début de tournée : " + livraisons.get(0).getCreneauHoraire().minusMinutes(listeChemins.get(0).getDuree().toMinutes() + Livraison.DUREE_LIVRAISON.toMinutes()).format(formatter) + "</p>");
                }
                writer.write("<p>Heure de fin de tournée : " + heureFinTournee.format(formatter) + "</p>");
            }

            int indexChemin = 1;
            for (Chemin chemin : listeChemins) {
                if(indexChemin < listeChemins.size() && indexChemin > 1 && livraisons.get(indexChemin - 1).getCreneauHoraire().isAfter(heureActuelle)){
                    Duration tempsAttente = Duration.between(heureActuelle.plusMinutes(chemin.getDuree().toMinutes() + Livraison.DUREE_LIVRAISON.toMinutes()), livraisons.get(indexChemin - 1).getHeureLivraison());
                    writer.write("<p style='color: #850606;'>Temps d'attente : " + tempsAttente.toMinutes() + " min </p>");
                }
                writer.write("<div class='segment'>");
                if(indexChemin < listeChemins.size()) {
                    writer.write("<h2>Livraison " + indexChemin + "</h2>");
                }else{
                    writer.write("<h2>Retour à l'entrepot</h2>");
                }


                int indexSegment = 1;
                String currentRoute = null;
                double totalLength = 0;

                for (Segment segment : chemin.getListeSegments()) {
                    if (currentRoute == null || !currentRoute.equals(segment.getName())) {
                        if (currentRoute != null) {
                            writer.write("<p><strong>" + currentRoute + ":</strong> Longueur : " + round(totalLength) + " mètres </p>");
                        }

                        currentRoute = segment.getName();
                        totalLength = 0;
                    }

                    totalLength += segment.getLength();

                    if (indexSegment == chemin.getListeSegments().size()) {
                        writer.write("<p><strong>" + currentRoute + ":</strong> Longueur : " + round(totalLength) + " mètres </p>");
                    }

                    indexSegment++;
                }

                if (indexChemin < listeChemins.size()) {
                    writer.write("<p class='arrival-time'><strong>Heure d'arrivée à la destination :</strong> " + livraisons.get(indexChemin - 1).getHeureLivraison().format(formatter) + "</p>");
                    heureActuelle = livraisons.get(indexChemin - 1).getHeureLivraison();
                } else {
                    writer.write("<p class='arrival-time'><strong>Heure de retour à l'entrepôt :</strong> " + getHeureFinTournee().format(formatter) + "</p>");
                }
                writer.write("</div>");
                indexChemin++;
            }

            writer.write("</body>");
            writer.write("</html>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}