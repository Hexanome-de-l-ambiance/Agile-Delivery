package com.example.model;


import java.util.Objects;

/**
 * Représente une intersection sur la carte.
 */
public class Intersection {

    /**
     * L'id de l'intersection
     */
    private Long id;

    /**
     * La latitude de l'intersection
     */
    private double latitude;

    /**
     * La longitude de l'intersection
     */
    private double longitude;

    /**
     * Default constructor
     */
    public Intersection() {
    }

    /**
     * Constructeur avec les paramètres d'identification et de position.
     *
     * @param id        L'ID de l'intersection.
     * @param latitude  La latitude de l'intersection.
     * @param longitude La longitude de l'intersection.
     */
    public Intersection(Long id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Vérifie l'égalité de cette intersection avec un autre objet.
     *
     * @param obj L'objet à comparer.
     * @return True si les objets sont égaux, sinon false.
     */
    @Override
    public boolean equals(Object obj) {
    	if (obj == null) return false;
    	if (obj == this) return true;
    	if (!(obj instanceof Intersection i)) return false;
        return Objects.equals(this.id, i.id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

}