package com.example.model;
import com.example.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SegmentTest {

    @Test
    void testGetAndSet() {
        // Création d'un segment
        Intersection origin = new Intersection(1L, 40.0, -74.0);
        Intersection destination = new Intersection(2L, 41.0, -75.0);
        Segment segment = new Segment(origin, destination, 10.0, "Segment1");

        // Test des getters
        assertEquals(origin, segment.getOrigin());
        assertEquals(destination, segment.getDestination());
        assertEquals(10.0, segment.getLength(), 0.0001);
        assertEquals("Segment1", segment.getName());

        // Test des setters
        Intersection newOrigin = new Intersection(3L, 42.0, -76.0);
        Intersection newDestination = new Intersection(4L, 43.0, -77.0);
        segment.setOrigin(newOrigin);
        segment.setDestination(newDestination);
        segment.setLength(15.0);
        segment.setName("UpdatedSegment");

        // Vérification de la MAJ des valeurs
        assertEquals(newOrigin, segment.getOrigin());
        assertEquals(newDestination, segment.getDestination());
        assertEquals(15.0, segment.getLength(), 0.0001);
        assertEquals("UpdatedSegment", segment.getName());
    }
}





