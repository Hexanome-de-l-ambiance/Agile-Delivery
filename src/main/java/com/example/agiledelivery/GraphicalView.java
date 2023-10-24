package com.example.view;

import com.example.model.Visitor;
import javafx.scene.layout.Pane;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

public class GraphicalView extends Pane implements PropertyChangeListener, Visitor{

    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    public GraphicalView() {
    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}