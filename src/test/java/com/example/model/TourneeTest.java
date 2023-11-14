package test.java.com.example.model;
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
import org.junit.jupiter.api.TestTemplate;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;

public class TourneeTest {
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
     * Test de la méthode calculerTournee de la classe Tournee.
     * Teste un cas avec une tournée vide
     */
    @Test
    public void testCalculTourneeVide() {
        Tournee tournee = new Tournee(1);
        tournee.calculerTournee(testCarte);

        assertEquals(0, tournee.getListeLivraisons().size());
        assertEquals(0, tournee.getListeChemins().size());
        assertEquals(LocalTime.of(8,0,0), tournee.getHeureFinTournee());

    }

    /**
     * Test de la méthode calculerTournee de la classe Tournee.
     * Teste un cas avec deux livraisons ajoutées dans l'ordre
     */
    @Test
    public void testCalculTournee1() {
        Livraison livraison1 = new Livraison(testCarte.getIntersection(2L), Livraison.DEBUT_TOURNEE);
        Livraison livraison2 = new Livraison(testCarte.getIntersection(4L), Livraison.DEBUT_TOURNEE.plusHours(1));

        Tournee tournee = new Tournee(1);
        tournee.addLivraison(livraison1);
        tournee.addLivraison(livraison2);

        tournee.calculerTournee(testCarte);

        assertEquals(livraison1, tournee.getListeLivraisons().get(0));
        assertEquals(livraison2, tournee.getListeLivraisons().get(1));
        assertEquals(LocalTime.of(8,0,0), tournee.getListeLivraisons().get(0).getHeureLivraison());
        assertEquals(LocalTime.of(9,0,0), tournee.getListeLivraisons().get(1).getHeureLivraison());
        assertEquals(LocalTime.of(9,5,0), tournee.getHeureFinTournee());

        assertEquals(3, tournee.getListeChemins().size());

        assertEquals(testCarte.getEntrepot(), tournee.getListeChemins().get(0).getOrigin());
        assertEquals(livraison1.getDestination(), tournee.getListeChemins().get(0).getDestination());

        assertEquals(livraison1.getDestination(), tournee.getListeChemins().get(1).getOrigin());
        assertEquals(livraison2.getDestination(), tournee.getListeChemins().get(1).getDestination());

        assertEquals(livraison2.getDestination(), tournee.getListeChemins().get(2).getOrigin());
        assertEquals(testCarte.getEntrepot(), tournee.getListeChemins().get(2).getDestination());
    }

    /**
     * Test de la méthode calculerTournee de la classe Tournee.
     * Teste un cas avec deux livraisons ajoutées dans l'ordre inverse
     */
    @Test
    public void testCalculTournee2() {
        Livraison livraison1 = new Livraison(testCarte.getIntersection(2L), Livraison.DEBUT_TOURNEE);
        Livraison livraison2 = new Livraison(testCarte.getIntersection(4L), Livraison.DEBUT_TOURNEE.plusHours(1));

        Tournee tournee = new Tournee(1);
        tournee.addLivraison(livraison2);
        tournee.addLivraison(livraison1);

        tournee.calculerTournee(testCarte);

        assertEquals(livraison1, tournee.getListeLivraisons().get(0));
        assertEquals(livraison2, tournee.getListeLivraisons().get(1));
        assertEquals(LocalTime.of(8,0,0), tournee.getListeLivraisons().get(0).getHeureLivraison());
        assertEquals(LocalTime.of(9,0,0), tournee.getListeLivraisons().get(1).getHeureLivraison());
        assertEquals(LocalTime.of(9,5,0), tournee.getHeureFinTournee());

        assertEquals(3, tournee.getListeChemins().size());

        assertEquals(testCarte.getEntrepot(), tournee.getListeChemins().get(0).getOrigin());
        assertEquals(livraison1.getDestination(), tournee.getListeChemins().get(0).getDestination());

        assertEquals(livraison1.getDestination(), tournee.getListeChemins().get(1).getOrigin());
        assertEquals(livraison2.getDestination(), tournee.getListeChemins().get(1).getDestination());

        assertEquals(livraison2.getDestination(), tournee.getListeChemins().get(2).getOrigin());
        assertEquals(testCarte.getEntrepot(), tournee.getListeChemins().get(2).getDestination());
    }

