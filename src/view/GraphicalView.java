package view;

import model.Visitor;

import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

public class GraphicalView implements Visitor{

    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    public GraphicalView() {
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