package com.example.agiledelivery;

import com.example.controller.Controller;
import com.example.model.Carte;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextFlow;

import java.util.Arrays;

/**
 * Le controleur des views
 */
public class ViewController {

    /**
     * Constructeur de ViewController.
     *
     * @param carte      La carte.
     * @param controller Le contrôleur.
     */
    public ViewController(Carte carte, Controller controller){
        this.carte = carte;
        this.controller = controller;
    }


    private Carte carte;
    private ButtonListener buttonListener;
    private MouseListener mouseListener;
    private GraphicalView graphicalView;

    private final Controller controller;
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
    private TextFlow aide;

    @FXML
    private Label textCreneau;

    @FXML
    private ScrollPane scrollPane;

    /**
     * Initialiser les views
     */
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
        graphicalView.prefHeightProperty().bind(mapPane.heightProperty());
        graphicalView.getGraph().prefHeightProperty().bind(graphicalView.heightProperty().add(-10));
        graphicalView.prefWidthProperty().bind(mapPane.widthProperty());
        graphicalView.getGraph().prefWidthProperty().bind(graphicalView.widthProperty().add(-10));
        handleHeightChanged();
        handleWidthChanged();
        setupTextualView();
        System.out.println(longitudeLabel.getText());
    }
    /**
     * Faire les setups pour les membres du textualView
     */
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
        textualView.setAide(aide);
        textualView.setTextNumeroCoursier(textNumeroCoursier);
        textualView.setButton_create_tournee(calculerTourneeButton);
        textualView.setButton_Nombre_coursier(changeNumberCouriersButton);
        textualView.setTextCreneau(textCreneau);
        textualView.setRemoveTournee(resetTourneeButton);
        textualView.setRedoButton(redoButton);
        textualView.setUndoButton(undoButton);
    }
    /**
     * Gérer les changements de la propriété de hauteur (height) de mainView en ajustant la vue graphique en conséquence.
     */
    private void handleHeightChanged() {
        mainView.heightProperty().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                if (oldValue.longValue() != 0.0) scrollPane.setPrefHeight(mapPane.getHeight());
            });
        });
    }
    /**
     * Gérer les changements de la propriété de largeur (width) de mainView en ajustant la vue graphique en conséquence.
     */
    private void handleWidthChanged(){
        mainView.widthProperty().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                if (oldValue.longValue() != 0.0) {
                    coordinatesPane.setLayoutX(mapPane.getWidth() / 2 - coordinatesPane.getWidth() / 2);
                    calculerTourneeButton.setLayoutX(mainView.getWidth() / 3 - calculerTourneeButton.getWidth() / 2);
                    resetTourneeButton.setLayoutX(2 * mainView.getWidth() / 3 - resetTourneeButton.getWidth() / 2);
                    genererFeuilleDeRouteButton.setLayoutX(mainView.getWidth() / 3 - calculerTourneeButton.getWidth() / 2);
                }
            });
        });
    }

}
