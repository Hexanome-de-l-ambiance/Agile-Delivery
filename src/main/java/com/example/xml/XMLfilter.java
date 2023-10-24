package com.example.xml;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import java.io.File;

public class XMLfilter {
    // Singleton pattern
    private XMLfilter() {}

    private static class SingletonHelper {
        private static final XMLfilter INSTANCE = new XMLfilter();
    }

    public static XMLfilter getInstance() {
        return XMLfilter.SingletonHelper.INSTANCE;
    }

    public File open(Stage stage, boolean read) throws CustomXMLParsingException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new ExtensionFilter("XML Files", "*.xml"));
        fileChooser.setTitle("Open XML File");

        File selectedFile;
        if (read) {
            selectedFile = fileChooser.showOpenDialog(stage);
        } else {
            selectedFile = fileChooser.showSaveDialog(stage);
        }

        if (selectedFile == null) {
            throw new CustomXMLParsingException("Problem when opening file");
        }
        return selectedFile;
    }
}