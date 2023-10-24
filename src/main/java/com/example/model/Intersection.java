package model;

import java.util.*;

/**
 * 
 */
public class Intersection {

    /**
     * Default constructor
     */
    private Long id;
    private double latitude;
    private double longitude;

    public Intersection() {
    }


    public Intersection(Long id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
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