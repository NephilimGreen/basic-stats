package gui.controller;

import java.awt.Container;

import javax.swing.JTextField;

import model.BasicStatsModel;

public class Enter implements Controller
{
    JTextField number;

    /**
     * Creates a JTextField for the user to enter new values into.
     * 
     * @return The created JTextField.
     */
    @Override
    public Container make() {
        number = new JTextField(5);
        return number;
    }

    /**
     * Clears the entered value. Adds it to the model if possible.
     *
     * @param model The current BasicStatsModel to be worked in.
     */
    @Override
    public void update(BasicStatsModel model)
    {
        if(model.lastUpdateReasonIsAdd)
        {
            try {model.addNumber(Double.parseDouble(number.getText()));}
            catch(RuntimeException e) {model.failedAdd = true;}
        }
        number.setText("");
    }
}
