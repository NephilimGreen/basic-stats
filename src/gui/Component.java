package gui;

import java.awt.*;
import java.util.EnumMap;

import gui.Composite.Element;

public interface Component
{
    /**
     * Creates a JComponent that will realize this Component's represented portion of the model.
     * This is separate from the constructor for extensibility.
     * 
     * @return The created JComponent.
     */
    public Container make();

    /**
     * Updates this Component to represent the current status of the BasicStatsModel.
     */
    public void update();

    /**
     * Returns atomic Containers and Components in this Component
     * @return Enumerated map of names:Objects
     * @throws IllegalAccessException if the BasicStatsModel's testingMode == false
     */
    public EnumMap<Element, Object> getElements() throws IllegalAccessException;
}
