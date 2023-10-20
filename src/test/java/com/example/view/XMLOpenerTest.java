package com.example.view;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;

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
        assertNotNull(xmlOpener, "XMLOpener instance should not be null");
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

        assertDoesNotThrow(() -> xmlOpener.ReadFile(testFile.getAbsolutePath()), "Should not throw an exception for a valid XML");
    }


}
