package com.example.agiledelivery;

import com.example.model.Carte;
import com.example.model.Intersection;
import com.example.model.Segment;
import com.example.model.Visitor;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class TextualView implements Visitor {

    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    /**
     * Default constructor
     */
    public TextualView() {
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    public void updateView(Object newValue) {
        support.firePropertyChange("update", null, newValue);
    }


    @Override
    public void display(Carte carte) {

    }
}