package com.example.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.util.Pair;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

public class Carte {
    private double minLat;
    private double maxLat;
    private double minLon;
    private double maxLon;
    private PropertyChangeSupport support = new PropertyChangeSupport(this);
    private HashMap<Long, Intersection> listeIntersection;
    private HashMap<Pair<Long, Long>, Segment> listeSegments;
    private HashMap<Long, ArrayList<Pair<Long, Double>>> listeAdjacence;
    private HashMap<Integer, Tournee> listeTournees;

    private Long entrepotId;
    private SimpleIntegerProperty idProperty = new SimpleIntegerProperty(1);
    public static final String RESET = "reset";
    public static final String READ = "read";
    public static final String UPDATE = "update";
    public static final String ERROR = "error";
    public static final String ADD = "add destination";
    public static final String REMOVE = "remove destination";

    public Carte(int nombreCoursier){
        listeIntersection = new HashMap<>();
        listeSegments = new HashMap<>();
        listeTournees = new HashMap<>();
        listeAdjacence = new HashMap<>();
        for(int i = 1; i <= nombreCoursier; i++){
            listeTournees.put(i, new Tournee());
        }
    }
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



    public void addIntersection(Long id, double latitude, double longitude) {
        Intersection newIntersection = new Intersection(id, latitude, longitude);
        listeIntersection.put(id, newIntersection);
    }

    public void addSegment(Long ori, Long dest, double length, String name) {
        Intersection origin = listeIntersection.get(ori);
        Intersection destination = listeIntersection.get(dest);
        Segment newSegment = new Segment(origin, destination, length, name);
        listeSegments.put(new Pair<>(ori, dest), newSegment);
        idProperty.set(idProperty.get() + 1);
    }

    public void setEntrepot(Long id) {
        this.entrepotId = id;
    }
    public Long getEntrepot() { return entrepotId; }

    public HashMap<Long, Intersection> getListeIntersections() {
        return listeIntersection;
    }

    public HashMap<Pair<Long, Long>, Segment> getListeSegments() {
        return listeSegments;
    }
    public HashMap<Long, ArrayList<Pair<Long, Double>>> getListeAdjacence() { return listeAdjacence; }

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

    public SimpleIntegerProperty idProperty() {
        return idProperty;
    }

    public void reset() {
        listeIntersection.clear();
        listeSegments.clear();
        idProperty.set(1);
        firePropertyChange(RESET, null, null);
    }

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
    public void addLivraison (int numeroCouriser, Intersection livraison) {
        listeTournees.get(numeroCouriser).addLivraison(livraison);
        firePropertyChange(ADD, null, livraison);
    }

    public void removeLivraison (int numeroCouriser, Intersection livraison) {
        listeTournees.get(numeroCouriser).removeLivraison(livraison);
        firePropertyChange(REMOVE, null, livraison);
    }
    public void addTournee (int coursier, Tournee tournee) {
        listeTournees.put(coursier, tournee);
        firePropertyChange(UPDATE, null, listeTournees);
    }
    public void calculerTournees() {
        for(Map.Entry<Integer, Tournee> entry: listeTournees.entrySet()){
            entry.getValue().calculerTournee(this);
        }
        firePropertyChange(UPDATE, null, listeTournees);
    }
    public HashMap<Integer, Tournee> getListeTournees() {
        return listeTournees;
    }

    public void sendException(Exception e){
        firePropertyChange(ERROR, null, e.getMessage());
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
    private void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        support.firePropertyChange(propertyName, oldValue, newValue);
    }
}