package com.example.controller;

import com.example.agiledelivery.ButtonListener;
import com.example.agiledelivery.GraphicalView;
import com.example.agiledelivery.MouseListener;
import com.example.agiledelivery.TextualView;
import com.example.model.Carte;
import com.example.model.Intersection;
import com.example.model.Livraison;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.time.LocalTime;
/**
 *
 */
public class Controller {

    public MenuItem loadMap;
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
    private Pane mapPane;
    @FXML
    public void initialize() {
        graphicalView = new GraphicalView(carte, mapPane);
        textualView = new TextualView(carte);
        buttonListener = new ButtonListener(this);
        mouseListener = new MouseListener(this, graphicalView);
        graphicalView.setMouseListener(mouseListener);
        mapPane.getChildren().add(graphicalView);
        loadMap.setOnAction(event -> buttonListener.handle(event));

        mapPane.widthProperty().addListener((observable, oldValue, newValue) -> {
            double newWidth = (double) newValue;
            double oldWidth = (double) oldValue;
            if (oldWidth == 0.0) {
                oldWidth = newWidth;
            }
            double scaleFactor = (newWidth/oldWidth);

            graphicalView.getGraph().setScaleX(scaleFactor*graphicalView.getGraph().getScaleX());
        });
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
