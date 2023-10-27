package com.example.controller;

import com.example.xml.XMLOpener;

/**
 * 
 */
public class CommandeLireTournee implements Commande{


    private XMLOpener reader;
    /**
     * Default constructor
     */
    public CommandeLireTournee(XMLOpener reader) {

        this.reader = reader;
    }

    @Override
    public void execute() {

    }

    @Override
    public void undo() {

    }
}