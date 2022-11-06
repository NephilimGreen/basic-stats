package gui;

import java.util.*;
import java.util.List;

import javax.swing.*;

import java.awt.*;

import model.BasicStatsModel;
import gui.controller.*;
import gui.view.*;

/**
 * Create a simple GUI that includes:
 * - a text field and a button that allows the user to enter numbers.
 * - a button that allows the user to clear all entered numbers.
 * - a panel with labels and text fields for count, median, and mean.
 * - a text area that shows all numbers.
 *
 * For the MVC architecture pattern, those are the views and controllers.
 */
public class Composite implements Component
{
    private static final String APP_TITLE = "Simple stats";;
    private static BasicStatsModel model = new BasicStatsModel();
	private List<Component> components = new ArrayList<>();  // Must add items to this in order they are to be displayed.
	private static int TOP_ENDDEX;  // Last index (inclusive) of the elements in components to be added to the top section of the window.
	private static int MIDDLE_ENDDEX;  // Last index (inclusive) of the elements in components to be added to the middle section of the window.
    private JFrame jfMain;
    
    public Composite()
    {
        new Composite(true);
    }

    public Composite(boolean make)
    {
        if(make) {make();}
    }

    /**
     * Creates a JFrame that will contain all the other Components' Containers.
     * Initializes all other Components and their Containers, and adds the Containers to the JFrame.
     * 
     * @return The created JFrame.
     */
    @Override
    public Container make() {
        //Use these if any Components need to have make() called out of order.
        List<Integer> alreadyMade = new ArrayList<>();
        List<Container> made = new ArrayList<>();

        //Populate list of Components
        components.add(new Enter());
        components.add(new Add(model, this));
        components.add(new Reset(model, this));
        TOP_ENDDEX = 2;  // INCLUSIVE
        components.add(new Count());
        components.add(new Median());
        components.add(new Mean());
        MIDDLE_ENDDEX = 5;  // INCLUSIVE
        components.add(new NumbersList());  // Must be updated after Enter

        //Initialize the Window
        jfMain = new JFrame(APP_TITLE);
        jfMain.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		jfMain.setSize(600,400);
		jfMain.setLocationRelativeTo(null);

        //Add Components to the top of the Window
        JPanel jpTop = new JPanel(new FlowLayout(FlowLayout.CENTER));
		for(int i = 0; i <= TOP_ENDDEX; i++)
		{
            Container toAdd;
            if(!(alreadyMade.contains(i))) {toAdd = components.get(i).make();}
            else {toAdd = made.remove(0);}
			jpTop.add(toAdd);
		}
		
		jfMain.getContentPane().add(jpTop, BorderLayout.NORTH);

        //Add Components to the middle of the Window
		JPanel jpMiddle = new JPanel(new FlowLayout(FlowLayout.CENTER));
		for(int i = TOP_ENDDEX + 1; i <= MIDDLE_ENDDEX; i++)
		{
			Container toAdd;
            if(!(alreadyMade.contains(i))) {toAdd = components.get(i).make();}
            else {toAdd = made.remove(0);}
			jpMiddle.add(toAdd);
		}

		jfMain.getContentPane().add(jpMiddle, BorderLayout.CENTER);

        //Add Components to the bottom of the Window
		JPanel jpBottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
		for(int i = MIDDLE_ENDDEX + 1; i <= components.size() - 1; i++)
		{
			Container toAdd;
            if(!(alreadyMade.contains(i))) {toAdd = components.get(i).make();}
            else {toAdd = made.remove(0);}
			jpBottom.add(toAdd);
		}

		jfMain.getContentPane().add(jpBottom, BorderLayout.SOUTH);

        //Show the Window
        jfMain.setVisible(true);

        return jfMain;
    }

    /**
     * Updates this and all the other Components to represent the current status of the BasicStatsModel.
     *
     * @param model The current BasicStatsModel to be worked in.
     */
    @Override
    public void update(BasicStatsModel model)
    {
        if(!model.lastUpdateReasonIsAdd) {model.reset();}
        for(Component component : components)
        {
            component.update(model);
        }
    }
    
}
