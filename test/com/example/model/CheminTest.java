package com.example.model;

import com.example.model.*;
import org.junit.jupiter.api.Test;
import java.time.Duration;
import java.time.LocalTime;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class CheminTest {

    @Test
    void testAddSegmentInFirstPosition() {
        // Création d'un ségment initial
        Intersection origin = new Intersection(1L, 40.0, -74.0);
        Intersection destination = new Intersection(2L, 41.0, -75.0);
        Segment initialSegment = new Segment(origin, destination, 10.0, "Initial segment");

        // Création d'un chemin
        Chemin chemin = new Chemin();
        // Ajout d'un segment en première position du chemin
        chemin.addSegmentInFirstPosition(initialSegment);

        // Vérifier si le chemin contient le segment initial
        LinkedList<Segment> segments = chemin.getListeSegments();
        assertTrue(segments.contains(initialSegment));

        // Création d'une d'une nouvelle origine qu'on relie avec l'ancienne origine à partir d'un segment
        Intersection newOrigin = new Intersection(3L, 42.0, -76.0);
        Segment newSegment = new Segment(newOrigin, origin, 5.0, "New segment");
        chemin.addSegmentInFirstPosition(newSegment);

        // Vérification de l'ajout du segment
        assertTrue(segments.contains(newSegment));

        // Vérification de la MAJ de la longueur du chemin et la durée de la livraison
        assertEquals(15.0, chemin.getLongueur(), 0.0001);
        assertEquals(Duration.ofMinutes(15 / Livraison.VITESSE_DEPLACEMENT), chemin.getDuree());
    }

    @Test
    void testGetOrigin() {
        // Création d'un chemin à partir d'un segment partant d'une origine vers une destination
        Intersection origin = new Intersection(1L, 40.0, -74.0);
        Intersection destination = new Intersection(2L, 41.0, -75.0);
        Segment segment = new Segment(origin, destination, 10.0, "Initial segment");

        Chemin chemin = new Chemin();
        chemin.addSegmentInFirstPosition(segment);

        // Vérification de la récupération de l'origine du chemin
        assertEquals(origin, chemin.getOrigin());
    }

    @Test
    void testGetDestination() {
        // Création d'un chemin à partir d'un segment partant d'une origine vers une destination
        Intersection origin = new Intersection(1L, 40.0, -74.0);
        Intersection destination = new Intersection(2L, 41.0, -75.0);
        Segment segment = new Segment(origin, destination, 10.0, "Initial segment");

        Chemin chemin = new Chemin();
        chemin.addSegmentInFirstPosition(segment);

        // Vérification de la récupération de la destination du chemin
        assertEquals(destination, chemin.getDestination());
    }

    @Test
    void testSetAndGetHeureArrivee() {
        // Création d'un chemin et affectation d'une heure d'arrivée
        Chemin chemin = new Chemin();
        LocalTime heureArrivee = LocalTime.of(12, 30);
        chemin.setHeureArrivee(heureArrivee);

        // Vérifier que l'heure d'arrivée est exacte
        assertEquals(heureArrivee, chemin.getHeureArrivee());
    }

}





