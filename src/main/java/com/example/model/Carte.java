package com.example.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.util.Pair;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

/**
 * Représente une carte avec des intersections, des segments et des tournées.
 */
public class Carte {

    /**
     * La latitude minimale de la carte.
     */
    private double minLat;

    /**
     * La latitude maximale de la carte.
     */
    private double maxLat;

    /**
     * La longitude minimale de la carte.
     */
    private double minLon;

    /**
     * La longitude maximale de la carte.
     */
    private double maxLon;

    /**
     * La liste des intersections sur la carte, indexée par leur identifiant.
     */
    private HashMap<Long, Intersection> listeIntersection;

    /**
     * La liste des segments sur la carte, indexée par l'intersection de départ et d'arrivée.
     */
    private HashMap<Pair<Long, Long>, Segment> listeSegments;

    /**
     * La liste d'adjacence représentant les relations entre les intersections,
     * indexée par l'identifiant de l'intersection avec une liste de paires (identifiant, distance).
     */
    private HashMap<Long, ArrayList<Pair<Long, Double>>> listeAdjacence;

    /**
     * La liste des tournées sur la carte, indexée par le numéro du coursier/de la tournée.
     */
    private HashMap<Integer, Tournee> listeTournees;

    /**
     * Le nombre de coursiers.
     */
    private int nbCoursiers;

    /**
     * L'identifiant de l'entrepôt sur la carte.
     */
    private Long entrepotId;

    /**
     * Indique si la tournée est vide.
     */
    private boolean isTourEmpty = true;
    public static final String RESET = "reset";
    public static final String RESET_TOURS = "reset tours";
    public static final String READ = "read";
    public static final String UPDATE = "update";
    public static final String ERROR = "error";
    public static final String ADD = "add destination";
    public static final String REMOVE = "remove destination";
    public static final String SET_NB_COURIERS = "set number of couriers";

    private SimpleIntegerProperty idProperty = new SimpleIntegerProperty(1);
    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    /**
     * Constructeur de la classe Carte.
     *
     * @param nombreCoursier Le nombre initial de coursiers.
     */
    public Carte(int nombreCoursier){
        listeIntersection = new HashMap<>();
        listeSegments = new HashMap<>();
        listeTournees = new HashMap<>();
        listeAdjacence = new HashMap<>();

        this.nbCoursiers = nombreCoursier;
        for(int i = 1; i <= nbCoursiers; i++){
            listeTournees.put(i, new Tournee(i));
        }
    }

    /**
     * Modifie le nombre de coursiers sur la carte.
     *
     * @param nbCoursiers Le nouveau nombre de coursiers.
     */
    public void setNbCoursiers(int nbCoursiers) {
        this.nbCoursiers = nbCoursiers;
        this.listeTournees.clear();
        for(int i = 1; i <= nbCoursiers; i++){
            listeTournees.put(i, new Tournee(i));
        }
        firePropertyChange(SET_NB_COURIERS, null, nbCoursiers);
    }

    /**
     * Initialise la liste d'adjacence entre les intersections.
     */
    public void initAdjacenceList() {
        listeAdjacence.clear();
        for (Map.Entry<Pair<Long, Long>, Segment> entry : listeSegments.entrySet()) {
            Segment segment = entry.getValue();
            Long origin = segment.getOrigin().getId();
            Long destination = segment.getDestination().getId();
            if (listeAdjacence.containsKey(origin)) {
                listeAdjacence.get(origin).add(new Pair<>(destination, segment.getLength()));
            } else {
                ArrayList<Pair<Long, Double>> list = new ArrayList<>();
                list.add(new Pair<>(destination, segment.getLength()));
                listeAdjacence.put(origin, list);
            }
        }
    }

    /**
     * Ajoute une intersection à la carte.
     *
     * @param id       L'identifiant de l'intersection.
     * @param latitude La latitude de l'intersection.
     * @param longitude La longitude de l'intersection.
     */
    public void addIntersection(Long id, double latitude, double longitude) {
        Intersection newIntersection = new Intersection(id, latitude, longitude);
        listeIntersection.put(id, newIntersection);
    }

    /**
     * Ajoute un segment reliant deux intersections sur la carte.
     *
     * @param ori    L'identifiant de l'intersection de départ du segment.
     * @param dest   L'identifiant de l'intersection d'arrivée du segment.
     * @param length La longueur du segment.
     * @param name   Le nom du segment.
     */
    public void addSegment(Long ori, Long dest, double length, String name) {
        Intersection origin = listeIntersection.get(ori);
        Intersection destination = listeIntersection.get(dest);
        Segment newSegment = new Segment(origin, destination, length, name);
        listeSegments.put(new Pair<>(ori, dest), newSegment);
        idProperty.set(idProperty.get() + 1);
    }

    public void setEntrepotId(Long id) {
        this.entrepotId = id;
    }
    public Intersection getEntrepot() { return listeIntersection.get(entrepotId); }
    public HashMap<Long, Intersection> getListeIntersections() {
        return listeIntersection;
    }
    public HashMap<Pair<Long, Long>, Segment> getListeSegments() {
        return listeSegments;
    }
    public Intersection getIntersection(Long id) {
        return listeIntersection.get(id);
    }
    public ArrayList<Pair<Long, Double>> getNeighbors(Long id) {
        return listeAdjacence.get(id);
    }
    public int getId() {
        return idProperty.get();
    }
    public double getMinLat() {
        return minLat;
    }
    public double getMaxLat() {
        return maxLat;
    }
    public double getMinLon() {
        return minLon;
    }
    public double getMaxLon() {
        return maxLon;
    }
    public boolean isTourEmpty() {
        return isTourEmpty;
    }
    public HashMap<Integer, Tournee> getListeTournees() {
        return listeTournees;
    }

