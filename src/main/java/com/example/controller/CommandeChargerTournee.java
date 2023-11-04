package com.example.controller;

import com.example.model.*;
import com.example.xml.*;
import javafx.stage.Stage;

public class CommandeChargerTournee implements Commande{

    private Carte carte;
    private Tournee currentTournee;
    private Stage stage;
    private Tournee previousTournee;

    public CommandeChargerTournee(Stage stage, Carte carte) {
        this.stage = stage;
        this.carte = carte;
        this.currentTournee = new Tournee();
    }

    @Override
    public void execute() {
        try {
            // Save current state for undo
            previousTournee = currentTournee.clone(); // This requires Tournee to implement clone, which does deep copying.

            // Load the tour data into a new Tournee object
            XMLOpener.getInstance().readFile(stage, carte);

            // After loading, you would typically convert the loaded Carte into a series of Chemins and Livraisons
            // This would involve additional logic to translate Carte data into the Tournee's linked list and array list.
            // For example:
            // currentTournee.calculateTourneeFromCarte(carte);

        } catch (CustomXMLParsingException e) {
            // Log the exception and handle it accordingly
            e.printStackTrace();
            // Potentially revert to previous state or notify user of error
        }
    }

    @Override
    public void undo() {
        // Restore the previous state of Tournee
        // This assumes that you have a way to restore the previous state effectively
        if (previousTournee != null) {
            currentTournee = previousTournee;
            // Optionally, revert any additional state changes that occurred during execute
        }
    }

    // Additional methods to access the currentTournee if needed
    public Tournee getCurrentTournee() {
        return currentTournee;
    }

    public void setCurrentTournee(Tournee tournee) {
        this.currentTournee = tournee;
    }
}
