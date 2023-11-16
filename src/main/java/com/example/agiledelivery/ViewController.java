package com.example.agiledelivery;

import com.example.controller.Controller;
import com.example.model.Carte;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextFlow;

import java.util.Arrays;

import static java.lang.Math.abs;

/**
 *
 */
public class ViewController {

    public ViewController(Carte carte, Controller controller){
        this.carte = carte;
        this.controller = controller;
    }

    /**
     * Default constructor
     */
    private Carte carte;
    private ButtonListener buttonListener;
    private MouseListener mouseListener;
    private GraphicalView graphicalView;

    private Controller controller;
    private TextualView textualView;


    @FXML
    private MenuItem loadMapButton;

    @FXML
    private Label latitudeLabel;

    @FXML
    private Label longitudeLabel;

    @FXML
    private SplitPane mainView;

    @FXML
    private Pane mapPane;

    @FXML
    private Button ajouterLivraisonButton;
    @FXML
    private Button supprimerLivraisonButton;

    @FXML
    private ComboBox<String> coursierComboBox;

    @FXML
    private ComboBox<String> creneauComboBox;

    @FXML
    private Pane coordinatesPane;

    @FXML
    private Button calculerTourneeButton;

    @FXML
    private Button undoButton;

    @FXML
    private Button redoButton;

    @FXML
    private Button resetTourneeButton;

    @FXML
    private Button ajouterAvantButton;

    @FXML
    private Button ajouterApresButton;

    @FXML
    private Button genererFeuilleDeRouteButton;

    @FXML
    private TextFlow info;

    @FXML
    private MenuItem sauvegarderTourneeButton;
    @FXML
    private MenuItem chargerTourneeButton;

    @FXML
    private Button changeNumberCouriersButton;

    @FXML
    private TextField courierNumberTextField;
    @FXML
    private Button supprimerApresTourneeButton;

    @FXML
    private Label textNumeroCoursier;

    @FXML
    private Label textCreneau;


    @FXML
    public void initialize() {
        graphicalView = new GraphicalView(carte, mapPane);
        textualView = new TextualView(carte);
        buttonListener = new ButtonListener(controller);
        mouseListener = new MouseListener(textualView, graphicalView,controller);
        graphicalView.setMouseListener(mouseListener);
        buttonListener.setTextualView(textualView);
        mapPane.getChildren().add(0,graphicalView);

        for (Button button : Arrays.asList(ajouterLivraisonButton, calculerTourneeButton, undoButton, redoButton, supprimerLivraisonButton, resetTourneeButton, changeNumberCouriersButton, supprimerApresTourneeButton, ajouterApresButton, ajouterAvantButton, genererFeuilleDeRouteButton)) {
            button.setOnAction(actionEvent -> buttonListener.handle(actionEvent));
        }
        for (MenuItem menuItem : Arrays.asList(chargerTourneeButton, sauvegarderTourneeButton,loadMapButton)) {
            menuItem.setOnAction(actionEvent -> buttonListener.handle(actionEvent));
        }
        graphicalView.prefWidthProperty().bind(mapPane.widthProperty());
        graphicalView.prefHeightProperty().bind(mapPane.heightProperty());
        graphicalView.setStyle("-fx-background-color: RED");


        //handleHeightChanged();
        //handleWidthChanged();



        setupTextualView();
        System.out.println(longitudeLabel.getText());
    }

    private void setupTextualView(){
        textualView.setCouriersComboBox(coursierComboBox);
        textualView.setCreneauComboBox(creneauComboBox);
        textualView.setCoordinatesPane(coordinatesPane);
        textualView.setLongitudeLabel(longitudeLabel);
        textualView.setLatitudeLabel(latitudeLabel);
        textualView.setButton_add(ajouterLivraisonButton);
        textualView.setButton_add_before(ajouterAvantButton);
        textualView.setButton_add_after(ajouterApresButton);
        textualView.setButton_generate(genererFeuilleDeRouteButton);
        textualView.setButton_remove(supprimerLivraisonButton);
        textualView.setButton_remove_after(supprimerApresTourneeButton);
        textualView.setTextField(courierNumberTextField);
        textualView.setInfo(info);
        textualView.setTextNumeroCoursier(textNumeroCoursier);
        textualView.setButton_create_tournee(calculerTourneeButton);
        textualView.setButton_Nombre_coursier(changeNumberCouriersButton);
        textualView.setTextCreneau(textCreneau);
    }

    private void handleHeightChanged(){
        mainView.heightProperty().addListener((observable, oldValue, newValue) -> {
            double newHeight = (double) newValue;
            double oldHeight = (double) oldValue;

        });
        mapPane.heightProperty().addListener((observable, oldValue, newValue) -> {
            double newHeight = (double) newValue;
            double oldHeight = (double) oldValue;
            if (oldHeight == 0.0) {
                oldHeight = newHeight;
            }
            double scaleFactor = (newHeight/oldHeight);
            graphicalView.getGraph().setScaleY(scaleFactor*graphicalView.getGraph().getScaleY());
            double offsetY = abs((1 - scaleFactor)) * mapPane.getHeight() / 2.0;
            graphicalView.getGraph().setTranslateY(graphicalView.getGraph().getTranslateY() + offsetY);

        });
    }

    private void handleWidthChanged(){
        mapPane.widthProperty().addListener((observable, oldValue, newValue) -> {
            double newWidth = (double) newValue;
            double oldWidth = (double) oldValue;
            if (oldWidth == 0.0) {
                oldWidth = newWidth;
            }
            double scaleFactor = (newWidth/oldWidth);

            graphicalView.getGraph().setScaleX(scaleFactor*graphicalView.getGraph().getScaleX());

        });
    }

}
