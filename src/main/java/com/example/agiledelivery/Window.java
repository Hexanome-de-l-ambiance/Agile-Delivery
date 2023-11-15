package com.example.agiledelivery;

import com.example.controller.Controller;
import com.example.model.Carte;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Window extends Application {
    protected static final String LOAD_PLAN = "loadMapButton";
    protected static final String ADD_DESTINATION = "ajouterLivraisonButton";
    protected static final String CALCULATE_TOUR = "calculerTourneeButton";
    protected static final String UNDO = "undoButton";
    protected static final String REDO = "redoButton";
    protected static final String LOAD_TOUR = "chargerTourneeButton";
    protected static final String SAVE_TOUR = "sauvegarderTourneeButton";
    protected static final String RESET = "resetTourneeButton";
    protected static final String GENERATE = "genererFeuilleDeRouteButton";


    protected static final String RESET_NB_COURIERS = "changeNumberCouriersButton";
    protected static final String REMOVE = "supprimerLivraisonButton";
    protected static final String ADD_DESTINATION_BEFORE = "ajouterAvantButton";
    protected static final String ADD_DESTINATION_AFTER = "ajouterApresButton";
    protected static final String REMOVE_AFTER_CALCULATED = "supprimerApresTourneeButton";



    @Override
    public void start(Stage primaryStage) throws Exception {
        Carte carte = new Carte(1);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view.fxml"));
        ViewController controller = new ViewController(carte, new Controller(carte,primaryStage));
        loader.setController(controller);
        Scene scene = loader.load();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}