    /**
     * Test de la méthode calculerTournee de la classe Tournee.
     * Teste un cas avec deux livraisons avec deux heures de délai
     */
    @Test
    public void testCalculTournee3() {
        Livraison livraison1 = new Livraison(testCarte.getIntersection(2L), Livraison.DEBUT_TOURNEE);
        Livraison livraison2 = new Livraison(testCarte.getIntersection(4L), Livraison.DEBUT_TOURNEE.plusHours(2));

        Tournee tournee = new Tournee(1);
        tournee.addLivraison(livraison2);
        tournee.addLivraison(livraison1);

        tournee.calculerTournee(testCarte);

        assertEquals(livraison1, tournee.getListeLivraisons().get(0));
        assertEquals(livraison2, tournee.getListeLivraisons().get(1));
        assertEquals(LocalTime.of(8,0,0), tournee.getListeLivraisons().get(0).getHeureLivraison());
        assertEquals(LocalTime.of(10,0,0), tournee.getListeLivraisons().get(1).getHeureLivraison());
        assertEquals(LocalTime.of(10,5,0), tournee.getHeureFinTournee());

        assertEquals(3, tournee.getListeChemins().size());

        assertEquals(testCarte.getEntrepot(), tournee.getListeChemins().get(0).getOrigin());
        assertEquals(livraison1.getDestination(), tournee.getListeChemins().get(0).getDestination());

        assertEquals(livraison1.getDestination(), tournee.getListeChemins().get(1).getOrigin());
        assertEquals(livraison2.getDestination(), tournee.getListeChemins().get(1).getDestination());

        assertEquals(livraison2.getDestination(), tournee.getListeChemins().get(2).getOrigin());
        assertEquals(testCarte.getEntrepot(), tournee.getListeChemins().get(2).getDestination());
    }

    /**
     * Test de la méthode calculerTournee de la classe Tournee
     * Teste un cas où la première livraison ne commence pas à 8h
     */
    @Test
    public void testCalculTournee4() {
        Livraison livraison1 = new Livraison(testCarte.getIntersection(2L), Livraison.DEBUT_TOURNEE.plusHours(1));
        Livraison livraison2 = new Livraison(testCarte.getIntersection(4L), Livraison.DEBUT_TOURNEE.plusHours(3));

        Tournee tournee = new Tournee(1);
        tournee.addLivraison(livraison2);
        tournee.addLivraison(livraison1);

        tournee.calculerTournee(testCarte);

        assertEquals(livraison1, tournee.getListeLivraisons().get(0));
        assertEquals(livraison2, tournee.getListeLivraisons().get(1));
        assertEquals(LocalTime.of(9,0,0), tournee.getListeLivraisons().get(0).getHeureLivraison());
        assertEquals(LocalTime.of(11,0,0), tournee.getListeLivraisons().get(1).getHeureLivraison());
        assertEquals(LocalTime.of(11,5,0), tournee.getHeureFinTournee());

        assertEquals(3, tournee.getListeChemins().size());

        assertEquals(testCarte.getEntrepot(), tournee.getListeChemins().get(0).getOrigin());
        assertEquals(livraison1.getDestination(), tournee.getListeChemins().get(0).getDestination());

        assertEquals(livraison1.getDestination(), tournee.getListeChemins().get(1).getOrigin());
        assertEquals(livraison2.getDestination(), tournee.getListeChemins().get(1).getDestination());

        assertEquals(livraison2.getDestination(), tournee.getListeChemins().get(2).getOrigin());
        assertEquals(testCarte.getEntrepot(), tournee.getListeChemins().get(2).getDestination());
    }

