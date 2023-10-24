package com.example.agiledelivery;

import com.example.model.Carte;
import com.example.xml.XMLOpener;

public class XmlReaderExample {

    public static void main(String[] args) {
        Carte carte = new Carte();
        XMLOpener opener = XMLOpener.getInstance();
        try {
            opener.ReadFile(carte,"data/xml/smallMap.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}