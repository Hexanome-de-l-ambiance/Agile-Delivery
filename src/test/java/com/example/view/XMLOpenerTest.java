package com.example.view;

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

    @BeforeEach
    public void setUp() {
        xmlOpener = XMLOpener.getInstance();
    }

    @Test
    public void getInstanceTest() {
        assertNotNull(xmlOpener, "XMLOpener instance ne peut pas être null");
    }

    @Test
    public void readValidFileTest() {
        File testFile = new File(tempDirectory, "test.xml");
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("<?xml version=\"1.0\"?>\n<root>\n" +
                    "<intersection id=\"1\" latitude=\"10.0\" longitude=\"20.0\" />\n" +
                    "<segment destination=\"2\" length=\"100\" name=\"segment1\" origin=\"1\" />\n" +
                    "</root>");
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertDoesNotThrow(() -> xmlOpener.readFile(testFile.getAbsolutePath()), "Il ne doit pas lancer d'exception pour un XML valide");
    }

    @Test
    public void readInvalidFileTest() {
        File testFile = new File(tempDirectory, "invalidTest.xml");
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("<?xml version=\"1.0\"?>\n<root>\n" +
                    "<intersection id=\"1\" latitude=\"10.0\" longitude=\"20.0\" "); // Intentionally incomplete XML
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThrows(Exception.class, () -> xmlOpener.readFile(testFile.getAbsolutePath()), "Il doit lancer une exception pour un XML invalide");
    }

    @Test
    public void readNonExistentFileTest() {
        String fakeFilePath = "path/to/nonexistent/file.xml";
        assertThrows(Exception.class, () -> xmlOpener.readFile(fakeFilePath), "Il doit lancer une exception pour un fichier inexistant");
    }

    @Test
    public void readEmptyFileTest() {
        File testFile = new File(tempDirectory, "emptyTest.xml");
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write(""); // Empty content
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThrows(Exception.class, () -> xmlOpener.readFile(testFile.getAbsolutePath()), "Il doit lancer une exception pour un fichier vide");
    }

    @Test
    public void singletonInstanceTest() {
        XMLOpener instance1 = XMLOpener.getInstance();
        XMLOpener instance2 = XMLOpener.getInstance();
        assertSame(instance1, instance2, "Les instances du singleton doivent être les mêmes");
    }
}
