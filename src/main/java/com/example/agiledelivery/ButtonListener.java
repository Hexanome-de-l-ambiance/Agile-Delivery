package com.example.agiledelivery;

import com.example.controller.Controller;
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
                try {
                    controller.addDelivery(Integer.parseInt(textualView.getComboBox().getValue()), Integer.parseInt(textualView.getComboBoxIntervals().getValue()));
                } catch (NumberFormatException e){
                    textualView.showAlert("Veuillez choisir un numero de coursier");
                }
                break;
            }
            case Window.CALCULATE_TOUR: controller.calculateDelivery();  break;
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
        }
    }
}