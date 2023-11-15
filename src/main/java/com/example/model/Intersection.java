package com.example.model;


import java.util.Objects;

/**
 * 
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


    public Intersection(Long id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

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