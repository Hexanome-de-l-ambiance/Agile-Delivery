package com.example.agiledelivery;

import com.example.model.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.control.TextArea;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TextualView extends Pane implements PropertyChangeListener, Visitor {

    private Carte carte;
    private TextFlow textFlow = new TextFlow();
    private TextFlow info = new TextFlow();
    private TextFlow hintFlow = new TextFlow();
    private Text messageText;
    private Text numberCouriersText = new Text("Nombre de coursiers : 1" + "\n");
    private Text hint = new Text();
    private String content = "Welcome!";
    private HashMap<Long, Text> textHashMap = new HashMap<>();
    private ComboBox<String> comboBox;
    private ComboBox<String> comboBoxIntervals;
    private ObservableList<String> couriers = FXCollections.observableArrayList(
            "1"
    );
    private ObservableList<String> intervals = FXCollections.observableArrayList(
            "8",
            "9",
            "10",
            "11"
    );


    private TextArea textArea;

    private int numeroCoursier = -1;
    private Livraison livraison = null;
    private Text selectedText = null;
    private Rectangle border;

    public TextualView(Carte carte) {
        this.carte = carte;

        this.setStyle("-fx-background-color: red;");
        messageText = new Text(content);
        messageText.setStyle("-fx-font-size: 12;");
        numberCouriersText.setStyle("-fx-font-size: 12;");
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

    public void setHint(String s){
        hint.setText(s);
    }

    public void setComboBox(ComboBox<String> comboBox) {
        this.comboBox = comboBox;
        this.comboBox.setItems(couriers);
    }

    public void setComboBoxIntervals(ComboBox<String> comboBoxIntervals) {
        this.comboBoxIntervals = comboBoxIntervals;
        this.comboBoxIntervals.setItems(intervals);
    }

    public void setTextArea(TextArea textArea) {
        this.textArea = textArea;
        this.textArea.setText("Veuillez saisir un nombre entier positif");
    }

    public int getNumeroCoursier() {
        return numeroCoursier;
    }

    public Livraison getLivraison() {
        return livraison;
    }

    public ComboBox<String> getComboBox() {
        return comboBox;
    }

    public ComboBox<String> getComboBoxIntervals() {
        return comboBoxIntervals;
    }

    public TextArea getTextArea() {
        return textArea;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String event = evt.getPropertyName();
        switch (event) {
            case Carte.RESET:
            {
                textFlow.getChildren().clear();
                content = "Reset";
                messageText = new Text(content);
                messageText.setStyle("-fx-font-size: 12;");
                textFlow.getChildren().add(messageText);
                break;
            }
            case Carte.READ: {
                textFlow.getChildren().clear();
                Text path = new Text((String) evt.getNewValue() + "\n");
                path.setStyle("-fx-font-size: 24;");
                textFlow.getChildren().add(path);
                display(carte);
                content = "Load success";
                messageText = new Text(content);
                messageText.setStyle("-fx-font-size: 12;");
                textFlow.getChildren().add(messageText);
                break;
            }
            case Carte.ERROR: showAlert((String) evt.getNewValue()); break;
            case Carte.ADD: {
                HashMap<Integer, Tournee> listeTournees = (HashMap<Integer, Tournee>) evt.getNewValue();
                displayListeTournees(listeTournees);
                hint.setText("");
                break;
            }
            case Carte.UPDATE: hint.setText(""); break;
            case Carte.REMOVE: {
                HashMap<Integer, Tournee> listeTournees = (HashMap<Integer, Tournee>) evt.getNewValue();
                displayListeTournees(listeTournees);
                break;
            }
            case Carte.SET_NB_COURIERS:{
                int newNumber = (int) evt.getNewValue();
                couriers.clear();
                for(int i = 1; i <= newNumber; i++){
                    couriers.add(String.valueOf(i));
                }
                comboBox.setItems(couriers);
                numberCouriersText.setText("Nombre de coursiers : " + evt.getNewValue() + "\n");
                info.getChildren().clear();
                textHashMap.clear();
                break;
            }
        }

    }

    private void displayListeTournees(HashMap<Integer, Tournee> listeTournees){
        info.getChildren().clear();
        numeroCoursier = -1;
        livraison = null;
        selectedText = null;
        for(Map.Entry<Integer, Tournee> entry: listeTournees.entrySet()){
            Tournee tournee = entry.getValue();
            if(tournee.getLivraisons().size() > 1 || (tournee.getLivraisons().size() == 1 && tournee.getLivraisons().get(0).getDestination() != carte.getEntrepot())){
                Text segment = new Text("Id Coursier: " + entry.getKey() + "\n");
                info.getChildren().add(segment);
                display(entry.getKey(), tournee);
            }
        }
    }

    @Override
    public void display(Carte carte) {
        Text segment = new Text("Nb segments: " + carte.getListeSegments().size() + "\n");
        segment.setStyle("-fx-font-size: 24;");
        textFlow.getChildren().add(segment);
        Text intersection = new Text("Nb segments: " + carte.getListeIntersections().size() + "\n");
        intersection.setStyle("-fx-font-size: 24;");
        textFlow.getChildren().add(intersection);
        textFlow.getChildren().add(numberCouriersText);
    }

    @Override
    public void display(int numeroCoursier, Tournee tournee)
    {
        ArrayList<Livraison> list = tournee.getLivraisons();
        if(list.size() > 0 && list.get(0).getDestination() == carte.getEntrepot()) list.remove(0);
        for(Livraison livraison : list){
            Text newtext = new Text("Id : " + livraison.getDestination().getId() + " longitude : " + livraison.getDestination().getLongitude() + " latitude: " + livraison.getDestination().getLatitude() + "\n");
            newtext.setOnMouseClicked(event -> {
                this.numeroCoursier = numeroCoursier;
                this.livraison = livraison;
                if(selectedText != null) selectedText.setStyle("-fx-fill: black;");
                selectedText = newtext;
                selectedText.setStyle("-fx-fill: yellow;");
            });
            info.getChildren().add(newtext);
        }
    }

    protected void showAlert(String alert){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Error Dialog");
        errorAlert.setHeaderText("An error occurred");
        errorAlert.setContentText(alert);
        errorAlert.showAndWait();
    }
}