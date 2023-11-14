package com.example.agiledelivery;

import com.example.controller.Controller;
import com.example.model.Carte;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

import static java.lang.Math.abs;


public class Window extends Application {

    private TextualView textualView;
    private GraphicalView graphicalView;
    private ButtonListener buttonListener;
    private MouseListener mouseListener;
    protected static final int PREFWIDTH = 1600;
    protected static final int PREFHEIGHT = 800;
    protected static final double textualViewScale = 0.2;
    protected static final double graphicalViewScale = 0.8;
    protected final int buttonHeight = 30;
    protected static final String LOAD_PLAN = "loadMapButton";
    protected static final String ADD_DESTINATION = "ajouterLivraisonButton";
    protected static final String CALCULATE_TOUR = "calculerTourneeButton";
    protected static final String UNDO = "undoButton";
    protected static final String REDO = "redoButton";
    protected static final String RESET_NB_COURIERS = "Modifier le nombre de coursiers";
    protected static final String REMOVE = "supprimerLivraisonButton";
    protected static final String NB_COURIERS = "Numero de coursier : ";
    protected static final String INTERVAL = "Choisir une fenêtre temporelle : ";
    private final String[] buttonTexts = new String[]{LOAD_PLAN, UNDO, REDO, CALCULATE_TOUR};
    private ArrayList<Button> buttons;


    @Override
    public void start(Stage primaryStage) throws Exception {
        Carte carte = new Carte(1);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view.fxml"));
        Controller controller = new Controller(carte,primaryStage);
        loader.setController(controller);
        Scene scene = loader.load();
        primaryStage.setScene(scene);
        primaryStage.show();

        /*Carte carte = new Carte(1);
        Controller controller = new Controller(carte, primaryStage);

        textualView = new TextualView(carte);
        graphicalView = new GraphicalView(carte);

        initializeButtons(controller);
        initializeRequestArea();
        buttonListener.setTextualView(textualView);
        mouseListener = new MouseListener(textualView, graphicalView, controller);
        graphicalView.setMouseListener(mouseListener);
        HBox mainLayout = new HBox(graphicalView, textualView);
        **/

        /*scene.widthProperty().addListener((observable, oldValue, newValue) -> {
            textualView.setPrefWidth(textualView.getPrefWidth()+(newValue.intValue()-oldValue.intValue()));
            graphicalView.setPrefWidth(graphicalView.getPrefWidth()+(newValue.intValue()-oldValue.intValue()));
        });

        scene.heightProperty().addListener((observable, oldValue, newValue) -> {
            textualView.setPrefHeight(textualView.getPrefHeight()+(newValue.intValue()-oldValue.intValue()));
            graphicalView.setPrefHeight(graphicalView.getPrefHeight()+(newValue.intValue()-oldValue.intValue()));

        });*/
    }
/*
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

    public void initializeRequestArea(){
        Button button1 = new Button(ADD_DESTINATION);
        buttons.add(button1);
        button1.setLayoutX(0);
        button1.setLayoutY((buttons.size()-1)*buttonHeight);
        button1.setOnAction(buttonListener);
        textualView.getChildren().add(button1);
        Text text1 = new Text(NB_COURIERS);
        text1.setLayoutX(0);
        text1.setLayoutY((buttons.size()+0.5)*buttonHeight);
        textualView.getChildren().add(text1);
        ComboBox<String> comboBox1 = new ComboBox<>();
        comboBox1.setLayoutX(0);
        comboBox1.setLayoutY((buttons.size()+1)*buttonHeight);
        textualView.getChildren().add(comboBox1);
        textualView.setComboBox(comboBox1);

        Text text2 = new Text(INTERVAL);
        text2.setLayoutX(0);
        text2.setLayoutY((buttons.size()+2.5)*buttonHeight);
        textualView.getChildren().add(text2);
        ComboBox<String> comboBox2 = new ComboBox<>();
        comboBox2.setLayoutX(0);
        comboBox2.setLayoutY((buttons.size()+3)*buttonHeight);
        textualView.getChildren().add(comboBox2);
        textualView.setComboBoxIntervals(comboBox2);

        Button button2 = new Button(RESET_NB_COURIERS);
        buttons.add(button2);
        button2.setLayoutX(0);
        button2.setLayoutY((buttons.size()+3)*buttonHeight);
        button2.setOnAction(buttonListener);
        textualView.getChildren().add(button2);
        TextArea textArea = new TextArea();
        textArea.setLayoutX(0);
        textArea.setLayoutY((buttons.size()+4)*buttonHeight);
        textArea.setPrefHeight(buttonHeight);
        textualView.getChildren().add(textArea);
        textualView.setTextArea(textArea);

        Button button3 = new Button(REMOVE);
        buttons.add(button3);
        button3.setLayoutX(0);
        button3.setLayoutY((buttons.size()+5)*buttonHeight);
        button3.setOnAction(buttonListener);
        textualView.getChildren().add(button3);
    }
*/

    public static void main(String[] args) {
        launch(args);

    }
}