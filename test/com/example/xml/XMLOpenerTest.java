package com.example.xml;

import com.example.model.Carte;
import com.example.xml.XMLOpener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

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
