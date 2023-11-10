package com.example.agiledelivery;

import com.example.controller.Controller;
import com.example.controller.EtatCarteChargee;
import com.example.controller.EtatInitial;
import com.example.model.Livraison;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;


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
    public void handle(ActionEvent event) {
        String actionCommand = ((Button) event.getSource()).getText();

        // Forward the corresponding message to the controller based on the button's text
        switch (actionCommand) {
            case Window.LOAD_PLAN: controller.load(); break;
            case Window.ADD_DESTINATION:{
                if (controller.getEtatCourant() instanceof EtatInitial){
                    textualView.showAlert("Aucune carte n'est chargée. Veuillez charger une carte avant d'ajouter des destinations.");
                } else if (!(controller.getEtatCourant() instanceof EtatCarteChargee)) {
                    textualView.showAlert("Veuillez sélectionner un point de livraison à ajouter.");
                } else {
                    try {
                        if(textualView.isCalculated()){
                            controller.addDelivery(Integer.parseInt(textualView.getComboBox().getValue()), Integer.parseInt(textualView.getComboBoxIntervals().getValue()), 0);
                        } else {
                            controller.addDelivery(Integer.parseInt(textualView.getComboBox().getValue()), Integer.parseInt(textualView.getComboBoxIntervals().getValue()));
                        }
                    } catch (NumberFormatException e){
                        textualView.showAlert("Veuillez choisir un numero de coursier et un fenêtre temporelle");
                    }
                }
                break;
            }
            case Window.CALCULATE_TOUR: controller.calculateDelivery();  break;
            case Window.LOAD_TOUR: controller.loadTour(); break;
            case Window.SAVE_TOUR: controller.saveTour(); break;
            case Window.UNDO: controller.undo(); break;
            case Window.REDO: controller.redo(); break;
            case Window.RESET_NB_COURIERS: {
                try {
                    controller.modiferCoursiers(Integer.parseInt(textualView.getTextArea().getText()));
                } catch (NumberFormatException e){
                    textualView.showAlert("Veuillez saisir un entier positif");
                }
                break;
            }
            case Window.REMOVE:{
                int numeroCoursier = textualView.getNumeroCoursier();
                Livraison livraison = textualView.getLivraison();
                if(numeroCoursier == -1 || livraison == null){
                    textualView.showAlert("Livraison à supprimer non choisie");
                } else {
                    controller.deleteDelivery(numeroCoursier, livraison);
                }
                break;
            }
            case Window.REMOVE_AFTER_CALCULATED:{
                int numeroCoursier = textualView.getNumeroCoursier();
                Livraison livraison = textualView.getLivraison();
                int index = textualView.getSelectedIndex();
                if(numeroCoursier == -1 || livraison == null){
                    textualView.showAlert("Livraison à supprimer non choisie");
                } else {
                    controller.deleteDelivery(numeroCoursier, livraison, index);
                }
                break;
            }
            case Window.RESET: controller.reset(); break;
            case Window.ADD_DESTINATION_BEFORE:{
                int numeroCoursier = textualView.getNumeroCoursier();
                Livraison livraison = textualView.getLivraison();
                int index = textualView.getSelectedIndex();
                if(numeroCoursier == -1 || livraison == null){
                    textualView.showAlert("Livraison à supprimer non choisie");
                }
                try {
                    controller.addDelivery(numeroCoursier, Integer.parseInt(textualView.getComboBoxIntervals().getValue()), index);
                } catch (NumberFormatException e){
                    textualView.showAlert("Veuillez choisir un fenêtre temporelle");
                }
                break;
            }
            case Window.ADD_DESTINATION_AFTER:{
                int numeroCoursier = textualView.getNumeroCoursier();
                Livraison livraison = textualView.getLivraison();
                int index = textualView.getSelectedIndex();
                if(numeroCoursier == -1 || livraison == null){
                    textualView.showAlert("Livraison à supprimer non choisie");
                }
                try {
                    controller.addDelivery(numeroCoursier, Integer.parseInt(textualView.getComboBoxIntervals().getValue()), index+1);
                } catch (NumberFormatException e){
                    textualView.showAlert("Veuillez choisir un fenêtre temporelle");
                }
                break;
            }
        }
    }
}