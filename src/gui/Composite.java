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
    public static enum Element
    {
        MODEL,          // BasicStatsModel
        UPDATEDCOUNT,   // int
        JFMAIN,         // JFrame
        ENTER,          // Controller
        ENTERFIELD,     // JTextField
        ADD,            // Controller
        ADDBUTTON,      // JButton
        RESET,          // Controller
        RESETBUTTON,    // JButton
        COUNT,          // View
        COUNTLABEL,     // JLabel
        COUNTFIELD,     // JTextField
        MEDIAN,         // View
        MEDIANLABEL,    // JLabel
        MEDIANFIELD,    // JTextField
        MEAN,           // View
        MEANLABEL,      // JLabel
        MEANFIELD,      // JTextField
        MAX,            // View
        MAXLABEL,       // JLabel
        MAXFIELD,       // JTextField
        NUMBERSLIST,    // View
        NUMBERSLISTAREA // JTextArea
    }

    public static final Collection<Element> COMPONENT_KEYS = new ArrayList<>(Arrays.asList(new Element[]
                                                            {Element.ENTER, Element.ADD, Element.RESET, Element.COUNT,
                                                             Element.MEDIAN, Element.MEAN, Element.MAX, Element.NUMBERSLIST}));
    public static final Collection<Element> JTEXTCOMPONENT_KEYS = new ArrayList<>(Arrays.asList(new Element[]
                                                                 {Element.ENTERFIELD, Element.COUNTFIELD, Element.MEDIANFIELD,
                                                                  Element.MEANFIELD, Element.MAXFIELD, Element.NUMBERSLISTAREA}));

    private boolean thisMade = false;
    private int updated = 0;

    public boolean show;
    private boolean shown = false;

    private static final String APP_TITLE = "Simple stats";;
    private static BasicStatsModel model;
	private List<Component> components = new ArrayList<>();  // Must add items to this in order they are to be displayed.
	private static int TOP_ENDDEX;  // Last index (inclusive) of the elements in components to be added to the top section of the window.
	private static int MIDDLE_ENDDEX;  // Last index (inclusive) of the elements in components to be added to the middle section of the window.
    private JFrame jfMain;
    
    public Composite()
    {
        new Composite(true, true, false);
    }

    public Composite(boolean make, boolean showWhenMake, boolean testingMode)
    {
        show = showWhenMake;
        model = new BasicStatsModel(testingMode);
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
        model.reset();

        //Use these if any Components need to have make() called out of order.
        List<Integer> alreadyMade = new ArrayList<>();
        List<Container> made = new ArrayList<>();

        //Populate list of Components
        components.add(new Enter(model));
        components.add(new Add(model, this));
        components.add(new Reset(model, this));
        TOP_ENDDEX = 2;  // INCLUSIVE
        components.add(new Count(model));
        components.add(new Median(model));
        components.add(new Mean(model));
        components.add(new Max(model));
        MIDDLE_ENDDEX = 6;  // INCLUSIVE
        components.add(new NumbersList(model));  // Must be updated after Enter

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

        //Show the Window now if set to do so
        if(show) 
        {
            jfMain.setVisible(true);
            shown = true;
        }

        thisMade = true;
        return jfMain;
    }

    /**
     * Updates this and all the other Components to represent the current status of the BasicStatsModel.
     */
    @Override
    public void update()
    {
        if(thisMade)
        {
            updated += 1;

            if(show && !shown)
            {
                jfMain.setVisible(true);
                shown = true;
            }

            if(!model.lastUpdateReasonIsAdd) {model.reset();}
            for(Component component : components)
            {
                component.update();
            }
        }
    }

    /**
     * Returns {updated, the BasicStatsModel, the JFrame,
     *          subordinate Component 0, subordinate 0's getElements() mappings except UPDATEDCOUNT,
     *          subordinate Component 1, ...}
     * if testingMode is on. Does not include subordinate stuff if make() has not been called either internally or externally.
     * @return Enumerated map of names:Objects
     * @throws IllegalAccessException if the BasicStatsModel's testingMode == false
     */
    @Override
    public EnumMap<Element, Object> getElements() throws IllegalAccessException {
        if(!model.testingMode) {throw new IllegalAccessException("Testing mode is not on!");}
        EnumMap<Element, Object> rets = new EnumMap<>(Element.class);
        rets.put(Element.MODEL, model);
        rets.put(Element.JFMAIN, jfMain);
        if(thisMade)
        {
            rets.put(Element.ENTER, components.get(0));
            rets.putAll(components.get(0).getElements());
            rets.put(Element.ADD, components.get(1));
            rets.putAll(components.get(1).getElements());
            rets.put(Element.RESET, components.get(2));
            rets.putAll(components.get(2).getElements());
            rets.put(Element.COUNT, components.get(3));
            rets.putAll(components.get(3).getElements());
            rets.put(Element.MEDIAN, components.get(4));
            rets.putAll(components.get(4).getElements());
            rets.put(Element.MEAN, components.get(5));
            rets.putAll(components.get(5).getElements());
            rets.put(Element.MAX, components.get(6));
            rets.putAll(components.get(6).getElements());
            rets.put(Element.NUMBERSLIST, components.get(7));
            rets.putAll(components.get(7).getElements());
        }
        rets.put(Element.UPDATEDCOUNT, updated);  // MUST ALWAYS BE AFTER SUBORDINATE COMPONENTS (they also have this key)
        return rets;
    }
    
}