    /**
     * Test de la méthode <code>addLivraison</code> de la classe Tournee.
     * Teste l'ajout d'une livraison à l'indice 0
     */
    @Test
    public void testAjouterApresCalculTournee1() {
        Livraison livraison1 = new Livraison(testCarte.getIntersection(2L), Livraison.DEBUT_TOURNEE);
        Livraison livraison2 = new Livraison(testCarte.getIntersection(4L), Livraison.DEBUT_TOURNEE.plusHours(1));

        Tournee tournee = new Tournee(1);
        tournee.addLivraison(livraison1);
        tournee.addLivraison(livraison2);

        tournee.calculerTournee(testCarte);

        Livraison livraison3 = new Livraison(testCarte.getIntersection(6L), Livraison.DEBUT_TOURNEE);
        tournee.addLivraison(testCarte, livraison3, 0);

        assertEquals(3, tournee.getListeLivraisons().size());
        assertEquals(4, tournee.getListeChemins().size());

        assertEquals(livraison3, tournee.getListeLivraisons().get(0));
        assertEquals(livraison1, tournee.getListeLivraisons().get(1));
        assertEquals(livraison2, tournee.getListeLivraisons().get(2));

        assertEquals(LocalTime.of(8,0,0), tournee.getListeLivraisons().get(0).getHeureLivraison());
        assertEquals(LocalTime.of(8,5,0), tournee.getListeLivraisons().get(1).getHeureLivraison());
        assertEquals(LocalTime.of(9,0,0), tournee.getListeLivraisons().get(2).getHeureLivraison());
        assertEquals(LocalTime.of(9,5,0), tournee.getHeureFinTournee());

        assertEquals(testCarte.getEntrepot(), tournee.getListeChemins().get(0).getOrigin());
        assertEquals(livraison3.getDestination(), tournee.getListeChemins().get(0).getDestination());

        assertEquals(livraison3.getDestination(), tournee.getListeChemins().get(1).getOrigin());
        assertEquals(livraison1.getDestination(), tournee.getListeChemins().get(1).getDestination());

        assertEquals(livraison1.getDestination(), tournee.getListeChemins().get(2).getOrigin());
        assertEquals(livraison2.getDestination(), tournee.getListeChemins().get(2).getDestination());

        assertEquals(livraison2.getDestination(), tournee.getListeChemins().get(3).getOrigin());
        assertEquals(testCarte.getEntrepot(), tournee.getListeChemins().get(3).getDestination());
    }

    /**
     * Test de la méthode <code>addLivraison</code> de la classe Tournee.
     * Teste l'ajout d'une livraison à l'indice 1
     */
    @Test
    public void testAjouterApresCalculTournee2() {
        Livraison livraison1 = new Livraison(testCarte.getIntersection(2L), Livraison.DEBUT_TOURNEE);
        Livraison livraison2 = new Livraison(testCarte.getIntersection(4L), Livraison.DEBUT_TOURNEE.plusHours(1));

        Tournee tournee = new Tournee(1);
        tournee.addLivraison(livraison1);
        tournee.addLivraison(livraison2);

        tournee.calculerTournee(testCarte);

        Livraison livraison3 = new Livraison(testCarte.getIntersection(6L), Livraison.DEBUT_TOURNEE);
        tournee.addLivraison(testCarte, livraison3, 1);

        assertEquals(3, tournee.getListeLivraisons().size());
        assertEquals(4, tournee.getListeChemins().size());

        assertEquals(livraison1, tournee.getListeLivraisons().get(0));
        assertEquals(livraison3, tournee.getListeLivraisons().get(1));
        assertEquals(livraison2, tournee.getListeLivraisons().get(2));

        assertEquals(LocalTime.of(8,0,0), tournee.getListeLivraisons().get(0).getHeureLivraison());
        assertEquals(LocalTime.of(8,5,0), tournee.getListeLivraisons().get(1).getHeureLivraison());
        assertEquals(LocalTime.of(9,0,0), tournee.getListeLivraisons().get(2).getHeureLivraison());
        assertEquals(LocalTime.of(9,5,0), tournee.getHeureFinTournee());

        assertEquals(testCarte.getEntrepot(), tournee.getListeChemins().get(0).getOrigin());
        assertEquals(livraison1.getDestination(), tournee.getListeChemins().get(0).getDestination());

        assertEquals(livraison1.getDestination(), tournee.getListeChemins().get(1).getOrigin());
        assertEquals(livraison3.getDestination(), tournee.getListeChemins().get(1).getDestination());

        assertEquals(livraison3.getDestination(), tournee.getListeChemins().get(2).getOrigin());
        assertEquals(livraison2.getDestination(), tournee.getListeChemins().get(2).getDestination());

        assertEquals(livraison2.getDestination(), tournee.getListeChemins().get(3).getOrigin());
        assertEquals(testCarte.getEntrepot(), tournee.getListeChemins().get(3).getDestination());
    }

