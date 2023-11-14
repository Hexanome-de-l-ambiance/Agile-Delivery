package test.java.com.example.model;
import com.example.model.Intersection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntersectionTest {

    @Test
    void testEquals() {
        Intersection intersection1 = new Intersection(1L, 40.0, -74.0);
        Intersection intersection2 = new Intersection(1L, 40.0, -74.0);
        Intersection intersection3 = new Intersection(2L, 40.0, -74.0);

        // Test pour 2 intersections égales
        assertEquals(intersection1, intersection2);

        // Test pour 2 intersection différentes
        assertNotEquals(intersection1, intersection3);
    }

    @Test
    void testGetAndSet() {
        Intersection intersection = new Intersection();

        // Test getters et setters
        intersection.setId(1L);
        assertEquals(1L, intersection.getId());

        intersection.setLatitude(40.0);
        assertEquals(40.0, intersection.getLatitude(), 0.0001);

        intersection.setLongitude(-74.0);
        assertEquals(-74.0, intersection.getLongitude(), 0.0001);
    }

}