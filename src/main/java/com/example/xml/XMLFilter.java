package com.example.xml;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import java.io.File;

public class XMLFilter {
    // Singleton pattern
    private XMLFilter() {}

    private static class SingletonHelper {
        private static final XMLFilter INSTANCE = new XMLFilter();
    }

    public static XMLFilter getInstance() {
        return XMLFilter.SingletonHelper.INSTANCE;
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

        return selectedFile;
    }
}