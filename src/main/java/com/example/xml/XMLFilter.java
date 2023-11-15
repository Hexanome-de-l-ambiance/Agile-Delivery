package com.example.xml;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import java.io.File;

/**
 * Cette classe gère la sélection et l'ouverture/enregistrement de fichiers XML en utilisant JavaFX FileChooser.
 */
public class XMLFilter {
    // Singleton pattern
    private XMLFilter() {}

    private static class SingletonHelper {
        private static final XMLFilter INSTANCE = new XMLFilter();
    }

    /**
     * Récupère l'instance unique de XMLFilter selon le modèle de conception Singleton.
     *
     * @return L'instance unique de XMLFilter.
     */
    public static XMLFilter getInstance() {
        return XMLFilter.SingletonHelper.INSTANCE;
    }

    /**
     * Affiche une boîte de dialogue pour ouvrir/enregistrer un fichier XML en fonction du paramètre 'read'.
     *
     * @param stage La fenêtre parente pour la boîte de dialogue FileChooser.
     * @param read  Booléen indiquant s'il faut ouvrir (true) ou enregistrer (false) un fichier.
     * @return Le fichier sélectionné ou null s'il n'est pas sélectionné.
     */
    public File open(Stage stage, boolean read){
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