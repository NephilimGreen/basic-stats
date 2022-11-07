package gui.view;

import java.awt.*;
import java.util.EnumMap;

import javax.swing.*;

import gui.Composite.Element;
import model.BasicStatsModel;

/**
 * The Numbers View is a visualization of the number of inputs the user has entered.
 */
public class NumbersList implements View
{
    private boolean made = false;
    private int updated = 0;

    private final BasicStatsModel model;

    private JTextArea jtfList;

    public NumbersList(BasicStatsModel mod)
    {
        model = mod;
    }

    /**
     * Creates a read-only JTextArea.
     * 
     * @return The created JTextArea.
     */
    @Override
    public Container make()
    {
        jtfList = new JTextArea(10, 50);
	    jtfList.setEditable(false);
        jtfList.setText("");

        made = true;
        return jtfList;
    }

    /**
     * Updates this View to show a list of the values currently in the model, along with any feedback messages for the user.
     */
    @Override
    public void update()
    {
        if(made)
        {
            updated += 1;

            if(jtfList.getText().endsWith(BasicStatsModel.IMPROPER_ADD_MESSAGE))
            {
                jtfList.setText(jtfList.getText().substring(0, jtfList.getText().length()
                                                                            - BasicStatsModel.IMPROPER_ADD_MESSAGE.length()));
            }
            else if(jtfList.getText().endsWith(BasicStatsModel.RESET_MESSAGE))
            {
                jtfList.setText(jtfList.getText().substring(0, jtfList.getText().length()
                                                                            - BasicStatsModel.RESET_MESSAGE.length()));
            }

            if(jtfList.getText().endsWith(", "))
            {
                jtfList.setText(jtfList.getText().substring(0, jtfList.getText().length() - 2));
            }

            if(!model.lastUpdateReasonIsAdd) {jtfList.setText("");}  //jtfList.setText(BasicStatsModel.RESET_MESSAGE);}
            else if(model.failedAdd)
            {
                if(model.getArrayDouble().length > 0) {jtfList.append(", ");}
                jtfList.append(BasicStatsModel.IMPROPER_ADD_MESSAGE);
                model.failedAdd = false;
            }
            else
            {
                if(model.getArrayDouble().length > 1) {jtfList.append(", ");}
                jtfList.append("" + model.getArrayDouble()[model.getArrayDouble().length - 1]);
            }
        }
    }

    /**
     * Returns {updated, the JTextArea} if testingMode is on.
     * @return Enumerated map of names:Objects
     * @throws IllegalAccessException if the BasicStatsModel's testingMode == false
     */
    @Override
    public EnumMap<Element, Object> getElements() throws IllegalAccessException {
        if(!model.testingMode) {throw new IllegalAccessException("Testing mode is not on!");}
        EnumMap<Element, Object> rets = new EnumMap<>(Element.class);
        rets.put(Element.UPDATEDCOUNT, updated);
        rets.put(Element.NUMBERSLISTAREA, jtfList);
        return rets;
    }
}
