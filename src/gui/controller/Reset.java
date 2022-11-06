package gui.controller;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import gui.Composite;
import model.BasicStatsModel;

public class Reset implements Controller
{
    JButton button;
    BasicStatsModel model;
    Composite composite;

    /**
     * Constructs this Reset Component. Does NOT call make().
     * Saves the model and Composite to be assigned for updates once make() is called.
     * 
     * @param statsMod The model to be sent to the Composite's updates when the button is pressed.
     * @param comp The Composite to be updated when the button is pressed.
     */
    public Reset(BasicStatsModel statsMod, Composite comp)
    {
        model = statsMod;
        composite = comp;
    }

    /**
     * Creates a JButton for the user to press to reset the model.
     * 
     * @return The created JButton.
     */
    @Override
    public Container make() {
        button = new JButton("Reset");
        button.addActionListener(new ActionListener() {
			// The interface ActionListener defines a call-back method actionPerformed,
			// which is invoked if the user interacts with the GUI component -- in this
			// case, if the user clicks the button.
            @Override
			public void actionPerformed(ActionEvent e) {
				model.lastUpdateReasonIsAdd = false;

				composite.update(model);
			}
			});
        return button;
    }

    /**
     * Updates this Component to represent the current status of the BasicStatsModel.
     * Currently does nothing.
     *
     * @param model The current BasicStatsModel to be worked in.
     */
    @Override
    public void update(BasicStatsModel model)
    { 
    }
}
