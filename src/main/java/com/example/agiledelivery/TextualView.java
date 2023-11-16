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
/**
 * La vue textuelle
 */
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
    private Button button_generate;
    private Button button_remove;
    private Button button_remove_after;

    private Button resetTourneeButton;

    /**
     * Initialiser le view
     *
     * @param carte La carte à afficher
     */
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

    /**
     * Définir la comboBox pour les coursiers.
     *
     * @param comboBox La comboBox à définir
     */
    public void setCouriersComboBox(ComboBox<String> comboBox){
        this.comboBoxCouriers = comboBox;
        this.comboBoxCouriers.setItems(couriers);
    }

    /**
     * Définir la comboBox pour les fenêtres temporelles.
     *
     * @param comboBox La comboBox à définir
     */
    public void setCreneauComboBox(ComboBox<String> comboBox){
        this.comboBoxIntervals = comboBox;
        this.comboBoxIntervals.setItems(intervals);
    }

    /**
     * Définir l'information des intersections.
     *
     * @param s Le texte à définir
     */
    public void setHint(String s){
        hint.setText(s);
    }

    /**
     * Définir le bouton pour ajouter une livraison.
     *
     * @param button_add Le bouton à définir
     */
    public void setButton_add(Button button_add) {
        this.button_add = button_add;
    }

    /**
     * Définir le bouton pour ajouter une livraison avant une livraison sélectionnée.
     *
     * @param button_add_before Le bouton à définir
     */
    public void setButton_add_before(Button button_add_before) {
        this.button_add_before = button_add_before;
    }

    /**
     * Définir le bouton pour ajouter une livraison après une livraison sélectionnée.
     *
     * @param button_add_after Le bouton à définir
     */
    public void setButton_add_after(Button button_add_after) {
        this.button_add_after = button_add_after;
    }

    /**
     * Définir le bouton pour générer les tournées.
     *
     * @param button_generate Le bouton à définir
     */
    public void setButton_generate(Button button_generate) {
        this.button_generate = button_generate;
    }

    /**
     * Définir le bouton pour supprimer une livraison.
     *
     * @param button_remove Le bouton à définir
     */
    public void setButton_remove(Button button_remove) {
        this.button_remove = button_remove;
    }

    /**
     * Définir le bouton pour supprimer une livraison après une livraison sélectionnée.
     *
     * @param button_remove_after Le bouton à définir
     */
    public void setButton_remove_after(Button button_remove_after) {
        this.button_remove_after = button_remove_after;
    }

    /**
     * Définir le texte pour le numéro de coursier.
     *
     * @param textNumeroCoursier Le texte à définir
     */
    public void setTextNumeroCoursier(Label textNumeroCoursier) {
        this.textNumeroCoursier = textNumeroCoursier;
    }

    /**
     * Définir le textField pour le numéro de coursier.
     *
     * @param textField Le textField à définir
     */
    public void setTextField(TextField textField) {
        this.textField = textField;
    }

    /**
     * Récupérer le numéro de coursier sélectionné.
     *
     * @return Le numéro de coursier sélectionné
     */
    public int getNumeroCoursier() {
        return numeroCoursier;
    }

    /**
     * Récupérer l'index de livraison sélectionné.
     *
     * @return L'index de la livraison sélectionnée
     */
    public int getSelectedIndex() {
        return selectedIndex;
    }


    /**
     * Récupérer la livraison sélectionnée.
     *
     * @return La livraison sélectionnée
     */
    public Livraison getLivraison() {
        return livraison;
    }

    /**
     * Récupérer la liste des coursiers.
     *
     * @return La liste des coursiers
     */
    public ComboBox<String> getComboBoxCouriers() {
        return comboBoxCouriers;
    }

    /**
     * Récupérer la liste des fenêtres temporelles.
     *
     * @return La liste des fenêtres temporelles
     */
    public ComboBox<String> getComboBoxIntervals() {
        return comboBoxIntervals;
    }

    /**
     * Récupérer le bouton pour ajouter une livraison.
     *
     * @return Le bouton pour ajouter une livraison
     */
    public boolean isCalculated() {
        return isCalculated;
    }

    /**
     * Récupérer le bouton pour ajouter une livraison.
     *
     * @return Le bouton pour ajouter une livraison
     */
    public TextField getTextField() {
        return textField;
    }

    /**
     * Gérer les changements dans les propriétés de l'événement de PropertyChange en effectuant différentes actions basées sur l'événement.
     * @param evt L'événement de changement de propriété
     */
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
                listeTournees = (HashMap<Integer, Tournee>) evt.getNewValue();
                displayListeTournees(listeTournees);
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
                    button_generate.setManaged(false);
                    button_generate.setDisable(true);
                    button_generate.setVisible(false);
                    textNumeroCoursier.setManaged(true);
                    textNumeroCoursier.setVisible(true);
                    comboBoxCouriers.setManaged(true);
                    comboBoxCouriers.setVisible(true);

                } else {
                    listeTournees = (HashMap<Integer, Tournee>) evt.getNewValue();
                    displayListeTournees(listeTournees);
                    button_add.setManaged(false);
                    button_add.setVisible(false);
                    button_add_before.setManaged(true);
                    button_add_before.setDisable(false);
                    button_add_before.setVisible(true);
                    button_add_after.setManaged(true);
                    button_add_after.setDisable(false);
                    button_add_after.setVisible(true);
                    button_generate.setManaged(true);
                    button_generate.setDisable(false);
                    button_generate.setVisible(true);
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
                button_generate.setManaged(false);
                button_generate.setDisable(true);
                button_generate.setVisible(false);
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

    /**
     * Afficher la liste des tournées sur le view en fonction des tournées fournies.
     * @param listeTournees La liste des tournées à afficher
     */
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
    }

    /**
     * Afficher une tournée spécifique pour un numéro de coursier donné.
     *
     * @param numeroCoursier Le numéro du coursier
     * @param tournee        La tournée à afficher
     */
    @Override
    public void display(int numeroCoursier, Tournee tournee)
    {

        ArrayList<Livraison> list = tournee.getListeLivraisons();

        for(Livraison livraison : list){
            Label newLabel = new Label(" longitude : " + livraison.getDestination().getLongitude() + " latitude: " + livraison.getDestination().getLatitude() + " heure : " + livraison.getHeureLivraison()+ "\n");
            newLabel.setOnMouseClicked(event -> {
                this.numeroCoursier = numeroCoursier;
                this.livraison = livraison;
                this.selectedIndex = list.indexOf(livraison);
                System.out.println("test");
                if(selectedLabel!= null) selectedLabel.setTextFill(Color.BLACK);
                selectedLabel = newLabel;
                selectedLabel.setTextFill(Color.YELLOW);
            });
            info.getChildren().add(newLabel);
        }
    }

    /**
     * Afficher une alerte avec un message d'erreur spécifié.
     * @param alert Le message d'erreur à afficher
     */
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