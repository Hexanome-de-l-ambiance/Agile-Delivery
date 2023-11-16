package com.example.tsp;

import com.example.utils.Astar;
import com.example.xml.XMLOpener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

import com.example.model.Carte;
import com.example.model.Livraison;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CompleteGraphTest {

    private Carte testCarte;
    private ArrayList<Livraison> livraisons;

    @BeforeEach
    public void setUp() {

        testCarte = new Carte(1);
        XMLOpener xmlOpener = XMLOpener.getInstance();
        try {
            xmlOpener.readFile(testCarte, "data/xml/testMap.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Teste la création d'un graph avec une liste de livraisons vide
     */
    @Test
    public void TestCreationGraphVide() {
        livraisons = new ArrayList<>();
        CompleteGraph completeGraph = new CompleteGraph(testCarte, livraisons);

        assertEquals(livraisons.size() + 1, completeGraph.getNbVertices());
    }

    /**
     * Teste la création d'un graph avec une liste de livraisons dans le même créneau horaire
     */
    @Test
    public void testCreationGraph() {
        livraisons = new ArrayList<>();
        Livraison livraison1 = new Livraison(testCarte.getIntersection(2L), Livraison.DEBUT_TOURNEE);
        Livraison livraison2 = new Livraison(testCarte.getIntersection(4L), Livraison.DEBUT_TOURNEE);
        livraisons.add(livraison1);
        livraisons.add(livraison2);
        CompleteGraph completeGraph = new CompleteGraph(testCarte, livraisons);

        assertEquals(livraisons.size() +1, completeGraph.getNbVertices());

        // Test la méthode getCost
        assertEquals(-1.0, completeGraph.getCost(0, 0));
        assertEquals(Astar.calculDistance(testCarte, livraison1.getDestination(),livraison2.getDestination()), completeGraph.getCost(0, 1));
        assertEquals(Astar.calculDistance(testCarte, livraison1.getDestination(), testCarte.getEntrepot()), completeGraph.getCost(0, 2));

        assertEquals(Astar.calculDistance(testCarte, livraison2.getDestination(), livraison1.getDestination()), completeGraph.getCost(1, 0));
        assertEquals(-1.0, completeGraph.getCost(1, 1));
        assertEquals(Astar.calculDistance(testCarte, livraison2.getDestination(), testCarte.getEntrepot()), completeGraph.getCost(1, 2));

        assertEquals(Astar.calculDistance(testCarte, testCarte.getEntrepot(), livraison1.getDestination()), completeGraph.getCost(2, 0));
        assertEquals(Astar.calculDistance(testCarte, testCarte.getEntrepot(), livraison2.getDestination()), completeGraph.getCost(2, 1));
        assertEquals(-1.0, completeGraph.getCost(2, 2));

        // Test la méthode getId
        assertEquals(2L, completeGraph.getId(0));
        assertEquals(4L, completeGraph.getId(1));


        // Test la méthode isArc
        assertFalse(completeGraph.isArc(0, 0));
        assertTrue(completeGraph.isArc(0, 1));
        assertTrue(completeGraph.isArc(0, 2));
        assertTrue(completeGraph.isArc(1, 0));
        assertFalse(completeGraph.isArc(1, 1));
        assertTrue(completeGraph.isArc(1, 2));
        assertTrue(completeGraph.isArc(2, 0));
        assertTrue(completeGraph.isArc(2, 1));
        assertFalse(completeGraph.isArc(2, 2));
    }

    /**
     * Teste la création d'un graph avec une liste de livraisons dans des créneaux horaires différents
     */
    @Test
    public void testCreationGraph2() {
        livraisons = new ArrayList<>();
        Livraison livraison1 = new Livraison(testCarte.getIntersection(2L), Livraison.DEBUT_TOURNEE);
        Livraison livraison2 = new Livraison(testCarte.getIntersection(4L), Livraison.DEBUT_TOURNEE.plusHours(1));
        livraisons.add(livraison1);
        livraisons.add(livraison2);
        CompleteGraph completeGraph = new CompleteGraph(testCarte, livraisons);

        assertEquals(livraisons.size() +1, completeGraph.getNbVertices());

        // Test la méthode getCost
        assertEquals(-1.0, completeGraph.getCost(0, 0));


        // Test la méthode isArc
        assertFalse(completeGraph.isArc(0, 0));
        assertTrue(completeGraph.isArc(0, 1));
        assertTrue(completeGraph.isArc(0, 2));
        assertFalse(completeGraph.isArc(1, 0));
        assertFalse(completeGraph.isArc(1, 1));
        assertTrue(completeGraph.isArc(1, 2));
        assertTrue(completeGraph.isArc(2, 0));
        assertTrue(completeGraph.isArc(2, 1));
        assertFalse(completeGraph.isArc(2, 2));
    }
}
