package com.example.utils;

import com.example.model.Carte;
import com.example.model.Chemin;
import com.example.model.Intersection;
import com.example.xml.XMLOpener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AstarTest {

    Carte testCarte;

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
     * Test de la méthode calculDistance de la classe Astar
     */
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

    /**
     * Test de la méthode calculChemin de la classe Astar
     */
    @Test
    public void aStarPathTest()
    {
        Intersection debut = testCarte.getListeIntersections().get(1L);
        Intersection fin = testCarte.getListeIntersections().get(2L);
        Chemin chemin = Astar.calculChemin(testCarte, debut, fin);

        assertEquals(1, chemin.getListeSegments().size());
        assertEquals(118.890465, chemin.getLongueur(), 0.0001);

        assertEquals(debut, chemin.getListeSegments().get(0).getOrigin());
        assertEquals(fin, chemin.getListeSegments().get(0).getDestination());

        debut = testCarte.getListeIntersections().get(1L);
        fin = testCarte.getListeIntersections().get(5L);
        chemin = Astar.calculChemin(testCarte, debut, fin);
        // path expected : 1->4->5

        assertEquals(2, chemin.getListeSegments().size());
        assertEquals(175.42484, chemin.getLongueur(), 0.0001);
        assertEquals(debut, chemin.getListeSegments().get(0).getOrigin());
        assertEquals(testCarte.getListeIntersections().get(4L), chemin.getListeSegments().get(0).getDestination());
        assertEquals(testCarte.getListeIntersections().get(4L), chemin.getListeSegments().get(1).getOrigin());
        assertEquals(fin, chemin.getListeSegments().get(1).getDestination());
    }
}