    public void reset() {
        listeIntersection.clear();
        listeSegments.clear();
        nbCoursiers = 1;
        for(int i = 1; i <= nbCoursiers; i++){
            listeTournees.put(i, new Tournee(i));
        }
        idProperty.set(1);
        firePropertyChange(RESET, null, null);
    }

    /**
     * Réinitialise toutes les tournées.
     */
    public void resetTournee(){
        listeTournees.clear();
        for(int i = 1; i <= nbCoursiers; i++){
            listeTournees.put(i, new Tournee(i));
        }
        firePropertyChange(RESET_TOURS, null, nbCoursiers);
    }

    /**
     * Analyse les intersections de la carte à la fin de la lecture, pour déterminer les valeurs de latitude et longitude minimales et maximales.
     *
     * @param path Le chemin du fichier.
     */
    public void readEnd(String path){
        minLat = Double.MAX_VALUE;
        maxLat = Double.MIN_VALUE;
        minLon = Double.MAX_VALUE;
        maxLon = Double.MIN_VALUE;

        for (Intersection intersection : listeIntersection.values()) {
            minLat = Math.min(minLat, intersection.getLatitude());
            maxLat = Math.max(maxLat, intersection.getLatitude());
            minLon = Math.min(minLon, intersection.getLongitude());
            maxLon = Math.max(maxLon, intersection.getLongitude());
        }

        firePropertyChange(READ, null, path);
    }

    /**
     * Ajoute une livraison à une tournée avant le calcul des tournées.
     *
     * @param numeroCouriser Le numéro du coursier.
     * @param livraison      La livraison à ajouter.
     */
    public void addLivraison (int numeroCouriser, Livraison livraison) {
        listeTournees.get(numeroCouriser).addLivraison(livraison);
        isTourEmpty = false;
        firePropertyChange(ADD, numeroCouriser, listeTournees);
    }

    /**
     * Ajoute une livraison à une position spécifique dans une tournée, après le calcul des tournées.
     *
     * @param numeroCouriser Le numéro du coursier.
     * @param livraison      La livraison à ajouter.
     * @param index          L'indice où ajouter la livraison.
     */
    public boolean addLivraison(int numeroCouriser, Livraison livraison, int index){
        boolean b = listeTournees.get(numeroCouriser).addLivraison(this, livraison, index);
        isTourEmpty = false;
        firePropertyChange(ADD, numeroCouriser, listeTournees);
        firePropertyChange(UPDATE, null, listeTournees);
        if(!b){
            firePropertyChange(ERROR, null, "livraison index" + index +" ajoutée non valide");
        }
        return b;
    }

    /**
     * Supprime une livraison d'une tournée spécifique avant le calcul des tournées.
     *
     * @param numeroCouriser Le numéro du coursier.
     * @param livraison      La livraison à supprimer.
     */
    public void removeLivraison (int numeroCouriser, Livraison livraison) {
        listeTournees.get(numeroCouriser).removeLivraison(livraison);
        boolean b = true;
        for(Map.Entry<Integer, Tournee> entry: listeTournees.entrySet()){
            if (!entry.getValue().getListeLivraisons().isEmpty()) {
                b = false;
                break;
            }
        }
        isTourEmpty = b;
        firePropertyChange(REMOVE, numeroCouriser, listeTournees);
    }

    /**
     * Supprime une livraison d'une position spécifique dans une tournée, après le calcul des tournées.
     *
     * @param numeroCouriser Le numéro du coursier/tournée.
     * @param index          L'indice de la livraison à supprimer.
     */
    public void removeLivraison (int numeroCouriser, int index) {
        listeTournees.get(numeroCouriser).removeLivraison(this, index);
        boolean b = true;
        for(Map.Entry<Integer, Tournee> entry: listeTournees.entrySet()){
            if (!entry.getValue().getListeLivraisons().isEmpty()) {
                b = false;
                break;
            }
        }
        isTourEmpty = b;
        firePropertyChange(REMOVE, numeroCouriser, listeTournees);
        firePropertyChange(UPDATE, null, listeTournees);

    }

    /**
     * Calcule les tournées pour chaque coursier.
     */
    public void calculerTournees() {
        boolean error = false;
        String s = "Tournée invalide du numero ";
        for(Map.Entry<Integer, Tournee> entry: listeTournees.entrySet()){
            if(!entry.getValue().calculerTournee(this)){
                error = true;
                s += entry.getKey() + " ";
            }
        }
        if(error){
            firePropertyChange(ERROR, null, s);
        }
        firePropertyChange(UPDATE, null, listeTournees);
    }

    /**
     * Envoie une exception à tous les écouteurs en cas d'erreur.
     *
     * @param e L'exception à envoyer.
     */
    public void sendException(Exception e){
        firePropertyChange(ERROR, null, e.getMessage());
    }

    /**
     * Ajoute un écouteur pour détecter les changements de propriété de la carte.
     *
     * @param listener L'écouteur à ajouter.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    /**
     * Notifie tous les écouteurs enregistrés d'un changement de propriété.
     *
     * @param propertyName Le nom de la propriété.
     * @param oldValue     La valeur précédente.
     * @param newValue     La nouvelle valeur.
     */
    private void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        support.firePropertyChange(propertyName, oldValue, newValue);
    }
}