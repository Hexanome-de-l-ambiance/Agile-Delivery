package com.example.controller;

import com.example.model.Carte;
import com.example.xml.CustomXMLParsingException;
import com.example.xml.XMLOpener;
import javafx.stage.Stage;

/**
 *
 */
public class EtatInitial implements Etat {

    @Override
    public void load(Carte carte, ListeDeCommandes l, Stage stage){
        try{
            XMLOpener.getInstance().readFile(stage, carte);
        } catch (CustomXMLParsingException e) {
            throw new RuntimeException(e);
        }
    }
}