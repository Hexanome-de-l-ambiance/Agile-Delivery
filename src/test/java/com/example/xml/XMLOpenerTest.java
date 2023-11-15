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
    public void testLoadMapWithNullFile() {
        XMLOpener xmlOpener = new XMLOpener();
        Carte carte = new Carte(1);

        Platform.startup(() -> {
            Stage stage = new Stage();
            try {
                xmlOpener.readFile(stage, carte);
                fail("Expected an exception to be thrown");
            } catch (RuntimeException | CustomXMLParsingException e) {
                assertEquals("File null", e.getMessage());
            }
        });
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





//    @Test
//    public void readValidFileTest() {
//        File testFile = new File(tempDirectory, "test.xml");
//        try (FileWriter writer = new FileWriter(testFile)) {
//            writer.write("<?xml version=\"1.0\"?>\n<root>\n" +
//                    "<intersection id=\"1\" latitude=\"10.0\" longitude=\"20.0\" />\n" +
//                    "<segment destination=\"2\" length=\"100\" name=\"segment1\" origin=\"1\" />\n" +
//                    "</root>");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        assertDoesNotThrow(() -> xmlOpener.readFile(carte, testFile.getAbsolutePath()), "Il ne doit pas lancer d'exception pour un XML valide");
//    }
//



}
