package gui.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.JTextField;

import model.BasicStatsModel;

/**
 * The Numbers View is a visualization of the number of inputs the user has entered.
 */
public class Count implements View
{
    private JPanel panel;
    private JLabel label;
    private JTextField jtfCount;

    /**
     * Creates a new JPanel, loads it with a JLabel containing "Numbers:", then a read-only JTextField.
     * 
     * @return The created JPanel.
     */
    @Override
    public Container make()
    {
        panel = new JPanel();
        label = new JLabel("Numbers:");
        jtfCount = new JTextField(5);
	    jtfCount.setEditable(false);
        panel.add(label);
        panel.add(jtfCount);
        return panel;
    }

    /**
     * Updates the JTextField to represent the number of values in the model.
     *
     * @param model The current BasicStatsModel to be visualized
     */
    @Override
    public void update(BasicStatsModel model)
    {
        jtfCount.setText("" + model.getArrayDouble().length);
    }
}
