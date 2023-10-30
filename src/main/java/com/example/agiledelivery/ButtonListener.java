package com.example.agiledelivery;

import com.example.controller.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class ButtonListener implements EventHandler<ActionEvent> {
    private Controller controller;
    public ButtonListener(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void handle(ActionEvent event) {
        String actionCommand = ((Button) event.getSource()).getText();

        // Forward the corresponding message to the controller based on the button's text
        switch (actionCommand) {
            case Window.LOAD_PLAN: controller.load(); break;
            case Window.ADD_DESTINATION: controller.addDelivery(); break;
            case Window.CALCULATE_TOUR: controller.calculateDelivery();  break;
            case Window.UNDO: controller.undo(); break;
            case Window.REDO: break;
        }
    }
}