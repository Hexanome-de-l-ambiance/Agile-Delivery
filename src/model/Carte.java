package model;

import java.beans.PropertyChangeSupport;
import java.util.*;

/**
 * 
 */
public class Carte extends PropertyChangeSupport {

    /**
     * Default constructor
     */
    public Carte() {
        super(new Object());
    }

    public void someMethodThatUpdatesCarte(Object newValue) {
        // Perform your logic
        firePropertyChange("update", null, newValue);
    }
}