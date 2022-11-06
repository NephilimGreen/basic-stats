package gui;

import java.awt.*;

import model.BasicStatsModel;

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
     *
     * @param model The current BasicStatsModel to be worked in.
     */
    public void update(BasicStatsModel model);
}