    /**
     * Test de la méthode <code>addLivraison</code> de la classe Tournee.
     * Teste l'ajout d'une livraison à l'indice 2
     */
    @Test
    public void testAjouterApresCalculTournee3() {
        Livraison livraison1 = new Livraison(testCarte.getIntersection(2L), Livraison.DEBUT_TOURNEE);
        Livraison livraison2 = new Livraison(testCarte.getIntersection(4L), Livraison.DEBUT_TOURNEE.plusHours(1));

        Tournee tournee = new Tournee(1);
        tournee.addLivraison(livraison1);
        tournee.addLivraison(livraison2);

        tournee.calculerTournee(testCarte);

        Livraison livraison3 = new Livraison(testCarte.getIntersection(6L), Livraison.DEBUT_TOURNEE);
        tournee.addLivraison(testCarte, livraison3, 2);

        assertEquals(3, tournee.getListeLivraisons().size());
        assertEquals(4, tournee.getListeChemins().size());

        assertEquals(livraison1, tournee.getListeLivraisons().get(0));
        assertEquals(livraison2, tournee.getListeLivraisons().get(1));
        assertEquals(livraison3, tournee.getListeLivraisons().get(2));

        assertEquals(LocalTime.of(8,0,0), livraison1.getHeureLivraison());
        assertEquals(LocalTime.of(9,0,0), livraison2.getHeureLivraison());
        assertEquals(LocalTime.of(9,5,0), livraison3.getHeureLivraison());
        assertEquals(LocalTime.of(9,10,0), tournee.getHeureFinTournee());

        assertEquals(testCarte.getEntrepot(), tournee.getListeChemins().get(0).getOrigin());
        assertEquals(livraison1.getDestination(), tournee.getListeChemins().get(0).getDestination());

        assertEquals(livraison1.getDestination(), tournee.getListeChemins().get(1).getOrigin());
        assertEquals(livraison2.getDestination(), tournee.getListeChemins().get(1).getDestination());

        assertEquals(livraison2.getDestination(), tournee.getListeChemins().get(2).getOrigin());
        assertEquals(livraison3.getDestination(), tournee.getListeChemins().get(2).getDestination());

        assertEquals(livraison3.getDestination(), tournee.getListeChemins().get(3).getOrigin());
        assertEquals(testCarte.getEntrepot(), tournee.getListeChemins().get(3).getDestination());
    }

    /**
     * Test de la méthode <code>addLivraison</code> de la classe Tournee.
     * Teste l'ajout d'une livraison avec un indice invalide
     */
    @Test
    public void testAjouterApresCalculTournee4() {
        Livraison livraison1 = new Livraison(testCarte.getIntersection(2L), Livraison.DEBUT_TOURNEE);
        Livraison livraison2 = new Livraison(testCarte.getIntersection(4L), Livraison.DEBUT_TOURNEE.plusHours(1));

        Tournee tournee = new Tournee(1);
        tournee.addLivraison(livraison1);
        tournee.addLivraison(livraison2);

        tournee.calculerTournee(testCarte);

        Livraison livraison3 = new Livraison(testCarte.getIntersection(6L), Livraison.DEBUT_TOURNEE);

        tournee.addLivraison(testCarte, livraison3, -1);
        assertEquals(2, tournee.getListeLivraisons().size());
        assertEquals(3, tournee.getListeChemins().size());

        tournee.addLivraison(testCarte, livraison3, 3);
        assertEquals(2, tournee.getListeLivraisons().size());
        assertEquals(3, tournee.getListeChemins().size());

    }

