package com.example.xml;

import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * Cette classe gère l'ouverture d'un répertoire en utilisant JavaFX directoryChooser.
 */
public class DirectoryMaker {
    private DirectoryMaker() {}

    private static class SingletonHelper {
        private static final DirectoryMaker INSTANCE = new DirectoryMaker();
    }

    public static DirectoryMaker getInstance() {
        return DirectoryMaker.SingletonHelper.INSTANCE;
    }

    public File open(Stage stage) throws CustomXMLParsingException {

        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(stage);

        if (selectedDirectory == null) {
            throw new CustomXMLParsingException("Pas de répertoire sélectionné");
        }
        return selectedDirectory;
    }
}
