package com.example.xml;

import com.example.controller.Controller;
import com.example.controller.ListeDeCommandes;
import com.example.model.*;
import com.example.xml.XMLOpener;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.xml.sax.SAXParseException;


import java.io.File;
import java.io.FileWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class XMLOpenerTest {

    @TempDir
    File tempDirectory;

    private XMLOpener xmlOpener;
    private Carte carte;

    @BeforeEach
    public void setUp() {
        xmlOpener = XMLOpener.getInstance();
    }

    @Test
    public void testSingletonInstance() {
        XMLOpener instance1 = XMLOpener.getInstance();
        XMLOpener instance2 = XMLOpener.getInstance();
        assertSame(instance1, instance2, "Les instances du singleton doivent être les mêmes");
    }

    @Test
    public void testLoadMapWithInvalidFile() {
        XMLOpener xmlOpener = new XMLOpener();
        String path = "data/xml/testTourneeTestMap.xml";
        Carte carte = new Carte(1);

        try {
            xmlOpener.readFile(carte, path);
            fail("Expected an exception to be thrown");
        } catch (RuntimeException | CustomXMLParsingException e) {
            assertEquals("Invalid XML file", e.getMessage());
        }
    }

    @Test
    public void testLoadMapWithValidFile() {
        XMLOpener xmlOpener = new XMLOpener();
        String path1 = "data/xml/testMap.xml";
        String path2 = "data/xml/smallMap.xml";
        String path3 = "data/xml/mediumMap.xml";
        String path4 = "data/xml/largeMap.xml";

        Carte carte = new Carte(1);

        try {
            xmlOpener.readFile(carte, path1);
            xmlOpener.readFile(carte, path2);
            xmlOpener.readFile(carte, path3);
            xmlOpener.readFile(carte, path4);
        } catch (RuntimeException | CustomXMLParsingException e) {
            fail("Expected no exception to be thrown");
        }
    }
}
