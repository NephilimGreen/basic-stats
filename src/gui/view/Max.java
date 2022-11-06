package gui.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.JTextField;

import gui.BasicStats;
import model.BasicStatsModel;

/**
 * The Numbers View is a visualization of the number of inputs the user has entered.
 */
public class Max implements View
{
    private JPanel panel;
    private JLabel label;
    private JTextField jtfMax;

    /**
     * Creates a new JPanel, loads it with a JLabel containing "Max:", then a read-only JTextField.
     * 
     * @return The created JPanel.
     */
    @Override
    public Container make()
    {
        panel = new JPanel();
        label = new JLabel("Max:");
        jtfMax = new JTextField(5);
	    jtfMax.setEditable(false);
        panel.add(label);
        panel.add(jtfMax);
        return panel;
    }

    /**
     * Updates this View to represent the maximum value in the model.
     *
     * @param model The current BasicStatsModel to be visualized
     */
    @Override
    public void update(BasicStatsModel model)
    {
        if(model.getArrayDouble().length == 0) {jtfMax.setText("");}
        else
        {
            double num = 0.0;
            if(model.getArrayDouble().length > 0) {num = BasicStats.max(model.getArrayDouble());}
            jtfMax.setText("" + num);
        }
    }
}
