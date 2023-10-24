package com.example.agiledelivery;

import com.example.controller.Controller;
import com.example.model.Carte;
import com.example.model.Intersection;
import com.example.model.Segment;
import com.example.xml.XMLOpener;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.util.ArrayList;


public class Window extends Application {
    private Pane leftPane;
    private GraphicalView rightPane;
    private Button loadMapButton;
    private ButtonListener buttonListener;
    private final int PREFWIDTH = 1600;
    private final int PREFHEIGHT = 800;
    private final int buttonHeight = 100;

    protected static final String LOAD_PLAN = "Charger un plan";
    private final String[] buttonTexts = new String[]{LOAD_PLAN};
    private ArrayList<Button> buttons;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Carte carte = new Carte();
        Controller controller = new Controller(carte, primaryStage);

        leftPane = new Pane();
        rightPane = new GraphicalView(carte);

        initializeButtons(controller);

        HBox mainLayout = new HBox();
        mainLayout.getChildren().addAll(leftPane, rightPane);

        leftPane.setPrefWidth(0.2 * PREFWIDTH);
        rightPane.setPrefWidth(0.8 * PREFWIDTH);

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
            leftPane.getChildren().add(button); // Add the button to the left pane
        }
    }


    public static void main(String[] args) {
        launch(args);

    }
}