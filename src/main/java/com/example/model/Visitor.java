package com.example.model;


/**
 * 
 */
public interface Visitor {
    public void display(Carte carte);
    public void display(int numeroCoursier, Tournee tournee);

}