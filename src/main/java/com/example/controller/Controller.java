package com.example.controller;

import com.example.agiledelivery.ButtonListener;
import com.example.agiledelivery.GraphicalView;
import com.example.agiledelivery.MouseListener;
import com.example.agiledelivery.TextualView;
import com.example.model.Carte;
import com.example.model.Intersection;
import com.example.model.Livraison;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.time.LocalTime;
/**
 *
 */
public class Controller {


    /**
     * Default constructor
     */
    private Carte carte;
    private Etat etatCourant;
    private Stage stage;
    private ListeDeCommandes listeDeCommandes;
    protected final EtatInitial etatInitial = new EtatInitial();
    protected final EtatCarteChargee etatCarteChargee = new EtatCarteChargee();
    protected final EtatAjoutDestination etatAjoutDestination = new EtatAjoutDestination();
    protected final EtatTourneeCalculee etatTourneeCalculee = new EtatTourneeCalculee();
    protected final EtatDemandeAjoutee etatDemandeAjoutee = new EtatDemandeAjoutee();
    private ButtonListener buttonListener;
    private MouseListener mouseListener;
    private GraphicalView graphicalView;

    private TextualView textualView;


    @FXML
    private MenuItem loadMap;

    @FXML
    private Label latitudeLabel;

    @FXML
    private Label longitudeLabel;

    @FXML
    private Pane mapPane;

    @FXML
    private Button ajouterLivraison;

    @FXML
    private Pane test;

    @FXML
    private ComboBox<String> coursierComboBox;

    @FXML
    private ComboBox<String> creneauComboBox;

    @FXML
    private Pane coordinatesPane;

    @FXML
    private TextFlow info;

    @FXML
    public void initialize() {
        graphicalView = new GraphicalView(carte, mapPane);
        textualView = new TextualView(carte);
        buttonListener = new ButtonListener(this);
        mouseListener = new MouseListener(this, graphicalView, textualView);
        graphicalView.setMouseListener(mouseListener);
        buttonListener.setTextualView(textualView);
        mapPane.getChildren().add(0,graphicalView);

        loadMap.setOnAction(event -> buttonListener.handle(event));
        ajouterLivraison.setOnAction(actionEvent -> buttonListener.handle(actionEvent));
        handleHeightChanged();
        handleWidthChanged();



        setupTextualView();
        System.out.println(longitudeLabel.getText());
    }

    private void setupTextualView(){
        textualView.setCouriersComboBox(coursierComboBox);
        textualView.setCreneauComboBox(creneauComboBox);
        textualView.setCoordinatesPane(coordinatesPane);
        textualView.setLongitudeLabel(longitudeLabel);
        textualView.setLatitudeLabel(latitudeLabel);
        textualView.setInfo(info);
    }

    private void handleHeightChanged(){
        mapPane.heightProperty().addListener((observable, oldValue, newValue) -> {
            double newHeight = (double) newValue;
            double oldHeight = (double) oldValue;
            if (oldHeight == 0.0) {
                oldHeight = newHeight;
            }
            double scaleFactor = (newHeight/oldHeight);
            graphicalView.getGraph().setScaleY(scaleFactor*graphicalView.getGraph().getScaleY());
            graphicalView.getGraph().setTranslateY(10);
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
    private void update(){}

    protected void setEtatCourant(Etat etat){
        etatCourant = etat;
    }
    public Controller(Carte carte, Stage stage) {
        listeDeCommandes = new ListeDeCommandes();
        etatCourant = etatInitial;
        this.stage = stage;
        this.carte = carte;

    }

    public void addDestination(Intersection intersection) {
        etatCourant.addIntersection(this, intersection);
    }


    public void addDelivery(int numeroCoursier, int heure) {
        etatCourant.addDelivery(listeDeCommandes, LocalTime.of(heure,0,0), numeroCoursier, this, carte);
    }

    public void deleteDelivery(int numeroCoursier, Livraison livraison) {
        etatCourant.deleteDelivery(listeDeCommandes, numeroCoursier, livraison, this, carte);
    }

    public void calculateDelivery() {
        etatCourant.calculerLivraisons(this, carte);
    }
    public void modiferCoursiers(int nombre) {
        etatCourant.modiferCoursiers(this, carte, nombre);
        listeDeCommandes.reset();
    }
    public void undo() {
        etatCourant.undo(listeDeCommandes);
    }

    public void redo() {
        etatCourant.redo(listeDeCommandes);
    }

    public void load() {
        etatCourant.loadMap(this, carte, listeDeCommandes, stage);
    }

    public void mouseMoved(Intersection intersection) {
        etatCourant.mouseMoved(this, carte, intersection);
    }
}