    /**
     * Test de la méthode <code>removeLivraison</code> de la classe Tournee.
     * Teste la suppression d'une livraison à l'indice 0
     */
    @Test
    public void testSupprimerApresCalculTournee1(){
        Livraison livraison1 = new Livraison(testCarte.getIntersection(2L), Livraison.DEBUT_TOURNEE);
        Livraison livraison2 = new Livraison(testCarte.getIntersection(4L), Livraison.DEBUT_TOURNEE.plusHours(1));
        Livraison livraison3 = new Livraison(testCarte.getIntersection(6L), Livraison.DEBUT_TOURNEE.plusHours(2));

        Tournee tournee = new Tournee(1);
        tournee.addLivraison(livraison1);
        tournee.addLivraison(livraison2);
        tournee.addLivraison(livraison3);

        tournee.calculerTournee(testCarte);

        tournee.removeLivraison(testCarte, 0);

        assertEquals(2, tournee.getListeLivraisons().size());
        assertEquals(3, tournee.getListeChemins().size());

        assertEquals(livraison2, tournee.getListeLivraisons().get(0));
        assertEquals(livraison3, tournee.getListeLivraisons().get(1));

        assertEquals(LocalTime.of(9,0,0), tournee.getListeLivraisons().get(0).getHeureLivraison());
        assertEquals(LocalTime.of(10,0,0), tournee.getListeLivraisons().get(1).getHeureLivraison());
        assertEquals(LocalTime.of(10,5,0), tournee.getHeureFinTournee());

        assertEquals(testCarte.getEntrepot(), tournee.getListeChemins().get(0).getOrigin());
        assertEquals(livraison2.getDestination(), tournee.getListeChemins().get(0).getDestination());

        assertEquals(livraison2.getDestination(), tournee.getListeChemins().get(1).getOrigin());
        assertEquals(livraison3.getDestination(), tournee.getListeChemins().get(1).getDestination());

        assertEquals(livraison3.getDestination(), tournee.getListeChemins().get(2).getOrigin());
        assertEquals(testCarte.getEntrepot(), tournee.getListeChemins().get(2).getDestination());

    }

    /**
     * Test de la méthode <code>removeLivraison</code> de la classe Tournee.
     * Teste la suppression d'une livraison à l'indice 1
     */
    @Test
    public void testSupprimerApresCalculTournee2(){
        Livraison livraison1 = new Livraison(testCarte.getIntersection(2L), Livraison.DEBUT_TOURNEE);
        Livraison livraison2 = new Livraison(testCarte.getIntersection(4L), Livraison.DEBUT_TOURNEE.plusHours(1));
        Livraison livraison3 = new Livraison(testCarte.getIntersection(6L), Livraison.DEBUT_TOURNEE.plusHours(2));

        Tournee tournee = new Tournee(1);
        tournee.addLivraison(livraison1);
        tournee.addLivraison(livraison2);
        tournee.addLivraison(livraison3);

        tournee.calculerTournee(testCarte);

        tournee.removeLivraison(testCarte, 1);

        assertEquals(2, tournee.getListeLivraisons().size());
        assertEquals(3, tournee.getListeChemins().size());

        assertEquals(livraison1, tournee.getListeLivraisons().get(0));
        assertEquals(livraison3, tournee.getListeLivraisons().get(1));

        assertEquals(LocalTime.of(8,0,0), tournee.getListeLivraisons().get(0).getHeureLivraison());
        assertEquals(LocalTime.of(10,0,0), tournee.getListeLivraisons().get(1).getHeureLivraison());
        assertEquals(LocalTime.of(10,5,0), tournee.getHeureFinTournee());

        assertEquals(testCarte.getEntrepot(), tournee.getListeChemins().get(0).getOrigin());
        assertEquals(livraison1.getDestination(), tournee.getListeChemins().get(0).getDestination());

        assertEquals(livraison1.getDestination(), tournee.getListeChemins().get(1).getOrigin());
        assertEquals(livraison3.getDestination(), tournee.getListeChemins().get(1).getDestination());

        assertEquals(livraison3.getDestination(), tournee.getListeChemins().get(2).getOrigin());
        assertEquals(testCarte.getEntrepot(), tournee.getListeChemins().get(2).getDestination());
    }

