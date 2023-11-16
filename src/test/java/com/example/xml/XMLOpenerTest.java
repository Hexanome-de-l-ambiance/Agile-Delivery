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

}
