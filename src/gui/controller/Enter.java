package gui.controller;

import java.awt.Container;
import java.util.EnumMap;

import javax.swing.JTextField;

import gui.Composite.Element;
import model.BasicStatsModel;

public class Enter implements Controller
{
    private boolean made = false;
    private int updated = 0;

    private final BasicStatsModel model;

    JTextField number;

    public Enter(BasicStatsModel mod)
    {
        model = mod;
    }

    /**
     * Creates a JTextField for the user to enter new values into.
     * 
     * @return The created JTextField.
     */
    @Override
    public Container make() {
        number = new JTextField(5);

        made = true;
        return number;
    }

    /**
     * Clears the entered value. Adds it to the model if possible.
     */
    @Override
    public void update()
    {
        if(made)
        {
            updated += 1;

            if(model.lastUpdateReasonIsAdd)
            {
                try {model.addNumber(Double.parseDouble(number.getText()));}
                catch(RuntimeException e) {model.failedAdd = true;}
            }
            number.setText("");
        }
    }

    /**
     * Returns {updated, the JTextField} if testingMode is on.
     * @return Enumerated map of names:Objects
     * @throws IllegalAccessException if the BasicStatsModel's testingMode == false
     */
    @Override
    public EnumMap<Element, Object> getElements() throws IllegalAccessException {
        if(!model.testingMode) {throw new IllegalAccessException("Testing mode is not on!");}
        EnumMap<Element, Object> rets = new EnumMap<>(Element.class);
        rets.put(Element.UPDATEDCOUNT, updated);
        rets.put(Element.ENTERFIELD, number);
        return rets;
    }
}
