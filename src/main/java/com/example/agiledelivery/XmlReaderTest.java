package com.example.agiledelivery;

import com.example.xml.XMLOpener;

public class XmlReaderTest {

    public static void main(String[] args) {
        XMLOpener opener = XMLOpener.getInstance();
        try {
            opener.readFile("data/xml/smallMap.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}