package com.example.model;

import com.example.xml.XMLOpener;

public class Textview {

    public static void main(String[] args) {
        XMLOpener opener = XMLOpener.getInstance();
        Carte carte = new Carte();
        try {
            opener.ReadFile(carte, "data/xml/smallMap.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
        carte.Info();
    }
}