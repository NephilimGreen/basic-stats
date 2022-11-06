package gui.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.JTextField;

import gui.BasicStats;
import model.BasicStatsModel;

/**
 * The Numbers View is a visualization of the number of inputs the user has entered.
 */
public class Median implements View
{
    private JPanel panel;
    private JLabel label;
    private JTextField jtfMedian;

    /**
     * Creates a new JPanel, loads it with a JLabel containing "Median:", then a read-only JTextField.
     * 
     * @return The created JPanel.
     */
    @Override
    public Container make()
    {
        panel = new JPanel();
        label = new JLabel("Median:");
        jtfMedian = new JTextField(5);
	    jtfMedian.setEditable(false);
        panel.add(label);
        panel.add(jtfMedian);
        return panel;
    }

    /**
     * Updates this View to represent the median of the values in the model.
     *
     * @param model The current BasicStatsModel to be visualized
     */
    @Override
    public void update(BasicStatsModel model)
    {
        if(model.getArrayDouble().length == 0) {jtfMedian.setText("");}
        else
        {
            double num = 0.0;
            if(model.getArrayDouble().length > 0) {num = BasicStats.median(model.getArrayDouble());}
            jtfMedian.setText("" + num);
        }
    }
}
