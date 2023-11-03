package com.example.agiledelivery;

import com.example.model.*;

import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;

public class TextualView extends Pane implements PropertyChangeListener, Visitor {

    private Carte carte;
    private TextFlow textFlow = new TextFlow();
    private TextFlow info = new TextFlow();
    private TextFlow hintFlow = new TextFlow();
    private Text messageText;
    private Text hint = new Text();
    private String content = "Welcome!";
    private HashMap<Long, Text> textHashMap = new HashMap<>();
    public TextualView(Carte carte) {
        this.setPrefWidth(Window.textualViewScale * Window.PREFWIDTH);
        this.setPrefHeight(Window.PREFHEIGHT);
        this.carte = carte;

        this.setStyle("-fx-background-color: red;");
        messageText = new Text(content);
        messageText.setStyle("-fx-font-size: 24;");
        textFlow.setPrefWidth(this.getPrefWidth());
        textFlow.setLayoutX(0);
        textFlow.setLayoutY((this.getPrefHeight() - textFlow.prefHeight(-1)) / 6*3);
        textFlow.getChildren().add(messageText);
        info.setPrefWidth(this.getPrefWidth());
        info.setLayoutX(0);
        info.setLayoutY((this.getPrefHeight() - info.prefHeight(-1)) / 6*4);


        hintFlow.setPrefWidth(this.getPrefWidth());
        hintFlow.setLayoutX(0);
        hintFlow.setLayoutY((this.getPrefHeight() - textFlow.prefHeight(-1)) / 6*5);
        hintFlow.getChildren().add(hint);
        this.getChildren().add(textFlow);
        this.getChildren().add(info);
        this.getChildren().add(hintFlow);
        carte.addPropertyChangeListener(this);
    }

    protected void setHint(String s){
        hint.setText(s);
    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String event = evt.getPropertyName();
        switch (event) {
            case Carte.RESET: textFlow.getChildren().clear(); content = "Reset"; break;
            case Carte.READ: {
                textFlow.getChildren().clear();
                Text path = new Text((String) evt.getNewValue() + "\n");
                path.setStyle("-fx-font-size: 24;");
                textFlow.getChildren().add(path);
                display(carte);
                content = "Load success";
                break;
            }
            case Carte.ERROR: showAlert((String) evt.getNewValue()); return;
            case Carte.ADD: {
                Livraison livraison = (Livraison) evt.getNewValue();
                Text tmp = new Text(("Intersection id: "+livraison.getDestination().getId() + " longitude: " + livraison.getDestination().getLongitude()+ " latitude: " + livraison.getDestination().getLatitude()+"\n"));
                textHashMap.put(livraison.getDestination().getId(), tmp);
                info.getChildren().add(tmp);
            } return;
            case Carte.UPDATE: hint.setText("");return;
            case Carte.REMOVE: {
                Text tmp = textHashMap.get(((Livraison)evt.getNewValue()).getDestination().getId());
                info.getChildren().remove(tmp);
                return;
            }
        }
        messageText = new Text(content);
        messageText.setStyle("-fx-font-size: 24;");
        textFlow.getChildren().add(messageText);
    }

    @Override
    public void display(Carte carte) {
        Text segment = new Text("Nb segments: " + carte.getListeSegments().size() + "\n");
        segment.setStyle("-fx-font-size: 24;");
        textFlow.getChildren().add(segment);
        Text intersection = new Text("Nb segments: " + carte.getListeIntersections().size() + "\n");
        intersection.setStyle("-fx-font-size: 24;");
        textFlow.getChildren().add(intersection);
    }

    @Override
    public void display(Tournee tournee)
    {

    }

    private void showAlert(String alert){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Error Dialog");
        errorAlert.setHeaderText("An error occurred");
        errorAlert.setContentText(alert);
        errorAlert.showAndWait();
    }
}