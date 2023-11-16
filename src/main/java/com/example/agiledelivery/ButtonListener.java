package com.example.agiledelivery;

import com.example.controller.*;
import com.example.model.Livraison;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;

/**
 * Cette classe est responsable de la gestion des événements de boutons et de l'envoi des actions correspondantes au contrôleur.
 */
public class ButtonListener implements EventHandler<ActionEvent> {

    private Controller controller;

    private TextualView textualView;

    /**
     * Constructeur pour définir le contrôleur.
     *
     * @param controller Le contrôleur à définir
     */
    public ButtonListener(Controller controller) {
        this.controller = controller;
    }

    /**
     * Définir la vue textuelle pour suivre les informations choisies par l'utilisateur.
     *
     * @param textualView La vue textuelle à définir
     */
    public void setTextualView(TextualView textualView) {
        this.textualView = textualView;
    }

    /**
     * Gérer l'événement d'action généré par les boutons ou les outils.
     *
     * @param event L'événement écouté par le listener
     */
    @Override
    public void handle(ActionEvent event) {
        String actionCommand;
        if(event.getSource().getClass().equals(MenuItem.class)){
            actionCommand = ((MenuItem) event.getSource()).getId();
        } else {
            actionCommand = ((Button) event.getSource()).getId();
        }

        switch (actionCommand) {
            case Window.LOAD_PLAN: controller.load(); break;
            case Window.ADD_DESTINATION:{
                if (controller.getEtatCourant() instanceof EtatInitial){
                    textualView.showAlert("Aucune carte n'est chargée. Veuillez charger une carte avant d'ajouter des destinations.");
                } else if (!(controller.getEtatCourant() instanceof EtatAjoutDestination || controller.getEtatCourant() instanceof EtatAjoutDestination2)) {
                    textualView.showAlert("Veuillez sélectionner un point de livraison à ajouter.");
                } else {
                    try {
                        if(textualView.isCalculated()){
                            controller.addDelivery(Integer.parseInt(textualView.getComboBoxCouriers().getValue()), Integer.parseInt(textualView.getComboBoxIntervals().getValue()), 0);
                        } else {
                            controller.addDelivery(Integer.parseInt(textualView.getComboBoxCouriers().getValue()), Integer.parseInt(textualView.getComboBoxIntervals().getValue()));
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
                    controller.modifierCoursiers(Integer.parseInt(textualView.getTextField().getText()));
                } catch (NumberFormatException e){
                    textualView.showAlert("Veuillez saisir un entier positif");
                }
                break;
            }
            case Window.REMOVE:{
                int numeroCoursier = textualView.getNumeroCoursier();
                Livraison livraison = textualView.getLivraison();
                if (numeroCoursier == -1 || livraison == null) {
                    textualView.showAlert("Livraison non choisie");
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
                    textualView.showAlert("Livraison non choisie");
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
                    textualView.showAlert("Veuillez choisir une livraison et une destination");
                } else {
                    try {
                        controller.addDelivery(numeroCoursier, Integer.parseInt(textualView.getComboBoxIntervals().getValue()), index);
                    } catch (NumberFormatException e) {
                        textualView.showAlert("Veuillez choisir un fenêtre temporelle");
                    }
                }
                break;
            }
            case Window.ADD_DESTINATION_AFTER:{
                int numeroCoursier = textualView.getNumeroCoursier();
                Livraison livraison = textualView.getLivraison();
                int index = textualView.getSelectedIndex();
                if(numeroCoursier == -1 || livraison == null){
                    textualView.showAlert("Veuillez choisir une livraison et une destination");
                } else {
                    try {
                        controller.addDelivery(numeroCoursier, Integer.parseInt(textualView.getComboBoxIntervals().getValue()), index + 1);
                    } catch (NumberFormatException e) {
                        textualView.showAlert("Veuillez choisir un fenêtre temporelle");
                    }
                }
                break;
            }
            case Window.GENERATE: controller.genererFeuilleRoute(); break;
        }
    }
}