    /**
     * Test de la méthode <code>removeLivraison</code> de la classe Tournee.
     * Teste la suppression d'une livraison à l'indice 2
     */
    @Test
    public void testSupprimerApresCalculTournee3(){
        Livraison livraison1 = new Livraison(testCarte.getIntersection(2L), Livraison.DEBUT_TOURNEE);
        Livraison livraison2 = new Livraison(testCarte.getIntersection(4L), Livraison.DEBUT_TOURNEE.plusHours(1));
        Livraison livraison3 = new Livraison(testCarte.getIntersection(6L), Livraison.DEBUT_TOURNEE.plusHours(2));

        Tournee tournee = new Tournee(1);
        tournee.addLivraison(livraison1);
        tournee.addLivraison(livraison2);
        tournee.addLivraison(livraison3);

        tournee.calculerTournee(testCarte);

        tournee.removeLivraison(testCarte, 2);

        assertEquals(2, tournee.getListeLivraisons().size());
        assertEquals(3, tournee.getListeChemins().size());

        assertEquals(livraison1, tournee.getListeLivraisons().get(0));
        assertEquals(livraison2, tournee.getListeLivraisons().get(1));

        assertEquals(LocalTime.of(8,0,0), tournee.getListeLivraisons().get(0).getHeureLivraison());
        assertEquals(LocalTime.of(9,0,0), tournee.getListeLivraisons().get(1).getHeureLivraison());
        assertEquals(LocalTime.of(9,5,0), tournee.getHeureFinTournee());

        assertEquals(testCarte.getEntrepot(), tournee.getListeChemins().get(0).getOrigin());
        assertEquals(livraison1.getDestination(), tournee.getListeChemins().get(0).getDestination());

        assertEquals(livraison1.getDestination(), tournee.getListeChemins().get(1).getOrigin());
        assertEquals(livraison2.getDestination(), tournee.getListeChemins().get(1).getDestination());

        assertEquals(livraison2.getDestination(), tournee.getListeChemins().get(2).getOrigin());
        assertEquals(testCarte.getEntrepot(), tournee.getListeChemins().get(2).getDestination());
    }

    /**
     * Test de la méthode <code>removeLivraison</code> de la classe Tournee.
     * Teste la suppression d'une livraison avec un indice invalide
     */
    @Test
    public void testSupprimerApresCalculTournee4() {
        Livraison livraison1 = new Livraison(testCarte.getIntersection(2L), Livraison.DEBUT_TOURNEE);
        Livraison livraison2 = new Livraison(testCarte.getIntersection(4L), Livraison.DEBUT_TOURNEE.plusHours(1));
        Livraison livraison3 = new Livraison(testCarte.getIntersection(6L), Livraison.DEBUT_TOURNEE.plusHours(2));

        Tournee tournee = new Tournee(1);
        tournee.addLivraison(livraison1);
        tournee.addLivraison(livraison2);
        tournee.addLivraison(livraison3);

        tournee.calculerTournee(testCarte);

        tournee.removeLivraison(testCarte, -1);
        assertEquals(3, tournee.getListeLivraisons().size());
        assertEquals(4, tournee.getListeChemins().size());

        tournee.removeLivraison(testCarte, 3);
        assertEquals(3, tournee.getListeLivraisons().size());
        assertEquals(4, tournee.getListeChemins().size());
    }

    /**
     * Test de la méthode <code>removeLivraison</code> de la classe Tournee.
     * Teste la suppression d'une livraison lorsque la tournée n'a qu'une seule livraison
     */
    @Test
    public void testSupprimerApresCalculTournee5() {
        Livraison livraison1 = new Livraison(testCarte.getIntersection(2L), Livraison.DEBUT_TOURNEE);

        Tournee tournee = new Tournee(1);
        tournee.addLivraison(livraison1);

        tournee.calculerTournee(testCarte);

        tournee.removeLivraison(testCarte, 0);
        assertEquals(0, tournee.getListeLivraisons().size());
        assertEquals(0, tournee.getListeChemins().size());
    }

    /**
     * Test de la méthode <code>removeLivraison</code> de la classe Tournee.
     * Teste la suppression d'une livraison la tournée n'a été calculée
     */
    @Test
    public void testSupprimerApresCalculTournee6() {
        Livraison livraison1 = new Livraison(testCarte.getIntersection(2L), Livraison.DEBUT_TOURNEE);

        Tournee tournee = new Tournee(1);
        tournee.addLivraison(livraison1);

        tournee.removeLivraison(testCarte, 0);
        assertEquals(0, tournee.getListeChemins().size());
    }


}
