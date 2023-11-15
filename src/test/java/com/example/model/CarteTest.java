package com.example.model;

import com.example.model.*;
import com.example.tsp.CompleteGraph;
import com.example.tsp.Graph;
import com.example.tsp.TSP;
import com.example.tsp.TSP1;
import com.example.utils.Astar;
import javafx.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.example.xml.XMLOpener;

import java.util.ArrayList;
import java.util.Map;

public class CarteTest {

    Carte testCarte;
    Carte petiteCarte;
    Carte moyenneCarte;
    Carte grandeCarte;

    @BeforeEach
    public void setUp() {
        testCarte = new Carte(1);
        petiteCarte = new Carte(1);
        moyenneCarte = new Carte(1);
        grandeCarte = new Carte(1);
        XMLOpener xmlOpener = XMLOpener.getInstance();
        try {
            xmlOpener.readFile(testCarte, "data/xml/testMap.xml");
            xmlOpener.readFile(petiteCarte, "data/xml/smallMap.xml");
            xmlOpener.readFile(moyenneCarte, "data/xml/mediumMap.xml");
            xmlOpener.readFile(grandeCarte, "data/xml/largeMap.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }

        long tempsDebut = System.currentTimeMillis();
        testCarte.initAdjacenceList();
        System.out.println("Temps d'initialisation de la liste d'adjacence de 'testCarte' : " + (System.currentTimeMillis() - tempsDebut) + "ms");

        tempsDebut = System.currentTimeMillis();
        petiteCarte.initAdjacenceList();
        System.out.println("Temps d'initialisation de la liste d'adjacence de 'petiteCarte' : " + (System.currentTimeMillis() - tempsDebut) + "ms");

        tempsDebut = System.currentTimeMillis();
        moyenneCarte.initAdjacenceList();
        System.out.println("Temps d'initialisation de la liste d'adjacence de 'moyenneCarte' : " + (System.currentTimeMillis() - tempsDebut) + "ms");

        tempsDebut = System.currentTimeMillis();
        grandeCarte.initAdjacenceList();
        System.out.println("Temps d'initialisation de la liste d'adjacence de 'grandeCarte' : " + (System.currentTimeMillis() - tempsDebut) + "ms");
    }

    @Test
    public void aStarDistanceTest()
    {
        Intersection debut = testCarte.getListeIntersections().get(1L);
        Intersection fin = testCarte.getListeIntersections().get(2L);
        assertEquals(118.890465, Astar.calculDistance(testCarte, debut, fin), 0.0001);
        assertEquals(118.890465, Astar.calculDistance(testCarte,debut, fin), 0.0001);

        debut = testCarte.getListeIntersections().get(1L);
        fin = testCarte.getListeIntersections().get(5L);
        assertEquals(175.42484, Astar.calculDistance(testCarte, debut, fin), 0.0001);
        assertEquals(175.42484, Astar.calculDistance(testCarte, fin, debut), 0.0001);

        debut = testCarte.getListeIntersections().get(2L);
        fin = testCarte.getListeIntersections().get(6L);
        assertEquals(230.575305, Astar.calculDistance(testCarte, debut, fin), 0.0001);
    }

    @Test
    public void aStarPathTest()
    {
        Intersection debut = testCarte.getListeIntersections().get(1L);
        Intersection fin = testCarte.getListeIntersections().get(2L);
        Chemin chemin = Astar.calculChemin(testCarte, debut, fin);
        printChemin(chemin);
        System.out.println();

        debut = testCarte.getListeIntersections().get(1L);
        fin = testCarte.getListeIntersections().get(5L);
        chemin = Astar.calculChemin(testCarte, debut, fin);
        printChemin(chemin);
        System.out.println();

        debut = testCarte.getListeIntersections().get(2L);
        fin = testCarte.getListeIntersections().get(6L);
        chemin = Astar.calculChemin(testCarte, debut, fin);
        printChemin(chemin);
        System.out.println();
    }

    @Test
    public void graphCompletTestCarte()
    {
        ArrayList<Livraison> nodes = new ArrayList<>();
        //nodes.add(testCarte.getListeIntersections().get(2L));
        //nodes.add(testCarte.getListeIntersections().get(4L));

        long tempsDebut = System.currentTimeMillis();
        Graph graphComplet = new CompleteGraph(testCarte, nodes);
        System.out.println("Temps pour construire le graph complet: " + (System.currentTimeMillis() - tempsDebut) + "ms");

        System.out.println();
        for (int i = 0; i < graphComplet.getNbVertices(); i++) {
            for (int j = 0; j < graphComplet.getNbVertices(); j++) {
                // cost with 2 decimals
                System.out.print(String.format("%.2f", graphComplet.getCost(i, j)) + "\t\t");
            }
            System.out.println();
        }
    }

    @Test
    public void graphCompletPetiteCarte()
    {
        ArrayList<Livraison> intersections = new ArrayList<>();
        //intersections.add(petiteCarte.getListeIntersections().get(25175791L));
        //intersections.add(petiteCarte.getListeIntersections().get(2129259178L));
        //intersections.add(petiteCarte.getListeIntersections().get(26086130L));
        //intersections.add(petiteCarte.getListeIntersections().get(26086123L));
        //intersections.add(petiteCarte.getListeIntersections().get(565375197L));
        //intersections.add(petiteCarte.getListeIntersections().get(55475025L));
        //intersections.add(petiteCarte.getListeIntersections().get(2117622721L));
        //intersections.add(petiteCarte.getListeIntersections().get(342867241L));
        //intersections.add(petiteCarte.getListeIntersections().get(26317246L));
        //intersections.add(petiteCarte.getListeIntersections().get(1423439485L));

        long tempsDebut = System.currentTimeMillis();
        Graph completeGraph = new CompleteGraph(petiteCarte, intersections);
        System.out.println("Temps pour construire le graph complet: " + (System.currentTimeMillis() - tempsDebut) + "ms");

        System.out.println();
        for (int i = 0; i < intersections.size(); i++) {
            for (int j = 0; j < intersections.size(); j++) {
                System.out.print(String.format("%.2f", completeGraph.getCost(i, j)) + "\t\t");
            }
            System.out.println('\n');
        }

    }


    // Test failed. To be verified.
    @Test
    public void creerTourneeTestCarte()
    {
        ArrayList<Intersection> intersections = new ArrayList<>();
        intersections.add(testCarte.getListeIntersections().get(2L));
        intersections.add(testCarte.getListeIntersections().get(4L));

        Tournee tournee = new Tournee(1);
        long tempsDebut = System.currentTimeMillis();
        //tournee.calculerTournee(testCarte, intersections);
        System.out.println("Temps pour calculer la tournée: " + (System.currentTimeMillis() - tempsDebut) + "ms");
        //tournee.printTournee();
    }

    @Test
    public void creerTourneeGrandeCarte()
    {
        ArrayList<Intersection> intersections = new ArrayList<>();
        intersections.add(grandeCarte.getListeIntersections().get(2129259178L));
        intersections.add(grandeCarte.getListeIntersections().get(25175791L));
        intersections.add(grandeCarte.getListeIntersections().get(26086130L));
        intersections.add(grandeCarte.getListeIntersections().get(26086123L));
        intersections.add(grandeCarte.getListeIntersections().get(565375197L));
        intersections.add(grandeCarte.getListeIntersections().get(55475025L));
        intersections.add(grandeCarte.getListeIntersections().get(2117622721L));
        intersections.add(grandeCarte.getListeIntersections().get(342867241L));
        intersections.add(grandeCarte.getListeIntersections().get(26317246L));
        intersections.add(grandeCarte.getListeIntersections().get(1423439485L));
        intersections.add(grandeCarte.getListeIntersections().get(26731885L));
        intersections.add(grandeCarte.getListeIntersections().get(26086117L));
        intersections.add(grandeCarte.getListeIntersections().get(26731890L));

        Tournee tournee = new Tournee(1);
        long tempsDebut = System.currentTimeMillis();
        //tournee.calculerTournee(grandeCarte, intersections);
        System.out.println("Temps pour calculer la tournée: " + (System.currentTimeMillis() - tempsDebut) + "ms");
        //tournee.printTournee();
        //System.out.println("Longueur totale " + tournee.getLongueurTotale());
    }

    private void printCarte(Carte carte)
    {
        for(Map.Entry<Pair<Long, Long>, Segment> segmentEntry : carte.getListeSegments().entrySet())
        {
            Segment segment = segmentEntry.getValue();
            System.out.println("Segment : " + segment.getOrigin().getId() + " -> " + segment.getDestination().getId());
        }
        System.out.println();
        for(Map.Entry<Long, Intersection> intersectionEntry : carte.getListeIntersections().entrySet())
        {
            Intersection intersection = intersectionEntry.getValue();
            System.out.println("Intersection : " + intersection.getId());
        }

        System.out.println();
        for(Map.Entry<Long, ArrayList<Pair<Long, Double>>> entry : carte.getListeAdjacence().entrySet())
        {
            System.out.print("Intersection " + entry.getKey() + " : ");
            for(Pair<Long, Double> pair : entry.getValue())
            {
                System.out.print(pair.getKey() + " ");
            }
            System.out.println();
        }
    }

    private void printChemin(Chemin chemin)
    {
        for(Segment segment : chemin.getListeSegments())
        {
            System.out.println("Segment : " + segment.getOrigin().getId() + " -> " + segment.getDestination().getId() + " : " + segment.getLength());
        }
    }

}
