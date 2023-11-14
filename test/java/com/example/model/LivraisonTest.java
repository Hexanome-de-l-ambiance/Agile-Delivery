package java.com.example.model;
import com.example.model.*;
import org.junit.jupiter.api.Test;
import java.time.LocalTime;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class LivraisonTest {

    @Test
    void testGettersAndSetters() {
        // Création d'une livraison
        Intersection destination = new Intersection(1L, 40.0, -74.0);
        LocalTime creneauHoraire = LocalTime.of(10, 0);

        Livraison livraison = new Livraison(destination, creneauHoraire);

        // Test des getters destination et creneau horaire
        assertEquals(destination, livraison.getDestination());
        assertEquals(creneauHoraire, livraison.getCrenauHoraire());
        assertNull(livraison.getHeureLivraison());

        // Test des getters et setters d'heure de livraison
        LocalTime heureLivraison = LocalTime.of(10, 30);
        livraison.setHeureLivraison(heureLivraison);
        assertEquals(heureLivraison, livraison.getHeureLivraison());

        // Ajout d'une destination et test de son getter
        Intersection newDestination = new Intersection(2L, 41.0, -75.0);
        livraison.setDestination(newDestination);
        assertEquals(newDestination, livraison.getDestination());

        // Ajout d'une heure de livraison et test de son getter
        LocalTime newHeureLivraison = LocalTime.of(11, 0);
        livraison.setHeureLivraison(newHeureLivraison);
        assertEquals(newHeureLivraison, livraison.getHeureLivraison());
    }

    @Test
    void testGetDestinationException() {
        // Création d'une livraison sans assigner de destination
        Livraison livraison = new Livraison();

        // Test de l'exception en cas de livraison sans destination
        assertThrows(IllegalStateException.class, livraison::getDestination);
    }

    @Test
    void testSetCrenauHoraire() {
        // Création d'une livraison
        Intersection destination = new Intersection(1L, 40.0, -74.0);
        LocalTime initialCreneauHoraire = LocalTime.of(10, 0);
        Livraison livraison = new Livraison(destination, initialCreneauHoraire);

        // Ajout d'un créneau horaire
        LocalTime newCreneauHoraire = LocalTime.of(12, 0);
        livraison.setCrenauHoraire(newCreneauHoraire);

        // Vérification de la MAJ de la livraison avec le nouveau créneau horaire
        assertEquals(newCreneauHoraire, livraison.getCrenauHoraire());
    }

}
