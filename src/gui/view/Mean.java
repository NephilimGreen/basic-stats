package gui.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.JTextField;

import gui.BasicStats;
import model.BasicStatsModel;

/**
 * The Numbers View is a visualization of the number of inputs the user has entered.
 */
public class Mean implements View
{
    private JPanel panel;
    private JLabel label;
    private JTextField jtfMean;

    /**
     * Creates a new JPanel, loads it with a JLabel containing "Mean:", then a read-only JTextField.
     * 
     * @return The created JPanel.
     */
    @Override
    public Container make()
    {
        panel = new JPanel();
        label = new JLabel("Mean:");
        jtfMean = new JTextField(5);
	    jtfMean.setEditable(false);
        panel.add(label);
        panel.add(jtfMean);
        return panel;
    }

    /**
     * Updates this View to represent the mean of the values in the model.
     *
     * @param model The current BasicStatsModel to be visualized
     */
    @Override
    public void update(BasicStatsModel model)
    {
        double num = 0.0;
        if(model.getArrayDouble().length > 0) {num = BasicStats.mean(model.getArrayDouble());}
        jtfMean.setText("" + num);
    }
}
