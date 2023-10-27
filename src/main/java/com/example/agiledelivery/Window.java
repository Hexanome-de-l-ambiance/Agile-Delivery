package com.example.agiledelivery;

import com.example.controller.Controller;
import com.example.model.Carte;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.util.ArrayList;


public class Window extends Application {
    private TextualView textualView;
    private GraphicalView graphicalView;
    private ButtonListener buttonListener;
    private MouseListener mouseListener;

    protected static final int PREFWIDTH = 1600;
    protected static final int PREFHEIGHT = 800;
    protected static final double textualViewScale = 0.2;
    protected static final double graphicalViewScale = 0.8;
    protected final int buttonHeight = 50;
    protected static final String LOAD_PLAN = "Charger un plan";
    protected static final String ADD_DESTINATION = "Ajouter une destination";
    protected static final String CALCULATE_TOUR = "Calculer la tournée";
    protected static final String UNDO = "Undo";
    protected static final String REDO = "Redo";

    private final String[] buttonTexts = new String[]{LOAD_PLAN, ADD_DESTINATION, CALCULATE_TOUR, UNDO, REDO};
    private ArrayList<Button> buttons;


    @Override
    public void start(Stage primaryStage) throws Exception {
        Carte carte = new Carte();
        Controller controller = new Controller(carte, primaryStage);

        textualView = new TextualView(carte);
        graphicalView = new GraphicalView(carte);

        initializeButtons(controller);
        mouseListener = new MouseListener(graphicalView, controller);
        HBox mainLayout = new HBox(graphicalView, textualView);
        Scene scene = new Scene(mainLayout, PREFWIDTH, PREFHEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void initializeButtons(Controller controller) {
        buttonListener = new ButtonListener(controller);
        buttons = new ArrayList<Button>();
        for (String text : buttonTexts){
            Button button = new Button(text);
            buttons.add(button);
            button.setLayoutX(0);
            button.setLayoutY((buttons.size()-1)*buttonHeight);
            button.setOnAction(buttonListener);
            textualView.getChildren().add(button); // Add the button to the left pane
        }
    }


    public static void main(String[] args) {
        launch(args);

    }
}