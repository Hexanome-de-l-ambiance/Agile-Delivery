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

    @Test
    public void testCreerCarte() {
        testCarte = new Carte(1);
        XMLOpener xmlOpener = XMLOpener.getInstance();
        try {
            xmlOpener.readFile(testCarte, "data/xml/testMap.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(6, testCarte.getListeIntersections().size());
        assertEquals(12, testCarte.getListeSegments().size());
        assertEquals(1, testCarte.getListeTournees().size());
    }



}
