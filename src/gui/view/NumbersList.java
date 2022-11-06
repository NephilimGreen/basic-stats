package gui.view;

import java.awt.*;
import javax.swing.*;

import model.BasicStatsModel;

/**
 * The Numbers View is a visualization of the number of inputs the user has entered.
 */
public class NumbersList implements View
{
    private JTextArea jtfList;

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
        return jtfList;
    }

    /**
     * Updates this View to show a list of the values currently in the model, along with any feedback messages for the user.
     *
     * @param model The current BasicStatsModel to be visualized
     */
    @Override
    public void update(BasicStatsModel model)
    {
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
