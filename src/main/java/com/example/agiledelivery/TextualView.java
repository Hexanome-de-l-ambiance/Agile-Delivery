package com.example.agiledelivery;

import com.example.model.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.control.TextArea;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;

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
            "8 a.m.",
            "9 a.m.",
            "10 a.m.",
            "11 a.m."
    );
    private TextArea textArea;
    public TextualView(Carte carte) {
        this.setPrefWidth(Window.textualViewScale * Window.PREFWIDTH);
        this.setPrefHeight(Window.PREFHEIGHT);
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
                hint.setText("");
            } return;
            case Carte.UPDATE: hint.setText(""); return;
            case Carte.REMOVE: {
                Text tmp = textHashMap.get(((Livraison)evt.getNewValue()).getDestination().getId());
                info.getChildren().remove(tmp);
                return;
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
                return;
            }
        }
        messageText = new Text(content);
        messageText.setStyle("-fx-font-size: 12;");
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
        textFlow.getChildren().add(numberCouriersText);
    }

    @Override
    public void display(Tournee tournee)
    {

    }

    protected void showAlert(String alert){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Error Dialog");
        errorAlert.setHeaderText("An error occurred");
        errorAlert.setContentText(alert);
        errorAlert.showAndWait();
    }
}