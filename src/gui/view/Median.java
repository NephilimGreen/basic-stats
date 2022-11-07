package gui.view;

import java.awt.*;
import java.util.EnumMap;

import javax.swing.*;
import javax.swing.JTextField;

import gui.BasicStats;
import gui.Composite.Element;
import model.BasicStatsModel;

/**
 * The Numbers View is a visualization of the number of inputs the user has entered.
 */
public class Median implements View
{
    private boolean made = false;
    private int updated = 0;

    private final BasicStatsModel model;

    private JPanel panel;
    private JLabel label;
    private JTextField jtfMedian;

    public Median(BasicStatsModel mod)
    {
        model = mod;
    }

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

        made = true;
        return panel;
    }

    /**
     * Updates this View to represent the median of the values in the model.
     */
    @Override
    public void update()
    {
        if(made)
        {
            updated += 1;
            if(model.getArrayDouble().length == 0) {jtfMedian.setText("");}
            else
            {
                double num = 0.0;
                if(model.getArrayDouble().length > 0) {num = BasicStats.median(model.getArrayDouble());}
                jtfMedian.setText("" + num);
            }
        }
    }

    /**
     * Returns {updated, the JLabel, the JTextField} if testingMode is on.
     * @return Enumerated map of names:Objects
     * @throws IllegalAccessException if the BasicStatsModel's testingMode == false
     */
    @Override
    public EnumMap<Element, Object> getElements() throws IllegalAccessException {
        if(!model.testingMode) {throw new IllegalAccessException("Testing mode is not on!");}
        EnumMap<Element, Object> rets = new EnumMap<>(Element.class);
        rets.put(Element.UPDATEDCOUNT, updated);
        rets.put(Element.MEDIANLABEL, label);
        rets.put(Element.MEDIANFIELD, jtfMedian);
        return rets;
    }
}
