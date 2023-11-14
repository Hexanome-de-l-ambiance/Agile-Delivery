package com.example.agiledelivery;

import com.example.controller.Controller;
import com.example.model.Livraison;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;

public class ButtonListener implements EventHandler<ActionEvent> {
    private Controller controller;
    private TextualView textualView;
    public ButtonListener(Controller controller) {
        this.controller = controller;
    }

    public void setTextualView(TextualView textualView) {
        this.textualView = textualView;
    }

    @Override
    @FXML
    public void handle(ActionEvent event) {
        String actionCommand;
        if(event.getSource().getClass().equals(MenuItem.class)){
            actionCommand = ((MenuItem) event.getSource()).getId();
        } else {
            actionCommand = ((Button) event.getSource()).getId();
        }
        System.out.println(actionCommand);

        // Forward the corresponding message to the controller based on the button's text
        switch (actionCommand) {
            case Window.LOAD_PLAN -> controller.load();
            case Window.ADD_DESTINATION -> {
                try {
                    controller.addDelivery(Integer.parseInt(textualView.getComboBoxCouriers().getValue()), Integer.parseInt(textualView.getComboBoxIntervals().getValue()));
                } catch (NumberFormatException e) {
                    textualView.showAlert("Veuillez choisir un numero de coursier");
                }
            }
            case Window.CALCULATE_TOUR -> controller.calculateDelivery();
            case Window.UNDO -> controller.undo();
            case Window.REDO -> controller.redo();
            case Window.RESET_NB_COURIERS -> {
                try {
                    controller.modiferCoursiers(Integer.parseInt(textualView.getTextArea().getText()));
                } catch (NumberFormatException e) {
                    textualView.showAlert("Veuillez saisir un entier positif");
                }
            }
            case Window.REMOVE -> {
                int numeroCoursier = textualView.getNumeroCoursier();
                Livraison livraison = textualView.getLivraison();
                if (numeroCoursier == -1 || livraison == null) {
                    textualView.showAlert("Livraison à supprimer non choisie");
                } else {
                    controller.deleteDelivery(numeroCoursier, livraison);
                }
            }
        }
    }
}