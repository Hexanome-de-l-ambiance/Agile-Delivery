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
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/**
 * La vue textuelle
 */
public class TextualView extends Pane implements PropertyChangeListener, Visitor {

    private final Carte carte;
    private TextFlow info;
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

    private Button button_create_tournee;

    private Button button_nombre_coursier;

    private Label textCreneau;

    private Button undoButton;

    private Button removeTournee;

    private Button redoButton;

    private Label errorLabel;

    private MenuItem sauvegarderTourneeButton;

    private MenuItem chargerTourneeButton;

    /**
     * Initialiser le view
     *
     * @param carte La carte à afficher
     */
    public TextualView(Carte carte) {
        this.carte = carte;
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

    public void setButton_create_tournee(Button button_create_tournee) { this.button_create_tournee = button_create_tournee; }

    public void setButton_sauvegarder_tournee(MenuItem sauvegarderTourneeButton) { this.sauvegarderTourneeButton = sauvegarderTourneeButton; }

    public void setButton_charger_tournee(MenuItem chargerTourneeButton) { this.chargerTourneeButton = chargerTourneeButton; }

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
     * Vérifie si les tournées de la carte ont été calculées.
     *
     * @return Vrai si les tournées de la carte ont été calculées, sinon faux.
     */
    public boolean isCalculated() {
        return carte.isTourneeCalculee();
    }

    /**
     * Récupère le champ de texte associé au nombre des coursiers.
     *
     * @return Le champ de texte associé au nombre des coursiers.
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
        switch (event) {
            case Carte.RESET:
            {
                break;
            }
            case Carte.READ: {
                if(carte.getEntrepot() != null){
                    display(carte);
                    button_add.setDisable(false);
                    button_remove.setDisable(false);
                    button_create_tournee.setDisable(false);
                    button_nombre_coursier.setDisable(false);
                    textField.setDisable(false);
                    comboBoxIntervals.setDisable(false);
                    comboBoxCouriers.setDisable(false);
                    removeTournee.setDisable(false);
                    undoButton.setDisable(false);
                    redoButton.setDisable(false);
                    sauvegarderTourneeButton.setDisable(false);
                    chargerTourneeButton.setDisable(false);
                    showAlert("Vous pouvez ajouter une livraison en cliquant sur une destination, supprimer une livraison en cliquant sur la liste, charger ou sauvegarder les tournées.");
                } else {
                    button_add.setDisable(true);
                    button_remove.setDisable(true);
                    button_create_tournee.setDisable(true);
                    button_nombre_coursier.setDisable(true);
                    textField.setDisable(true);
                    comboBoxIntervals.setDisable(true);
                    comboBoxCouriers.setDisable(true);
                    removeTournee.setDisable(true);
                    undoButton.setDisable(true);
                    redoButton.setDisable(true);
                    sauvegarderTourneeButton.setDisable(true);
                    chargerTourneeButton.setDisable(true);
                    showError("Erreur lors du chargement de la carte. Veuillez charger une carte valide.");
                }
                break;
            }
            case Carte.ERROR: showAlert((String) evt.getNewValue()); break;
            case Carte.ADD: {
                listeTournees = (HashMap<Integer, Tournee>) evt.getNewValue();
                displayListeTournees(listeTournees);
                coordinatesPane.setVisible(false);
                latitudeLabel.setText("");
                longitudeLabel.setText("");
                showAlert("Livraison ajoutée.");
                break;
            }
            case Carte.UPDATE: {
                listeTournees = (HashMap<Integer, Tournee>) evt.getNewValue();
                displayListeTournees(listeTournees);
                coordinatesPane.setVisible(false);
                latitudeLabel.setText("");
                longitudeLabel.setText("");

                button_nombre_coursier.setManaged(false);
                button_nombre_coursier.setVisible(false);
                textField.setManaged(false);
                textField.setVisible(false);
                button_create_tournee.setManaged(false);
                button_create_tournee.setVisible(false);
                button_generate.setManaged(true);
                button_generate.setVisible(true);

                if(carte.isTourEmpty()){
                    button_add.setManaged(true);
                    button_add.setVisible(true);
                    button_add_before.setManaged(false);
                    button_add_before.setVisible(false);
                    button_add_after.setManaged(false);
                    button_add_after.setVisible(false);

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
                    button_add_before.setVisible(true);
                    button_add_after.setManaged(true);
                    button_add_after.setVisible(true);

                    textNumeroCoursier.setManaged(false);
                    textNumeroCoursier.setVisible(false);
                    comboBoxCouriers.setManaged(false);
                    comboBoxCouriers.setVisible(false);
                    button_remove.setManaged(false);
                    button_remove.setVisible(false);
                    button_remove_after.setManaged(true);
                    button_remove_after.setVisible(true);

                }
                showAlert("Nouvelle tournée calculée. Vous pouvez choisir une livraison pour ajouter une nouvelle livraison avant ou après cette livaison, ou supprimer une livraison.");
                break;
            }
            case Carte.REMOVE: {
                listeTournees = (HashMap<Integer, Tournee>) evt.getNewValue();
                displayListeTournees(listeTournees);
                showAlert("Livraison supprimée.");
                break;
            }
            case Carte.SET_NB_COURIERS:{
                int newNumber = (int) evt.getNewValue();
                couriers.clear();
                for(int i = 1; i <= newNumber; i++){
                    couriers.add(String.valueOf(i));
                }
                comboBoxCouriers.setItems(couriers);
                numberCouriersText.setText("Nombre de coursiers : " + evt.getNewValue() + "\n");
                info.getChildren().clear();
                showAlert("Nombre de coursiers modifié : " + newNumber);
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
                textField.setManaged(true);
                textField.setVisible(true);
                showAlert("Tournées réinitialisées. Vous pouvez refaire l'ajout et le calcul des tournées.");
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
                Text segment = new Text("\n"+" Coursier : " + entry.getKey() +"\n");
                segment.setStyle("-fx-font-size: 20px");
                info.getChildren().add(segment);
                display(entry.getKey(), tournee);
                info.getChildren().add(new Text("\n"));
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
        int i = 1;
        for(Livraison livraison : list){
            Label newLabel = new Label();
            if(!isCalculated()){
                newLabel.setText(" " + i++  + " : Longitude : " + Math.round(livraison.getDestination().getLongitude() * 1000.0) / 1000.0 + " Latitude: " + Math.round(livraison.getDestination().getLatitude() * 1000.0) / 1000.0 + "\n" + " Créneau :" + livraison.getCreneauHoraire() + "\n\n");
            } else {
                newLabel.setText(" Longitude : " + Math.round(livraison.getDestination().getLongitude() * 1000.0) / 1000.0 + " Latitude: " + Math.round(livraison.getDestination().getLatitude() * 1000.0) / 1000.0 + "\n" + " Heure :" + livraison.getHeureLivraison() + "\n\n");
            }
            switch (livraison.getEtat()){
                case EN_RETARD:
                    newLabel.setTextFill(Color.ORANGE);
                    break;
                default:
                    newLabel.setTextFill(Color.BLACK);
                    break;
            }
            newLabel.setOnMouseClicked(event -> {
                this.numeroCoursier = numeroCoursier;
                this.livraison = livraison;
                this.selectedIndex = list.indexOf(livraison);
                showAlert("Vous avez choisi une livraison. Vous pouvez supprimer cette livraison en cliquant sur le bouton.");
                if(selectedLabel!= null) selectedLabel.setTextFill(Color.BLACK);
                selectedLabel = newLabel;
                selectedLabel.setTextFill(Color.YELLOW);
            });
            info.getChildren().add(newLabel);
        }
        if(isCalculated()) {
            Label heureFinTournee = new Label();
            heureFinTournee.setText(" Retour à l'entrepot :" + tournee.getHeureFinTournee());
            if (tournee.getHeureFinTournee().isAfter(LocalTime.of(12, 0, 0))) {
                heureFinTournee.setTextFill(Color.RED);
            } else {
                heureFinTournee.setTextFill(Color.BLACK);
            }
            info.getChildren().add(heureFinTournee);
        }
    }

    /**
     * Afficher une alerte avec un message d'erreur spécifié.
     * @param alert Le message d'erreur à afficher
     */
    protected void showAlert(String alert){
        errorLabel.setText(alert);
        errorLabel.setTextFill(Color.BLACK);
    }

    protected void showError(String error) {
        errorLabel.setText(error);
        errorLabel.setTextFill(Color.RED);
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
    public void setCoordinatesPane(Pane coordinatesPane) {
        this.coordinatesPane = coordinatesPane;
    }

    public void setCoordinatesPaneVisible(boolean b) {
        this.coordinatesPane.setVisible(b);
    }

    public void setErrorLabel(Label errorLabel) {
        this.errorLabel = errorLabel;
    }
    public void setButton_Nombre_coursier(Button changeNumberCouriersButton) {
        this.button_nombre_coursier = changeNumberCouriersButton;
    }

    public void setUndoButton(Button undoButton) {
        this.undoButton = undoButton;
    }

    public void setRemoveTournee(Button removeTournnee) {
        this.removeTournee = removeTournnee;
    }

    public void setRedoButton(Button redoButton) {
        this.redoButton = redoButton;
    }
}