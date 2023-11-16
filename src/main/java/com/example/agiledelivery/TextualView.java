package com.example.agiledelivery;

import com.example.model.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TextualView extends Pane implements PropertyChangeListener, Visitor {

    private final Carte carte;
    private TextFlow info;

    private TextFlow aide;
    private final Text numberCouriersText = new Text("Nombre de coursiers : 1" + "\n");
    private Label textNumeroCoursier;
    HashMap<Integer, Tournee> listeTournees = new HashMap<>();
    private ComboBox<String> comboBoxCouriers;
    private ComboBox<String> comboBoxIntervals;

    private final ObservableList<String> couriers = FXCollections.observableArrayList(
            "1"
    );
    private final ObservableList<String> intervals = FXCollections.observableArrayList(
            "8",
            "9",
            "10",
            "11"
    );

    private final ArrayList<Integer> sizeTournee = new ArrayList<>();
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
    private Button button_generate;
    private Button button_remove;
    private Button button_remove_after;

    private Button button_create_tournee;

    private Button button_nombre_coursier;

    private Label textCreneau;

    public TextualView(Carte carte) {
        this.carte = carte;
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

    public void setButton_add(Button button_add) {
        this.button_add = button_add;
    }

    public void setButton_add_before(Button button_add_before) {
        this.button_add_before = button_add_before;
    }

    public void setButton_add_after(Button button_add_after) {
        this.button_add_after = button_add_after;
    }

    public void setButton_generate(Button button_generate) {
        this.button_generate = button_generate;
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

    public void setButton_create_tournee(Button button_create_tournee) { this.button_create_tournee = button_create_tournee; }

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
                break;
            }
            case Carte.READ: {
                display(carte);
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
                listeTournees = (HashMap<Integer, Tournee>) evt.getNewValue();
                displayListeTournees(listeTournees);
                coordinatesPane.setVisible(false);
                latitudeLabel.setText("");
                longitudeLabel.setText("");
                if(carte.isTourEmpty()){
                    button_add.setManaged(true);
                    button_add.setVisible(true);
                    button_add_before.setManaged(false);
                    button_add_before.setVisible(false);
                    button_add_after.setManaged(false);
                    button_add_after.setVisible(false);
                    button_generate.setManaged(false);
                    button_generate.setVisible(false);
                    textNumeroCoursier.setManaged(true);
                    textNumeroCoursier.setVisible(true);
                    comboBoxCouriers.setManaged(true);
                    comboBoxCouriers.setVisible(true);
                    button_create_tournee.setManaged(true);
                    button_create_tournee.setVisible(true);
                    button_nombre_coursier.setManaged(true);
                    button_nombre_coursier.setVisible(true);
                    textCreneau.setManaged(true);
                    textCreneau.setVisible(true);
                    comboBoxIntervals.setManaged(true);
                    comboBoxIntervals.setVisible(true);
                    textField.setManaged(true);
                    textField.setVisible(true);

                } else {
                    listeTournees = (HashMap<Integer, Tournee>) evt.getNewValue();
                    displayListeTournees(listeTournees);
                    button_add.setManaged(false);
                    button_add.setVisible(false);
                    button_add_before.setManaged(true);
                    button_add_before.setVisible(true);
                    button_add_after.setManaged(true);
                    button_add_after.setVisible(true);
                    button_generate.setManaged(true);
                    button_generate.setVisible(true);
                    textNumeroCoursier.setManaged(false);
                    textNumeroCoursier.setVisible(false);
                    comboBoxCouriers.setManaged(false);
                    comboBoxCouriers.setVisible(false);
                    button_create_tournee.setManaged(false);
                    button_create_tournee.setVisible(false);
                    button_nombre_coursier.setManaged(false);
                    button_nombre_coursier.setVisible(false);
                    button_remove.setManaged(false);
                    button_remove.setVisible(false);
                    button_remove_after.setManaged(true);
                    button_remove_after.setVisible(true);
                    textCreneau.setVisible(false);
                    textCreneau.setManaged(false);
                    comboBoxIntervals.setManaged(false);
                    comboBoxIntervals.setVisible(false);
                    textField.setManaged(false);
                    textField.setVisible(false);

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
                button_add_before.setVisible(false);
                button_add_after.setManaged(false);
                button_add_after.setVisible(false);
                button_generate.setManaged(false);
                button_generate.setVisible(false);
                textNumeroCoursier.setManaged(true);
                textNumeroCoursier.setVisible(true);
                comboBoxCouriers.setManaged(true);
                comboBoxCouriers.setVisible(true);
                button_remove.setManaged(true);
                button_remove.setVisible(true);
                button_remove_after.setManaged(false);
                button_remove_after.setVisible(false);
                button_create_tournee.setManaged(true);
                button_create_tournee.setVisible(true);
                button_nombre_coursier.setManaged(true);
                button_nombre_coursier.setVisible(true);
                textCreneau.setManaged(true);
                textCreneau.setVisible(true);
                comboBoxIntervals.setManaged(true);
                comboBoxIntervals.setVisible(true);
                textField.setManaged(true);
                textField.setVisible(true);
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
                Text segment = new Text("\n"+" Coursier : " + entry.getKey() +"\n");
                segment.setStyle("-fx-font-size: 20px");
                info.getChildren().add(segment);
                display(entry.getKey(), tournee);
            }
            sizeTournee.add(entry.getValue().getListeLivraisons().size());
        }
    }

    @Override
    public void display(Carte carte) {
    }

    @Override
    public void display(int numeroCoursier, Tournee tournee)
    {

        ArrayList<Livraison> list = tournee.getListeLivraisons();

        for(Livraison livraison : list){
            Label newLabel = new Label(" Longitude : " + livraison.getDestination().getLongitude() + " latitude: " + livraison.getDestination().getLatitude() + "\n Heure : " + livraison.getCreneauHoraire()+ "\n");
            newLabel.setOnMouseClicked(event -> {
                this.numeroCoursier = numeroCoursier;
                this.livraison = livraison;
                this.selectedIndex = list.indexOf(livraison);
                if(selectedLabel!= null) selectedLabel.setTextFill(Color.BLACK);
                selectedLabel = newLabel;
                selectedLabel.setTextFill(Color.YELLOW);
            });
            info.getChildren().add(newLabel);
        }
    }

    protected void showAlert(String alert){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Erreur");
        errorAlert.setHeaderText("Une erreur est survenue");
        errorAlert.setContentText(alert);
        errorAlert.showAndWait();
    }

    protected void showWarning(String warning) {
        aide.getChildren().clear();
        Text text_warning = new Text(warning);
        text_warning.setFill(Color.RED);
        aide.getChildren().add(text_warning);
    }

    protected void showInfo(String info) {
        aide.getChildren().clear();
        Text text_info = new Text(info);
        aide.getChildren().add(text_info);
    }

    public void setTextCreneau(Label textCreneau) {
        this.textCreneau = textCreneau;
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

    public void setInfo(TextFlow text){
        this.info = text;
    }

    public void setAide(TextFlow text){
        this.aide = text;
    }

    public void setCoordinatesPane(Pane coordinatesPane) {
        this.coordinatesPane = coordinatesPane;
    }

    public void setCoordinatesPaneVisible(boolean b) {
        this.coordinatesPane.setVisible(b);
    }

    public void setButton_Nombre_coursier(Button changeNumberCouriersButton) {
        this.button_nombre_coursier = changeNumberCouriersButton;
    }
}