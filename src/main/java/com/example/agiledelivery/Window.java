package com.example.agiledelivery;

import com.example.controller.Controller;
import com.example.agiledelivery.ViewController;
import com.example.model.Carte;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.util.ArrayList;

import static java.lang.Math.abs;


public class Window extends Application {
    private TextualView textualView;
    private GraphicalView graphicalView;
    private ButtonListener buttonListener;
    private MouseListener mouseListener;

    private VBox vbButtons = new VBox();
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
    protected static final String LOAD_TOUR = "chargerTourneeButton";
    protected static final String SAVE_TOUR = "sauvegarderTourneeButton";
    protected static final String RESET = "resetTourneeButton";

    protected static final String RESET_NB_COURIERS = "changeNumberCouriersButton";
    protected static final String REMOVE = "supprimerLivraisonButton";
    protected static final String NB_COURIERS = "Numero de coursier : ";
    protected static final String ADD_DESTINATION_BEFORE = "ajouterAvantButton";
    protected static final String ADD_DESTINATION_AFTER = "ajouterApresButton";
    protected static final String REMOVE_AFTER_CALCULATED = "supprimerApresTourneeButton";
    protected static final String ID_COURIER = "Numero de coursier : ";

    private final String[] buttonTexts = new String[]{LOAD_PLAN, UNDO, REDO, CALCULATE_TOUR, LOAD_TOUR, SAVE_TOUR, RESET};

    private ArrayList<Button> buttons;


    @Override
    public void start(Stage primaryStage) throws Exception {
        Carte carte = new Carte(1);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view.fxml"));
        ViewController controller = new ViewController(carte, new Controller(carte,primaryStage));
        loader.setController(controller);
        Scene scene = loader.load();
        primaryStage.setScene(scene);
        primaryStage.show();

        /*Carte carte = new Carte(1);
        Controller controller = new Controller(carte, primaryStage);

        textualView = new TextualView(carte);
        graphicalView = new GraphicalView(carte);
        vbButtons.setSpacing(10);
        vbButtons.setPadding(new Insets(0, 35, buttonHeight, 35));
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
            button.setMaxWidth(Double.MAX_VALUE);
            button.setOnAction(buttonListener);
            vbButtons.getChildren().add(button);
        }
        textualView.getChildren().add(vbButtons);
    }

    public void initializeRequestArea(){

        Button button_add = new Button(ADD_DESTINATION);
        buttons.add(button_add);
        button_add.setMaxWidth(Double.MAX_VALUE);
        button_add.setOnAction(buttonListener);
        vbButtons.getChildren().add(button_add);
        textualView.setButton_add(button_add);

        Button button_add_before = new Button(ADD_DESTINATION_BEFORE);
        buttons.add(button_add_before);
        button_add_before.setMaxWidth(Double.MAX_VALUE);
        button_add_before.setOnAction(buttonListener);
        vbButtons.getChildren().add(button_add_before);
        button_add_before.setManaged(false);
        button_add_before.setDisable(true);
        button_add_before.setVisible(false);
        textualView.setButton_add_before(button_add_before);

        Button button_add_after = new Button(ADD_DESTINATION_AFTER);
        buttons.add(button_add_after);
        button_add_after.setMaxWidth(Double.MAX_VALUE);
        button_add_after.setOnAction(buttonListener);
        vbButtons.getChildren().add(button_add_after);
        button_add_after.setManaged(false);
        button_add_after.setDisable(true);
        button_add_after.setVisible(false);
        textualView.setButton_add_after(button_add_after);

        Text text1 = new Text(ID_COURIER);
        vbButtons.getChildren().add(text1);
        textualView.setTextNumeroCoursier(text1);
        ComboBox<String> comboBox1 = new ComboBox<>();
        vbButtons.getChildren().add(comboBox1);
        textualView.setComboBox(comboBox1);

        Text text2 = new Text(INTERVAL);
        vbButtons.getChildren().add(text2);
        ComboBox<String> comboBox2 = new ComboBox<>();
        vbButtons.getChildren().add(comboBox2);
        textualView.setComboBoxIntervals(comboBox2);

        Button button2 = new Button(RESET_NB_COURIERS);
        buttons.add(button2);
        button2.setMaxWidth(Double.MAX_VALUE);
        button2.setOnAction(buttonListener);
        vbButtons.getChildren().add(button2);
        TextArea textArea = new TextArea();

        textArea.setPrefHeight(buttonHeight);
        textArea.setPrefWidth(PREFWIDTH*textualViewScale*0.8);
        vbButtons.getChildren().add(textArea);
        textualView.setTextArea(textArea);

        Button button_remove = new Button(REMOVE);
        buttons.add(button_remove);
        button_remove.setMaxWidth(Double.MAX_VALUE);
        button_remove.setOnAction(buttonListener);
        vbButtons.getChildren().add(button_remove);
        textualView.setButton_remove(button_remove);

        Button button_remove_after = new Button(REMOVE_AFTER_CALCULATED);
        buttons.add(button_remove_after);
        button_remove_after.setMaxWidth(Double.MAX_VALUE);
        button_remove_after.setOnAction(buttonListener);
        vbButtons.getChildren().add(button_remove_after);
        textualView.setButton_remove_after(button_remove_after);
    }
*/


    public static void main(String[] args) {
        launch(args);
    }
}