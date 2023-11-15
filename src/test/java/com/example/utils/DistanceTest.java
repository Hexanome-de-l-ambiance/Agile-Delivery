package com.example.utils;

import com.example.model.Intersection;
import com.example.utils.Distance;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DistanceTest {

    @Test
    void testHaversine() {
        // Test à partir de coordonnées connues et d'une distance attendue
        double lat1 = 40.0;
        double lon1 = -74.0;
        double lat2 = 41.0;
        double lon2 = -75.0;

        // Distance attendue en mètres
        double distanceAttendue = 139689;

        double distanceCalcule = Distance.haversine(lat1, lon1, lat2, lon2);

        // Verification de l'égalité entre la distance attendue et la distance calculée à un mètre près
        assertEquals(distanceAttendue, distanceCalcule, 1.0);
    }

}
