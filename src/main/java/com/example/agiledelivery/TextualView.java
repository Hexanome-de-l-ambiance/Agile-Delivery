package com.example.agiledelivery;

import com.example.model.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

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
    private Label textNumeroCoursier;
    private String content = "Welcome!";
    private HashMap<Long, Text> textHashMap = new HashMap<>();
    HashMap<Integer, Tournee> listeTournees = new HashMap<>();
    private ComboBox<String> comboBoxCouriers;
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

    private ArrayList<Integer> sizeTournee = new ArrayList<>();
    private TextField textField;
    private int numeroCoursier = -1;
    private Livraison livraison = null;
    private Label selectedLabel = null;
    private int selectedIndex = -1;
    private boolean isCalculated = false;


    private Label longitudeLabel;
    private Label latitudeLabel;
    private Pane coordinatesPane;
    private Button button_add;
    private Button button_add_before;
    private Button button_add_after;
    private Button button_remove;
    private Button button_remove_after;

    private Button resetTourneeButton;

    public TextualView(Carte carte) {
        this.carte = carte;

        this.setStyle("-fx-background-color: red;");
        messageText = new Text(content);
        messageText.setStyle("-fx-font-size: 12;");
        numberCouriersText.setStyle("-fx-font-size: 12;");
        textFlow.setPrefWidth(this.getPrefWidth());
        textFlow.setLayoutX(0);
        textFlow.setLayoutY((this.getPrefHeight() - textFlow.prefHeight(-1)) / 5*3);
        textFlow.getChildren().add(messageText);
        info.setPrefWidth(this.getPrefWidth());
        info.setLayoutX(0);
        info.setLayoutY((this.getPrefHeight() - info.prefHeight(-1)) / 5*4);


        hintFlow.setPrefWidth(this.getPrefWidth());
        hintFlow.setLayoutX(0);
        hintFlow.setLayoutY((this.getPrefHeight() - textFlow.prefHeight(-1)) / 12*11);
        hintFlow.getChildren().add(hint);
        this.getChildren().add(textFlow);
        this.getChildren().add(info);
        this.getChildren().add(hintFlow);
        carte.addPropertyChangeListener(this);
    }


    public void setCouriersComboBox(ComboBox<String> comboBox){
        this.comboBoxCouriers = comboBox;
        this.comboBoxCouriers.setItems(couriers);
    }

    public void setCreneauComboBox(ComboBox<String> comboBox){
        this.comboBoxIntervals = comboBox;
        this.comboBoxIntervals.setItems(intervals);
    }
    public void setHint(String s){
        hint.setText(s);
    }


    public void setButton_add(Button button_add) {
        this.button_add = button_add;
    }

    public void setButton_add_before(Button button_add_before) {
        this.button_add_before = button_add_before;
    }

    public void setButton_add_after(Button button_add_after) {
        this.button_add_after = button_add_after;
    }

    public void setButton_remove(Button button_remove) {
        this.button_remove = button_remove;
    }

    public void setButton_remove_after(Button button_remove_after) {
        this.button_remove_after = button_remove_after;
    }

    public void setTextNumeroCoursier(Label textNumeroCoursier) {
        this.textNumeroCoursier = textNumeroCoursier;
    }

    public void setTextField(TextField textField) {
        this.textField = textField;
    }

    public int getNumeroCoursier() {
        return numeroCoursier;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public Livraison getLivraison() {
        return livraison;
    }

    public ComboBox<String> getComboBoxCouriers() {
        return comboBoxCouriers;
    }

    public ComboBox<String> getComboBoxIntervals() {
        return comboBoxIntervals;
    }

    public boolean isCalculated() {
        return isCalculated;
    }

    public TextField getTextField() {
        return textField;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String event = evt.getPropertyName();
        System.out.println(event);
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
                listeTournees = (HashMap<Integer, Tournee>) evt.getNewValue();
                displayListeTournees(listeTournees);
                coordinatesPane.setVisible(false);
                latitudeLabel.setText("");
                longitudeLabel.setText("");
                break;
            }
            case Carte.UPDATE: {
                coordinatesPane.setVisible(false);
                latitudeLabel.setText("");
                longitudeLabel.setText("");
                if(carte.isTourEmpty()){
                    button_add.setManaged(true);
                    button_add.setVisible(true);
                    button_add_before.setManaged(false);
                    button_add_before.setDisable(true);
                    button_add_before.setVisible(false);
                    button_add_after.setManaged(false);
                    button_add_after.setDisable(true);
                    button_add_after.setVisible(false);
                    textNumeroCoursier.setManaged(true);
                    textNumeroCoursier.setVisible(true);
                    comboBoxCouriers.setManaged(true);
                    comboBoxCouriers.setVisible(true);

                } else {
                    button_add.setManaged(false);
                    button_add.setVisible(false);
                    button_add_before.setManaged(true);
                    button_add_before.setDisable(false);
                    button_add_before.setVisible(true);
                    button_add_after.setManaged(true);
                    button_add_after.setDisable(false);
                    button_add_after.setVisible(true);
                    textNumeroCoursier.setManaged(false);
                    textNumeroCoursier.setVisible(false);
                    comboBoxCouriers.setManaged(false);
                    comboBoxCouriers.setVisible(false);


                    button_remove.setManaged(false);
                    button_remove.setDisable(true);
                    button_remove.setVisible(false);
                    button_remove_after.setManaged(true);
                    button_remove_after.setDisable(false);
                    button_remove_after.setVisible(true);
                }
                isCalculated = true;
                break;
            }
            case Carte.REMOVE: {
                listeTournees = (HashMap<Integer, Tournee>) evt.getNewValue();
                displayListeTournees(listeTournees);
                break;
            }
            case Carte.SET_NB_COURIERS:{
                int newNumber = (int) evt.getNewValue();
                couriers.clear();
                for(int i = 1; i <= newNumber; i++){
                    couriers.add(String.valueOf(i));
                }
                comboBoxCouriers.setItems(couriers);
                System.out.println("123");

                numberCouriersText.setText("Nombre de coursiers : " + evt.getNewValue() + "\n");
                info.getChildren().clear();
                break;
            }
            case Carte.RESET_TOURS:{
                couriers.clear();
                couriers.add("1");
                comboBoxCouriers.setItems(couriers);
                numberCouriersText.setText("Nombre de coursiers : " + evt.getNewValue() + "\n");
                info.getChildren().clear();
                button_add.setManaged(true);
                button_add.setVisible(true);
                button_add_before.setManaged(false);
                button_add_before.setDisable(true);
                button_add_before.setVisible(false);
                button_add_after.setManaged(false);
                button_add_after.setDisable(true);
                button_add_after.setVisible(false);
                textNumeroCoursier.setManaged(true);
                textNumeroCoursier.setVisible(true);
                comboBoxCouriers.setManaged(true);
                comboBoxCouriers.setVisible(true);

                button_remove.setManaged(true);
                button_remove.setDisable(false);
                button_remove.setVisible(true);
                button_remove_after.setManaged(false);
                button_remove_after.setDisable(true);
                button_remove_after.setVisible(false);
                isCalculated = false;
                break;
            }
        }

    }

    private void displayListeTournees(HashMap<Integer, Tournee> listeTournees){
        info.getChildren().clear();
        numeroCoursier = -1;
        livraison = null;
        selectedLabel = null;
        for(Map.Entry<Integer, Tournee> entry: listeTournees.entrySet()){
            Tournee tournee = entry.getValue();
            if(tournee.getListeLivraisons().size() > 1 || (tournee.getListeLivraisons().size() == 1 && tournee.getListeLivraisons().get(0).getDestination() != carte.getEntrepot())){
                Text segment = new Text("Id Coursier: " + entry.getKey() + "\n");
                info.getChildren().add(segment);
                display(entry.getKey(), tournee);
            }
            sizeTournee.add(entry.getValue().getListeLivraisons().size());
        }
    }

    @Override
    public void display(Carte carte) {
    /*    Text segment = new Text("Nb segments: " + carte.getListeSegments().size() + "\n");
        segment.setStyle("-fx-font-size: 24;");
        textFlow.getChildren().add(segment);
        Text intersection = new Text("Nb segments: " + carte.getListeIntersections().size() + "\n");
        intersection.setStyle("-fx-font-size: 24;");
        textFlow.getChildren().add(intersection);
        textFlow.getChildren().add(numberCouriersText);
    */}

    @Override
    public void display(int numeroCoursier, Tournee tournee)
    {
        ArrayList<Livraison> list = tournee.getListeLivraisons();
        if(list.size() > 0 && list.get(0).getDestination() == carte.getEntrepot()) list.remove(0);
        for(Livraison livraison : list){
            Label newLabel = new Label(" longitude : " + livraison.getDestination().getLongitude() + " latitude: " + livraison.getDestination().getLatitude() + "\n");
            newLabel.setOnMouseClicked(event -> {
                this.numeroCoursier = numeroCoursier;
                this.livraison = livraison;
                this.selectedIndex = list.indexOf(livraison);
                System.out.println("test");
                if(selectedLabel!= null) selectedLabel.setStyle("-fx-fill: black;");
                selectedLabel = newLabel;
                selectedLabel.setStyle("-fx-fill: yellow;");
            });
            info.getChildren().add(newLabel);
        }
    }

    protected void showAlert(String alert){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Error Dialog");
        errorAlert.setHeaderText("An error occurred");
        errorAlert.setContentText(alert);
        errorAlert.showAndWait();
    }

    public void setLongitudeLabel(Label label){
        this.longitudeLabel = label;
    }
    public void setTextLongitudeLabel(String s) {
        longitudeLabel.setText(s);
    }
    public void setLatitudeLabel(Label label){
        this.latitudeLabel = label;
    }
    public void setTextLatitudeLabel(String s) {
        latitudeLabel.setText(s);
    }

    public void setResetTourneeButton(Button button){
        this.resetTourneeButton = button;
    }

    public void setInfo(TextFlow text){
        this.info = text;
    }

    public void setCoordinatesPane(Pane coordinatesPane) {
        this.coordinatesPane = coordinatesPane;
    }

    public void setCoordinatesPaneVisible(boolean b) {
        this.coordinatesPane.setVisible(b);
    }
}