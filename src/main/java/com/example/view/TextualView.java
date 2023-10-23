package com.example.view;

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
}