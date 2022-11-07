package gui.view;

import java.awt.*;
import java.util.EnumMap;

import javax.swing.*;
import javax.swing.JTextField;

import gui.Composite.Element;
import model.BasicStatsModel;

/**
 * The Numbers View is a visualization of the number of inputs the user has entered.
 */
public class Count implements View
{
    private boolean made = false;
    private int updated = 0;

    private final BasicStatsModel model;

    private JPanel panel;
    private JLabel label;
    private JTextField jtfCount;

    public Count(BasicStatsModel mod)
    {
        model = mod;
    }

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

        made = true;
        return panel;
    }

    /**
     * Updates the JTextField to represent the number of values in the model.
     *
     * @param model The current BasicStatsModel to be visualized
     */
    @Override
    public void update()
    {
        if(made)
        {
            updated += 1;

            if(model.getArrayDouble().length == 0) {jtfCount.setText("");}
            else{jtfCount.setText("" + model.getArrayDouble().length);}
        }
    }

    /**
     * Returns <updated, the JLabel, the JTextField> if testingMode is on.
     * @return Enumerated map of names:Objects
     * @throws IllegalAccessException if the BasicStatsModel's testingMode == false
     */
    @Override
    public EnumMap<Element, Object> getElements() throws IllegalAccessException {
        if(!model.testingMode) {throw new IllegalAccessException("Testing mode is not on!");}
        EnumMap<Element, Object> rets = new EnumMap<>(Element.class);
        rets.put(Element.UPDATEDCOUNT, updated);
        rets.put(Element.COUNTLABEL, label);
        rets.put(Element.COUNTFIELD, jtfCount);
        return rets;
    }
}
