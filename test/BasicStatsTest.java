import org.junit.Test;
import static org.junit.Assert.*;

import java.util.EnumMap;
import javax.swing.text.JTextComponent;
import gui.Component;
import gui.BasicStats;
import gui.Composite;
import gui.Composite.Element;
import model.BasicStatsModel;

public class BasicStatsTest {
    private static double EPS = 1e-9;

    @Test
    public void testCentralTendency() {
        double[] numbers = {2, 2, 3, 3, 3, 4, 4};
        double mean   = BasicStats.mean(numbers);
        assertEquals (3, mean, EPS);
        double median = BasicStats.median(numbers);
        assertEquals (3, median, EPS);
        double mode   = BasicStats.mode(numbers);
        assertEquals (3, mode, EPS);
    }

    @Test
    public void testMedian() {
      //Median should be 8.0 since size is even
      /* double[] numbers = {1, 4, 7, 9, 11, 21}; */
      double[] numbers = {9, 11, 1, 4, 7, 21};

      double median = BasicStats.median(numbers);
      assertEquals(8.0, median, EPS);

      //Median should be 7 since size is odd
      double[] numbers2 = {9, 1, 4, 7, 21};
      median = BasicStats.median(numbers2);
      assertEquals(7, median, EPS);

      //Median should be 3 since size is 1
      double[] numbers3 = {3};
      median = BasicStats.median(numbers3);
      assertEquals(3, median, EPS);

      //Median should be 0 since size is 0
      double[] numbers4 = {};
      median = BasicStats.median(numbers4);
      assertEquals(0, median, EPS);
    }

    @Test
    public void testMode() {
      //Mode should be 1 since we are taking the first mode
      double[] numbers = {1, 4, 7, 9, 11, 21};
      double mode   = BasicStats.mode(numbers);
      assertEquals (1, mode, EPS);

      //Mode should be 4
      double[] numbers2 = {1, 4, 4, 7, 9, 11, 21};
      mode   = BasicStats.mode(numbers2);
      assertEquals (4, mode, EPS);

      //Mode should be 7
      double[] numbers3 = {4, 4, 7, 7, 7, 11, 11};
      mode   = BasicStats.mode(numbers3);
      assertEquals (7, mode, EPS);

      //Mode should be 7
      double[] numbers4 = {7};
      mode   = BasicStats.mode(numbers4);
      assertEquals (7, mode, EPS);

      //Mode should be 0
      double[] numbers5 = {};
      mode   = BasicStats.mode(numbers5);
      assertEquals (0, mode, EPS);
    }

    @Test
    public void testMVC()
    {
      Composite composite = new Composite(false, false, true);
      
      composite.update();
      composite.make();
      int updates = 0;
      try
      {
        EnumMap<Element, Object> elementsMap = composite.getElements();
        if((Integer)elementsMap.get(Element.UPDATEDCOUNT) > 0) {updates += 1;}
        for(Element e : Composite.COMPONENT_KEYS)
        {
          if((Integer)((Component)elementsMap.get(e)).getElements().get(Element.UPDATEDCOUNT) > 0) {updates += 1;}
        }
      }
      catch(IllegalAccessException e)
      {
        fail("getElements() returned IllegalAccessException");
      }
      assertEquals(updates + " updates", 0, updates);

      composite.update();
      int missedUpdates = 0;
      try
      {
        EnumMap<Element, Object> elementsMap = composite.getElements();
        if((Integer)elementsMap.get(Element.UPDATEDCOUNT) < 1) {missedUpdates += 1;}
        for(Element e : Composite.COMPONENT_KEYS)
        {
          if((Integer)((Component)elementsMap.get(e)).getElements().get(Element.UPDATEDCOUNT) < 1) {missedUpdates += 1;}
        }
      }
      catch(IllegalAccessException e)
      {
        fail("getElements() returned IllegalAccessException");
      }
      assertEquals(missedUpdates + " missed updates", 0, missedUpdates);
    }

    @Test
    public void testInitialConfig()
    {
      Composite composite = new Composite(true, false, true);
      
      int populatedTexts = 0;
      try
      {
        EnumMap<Element, Object> elementsMap = composite.getElements();
        for(Element e : Composite.JTEXTCOMPONENT_KEYS)
        {
          if(!((JTextComponent)elementsMap.get(e)).getText().equals("")) {populatedTexts += 1;}
        }
      }
      catch(IllegalAccessException e)
      {
        fail("getElements() returned IllegalAccessException");
      }
      assertEquals(populatedTexts + " populated texts", 0, populatedTexts);
    }

    @Test
    public void testAddNumber()
    {
      Composite composite = new Composite(true, false, true);

      String numbers = "";
      String enterAfter = "";
      try
      {
        EnumMap<Element, Object> elements = composite.getElements();
        ((JTextComponent)elements.get(Element.ENTERFIELD)).setText("1");
        ((BasicStatsModel)elements.get(Element.MODEL)).lastUpdateReasonIsAdd = true;
        composite.update();
        numbers = ((JTextComponent)elements.get(Element.NUMBERSLISTAREA)).getText();
        enterAfter = ((JTextComponent)elements.get(Element.ENTERFIELD)).getText();
      }
      catch(IllegalAccessException e)
      {
        fail("getElements() returned IllegalAccessException");
      }
      assertEquals("After adding \"1\", numbers list displays: " + numbers, "1.0", numbers);
      assertEquals("After adding \"1\", enter field displays: " + enterAfter, "", enterAfter);

      composite.make();
      try
      {
        EnumMap<Element, Object> elements = composite.getElements();
        ((JTextComponent)elements.get(Element.ENTERFIELD)).setText("one");
        ((BasicStatsModel)elements.get(Element.MODEL)).lastUpdateReasonIsAdd = true;
        composite.update();
        numbers = ((JTextComponent)elements.get(Element.NUMBERSLISTAREA)).getText();
        enterAfter = ((JTextComponent)elements.get(Element.ENTERFIELD)).getText();
      }
      catch(IllegalAccessException e)
      {
        fail("getElements() returned IllegalAccessException");
      }
      assertEquals("After adding \"one\", numbers list displays: " + numbers, BasicStatsModel.IMPROPER_ADD_MESSAGE, numbers);
      assertEquals("After adding \"one\", enter field displays: " + enterAfter, "", enterAfter);
    }

    @Test
    public void testMax()
    {
      double[] numbersEmpty = {};
      double max = BasicStats.max(numbersEmpty);
      assertEquals(Double.NEGATIVE_INFINITY, max, EPS);  // max() returns NEGATIVE_INFINITY if passed an empty list (i.e. no max)

      double[] numbersPopulated = {3, 5, 1, 2, 4};
      max = BasicStats.max(numbersPopulated);
      assertEquals(5, max, EPS);

      double[] numbersDecimal = {1.5, 4.5, 2.5, 0.5, 4.50000001, 2.1};
      max = BasicStats.max(numbersDecimal);
      assertEquals(4.50000001, max, EPS);
    }

    @Test
    public void testReset()
    {
      Composite composite = new Composite(true, false, true);

      int populatedTexts = 0;

      try
      {
        EnumMap<Element, Object> elements = composite.getElements();
        ((JTextComponent)elements.get(Element.ENTERFIELD)).setText("1");
        ((BasicStatsModel)elements.get(Element.MODEL)).lastUpdateReasonIsAdd = true;
        composite.update();
        ((JTextComponent)elements.get(Element.ENTERFIELD)).setText("4.3");
        ((BasicStatsModel)elements.get(Element.MODEL)).lastUpdateReasonIsAdd = true;
        composite.update();
        ((JTextComponent)elements.get(Element.ENTERFIELD)).setText("59");
        ((BasicStatsModel)elements.get(Element.MODEL)).lastUpdateReasonIsAdd = true;
        composite.update();
        ((JTextComponent)elements.get(Element.ENTERFIELD)).setText("one");
        ((BasicStatsModel)elements.get(Element.MODEL)).lastUpdateReasonIsAdd = true;
        composite.update();

        ((BasicStatsModel)elements.get(Element.MODEL)).lastUpdateReasonIsAdd = false;
        composite.update();
      
        EnumMap<Element, Object> elementsMap = composite.getElements();
        for(Element e : Composite.JTEXTCOMPONENT_KEYS)
        {
          if(!((JTextComponent)elementsMap.get(e)).getText().equals("")) {populatedTexts += 1;}
        }
      }
      catch(IllegalAccessException e)
      {
        fail("getElements() returned IllegalAccessException");
      }
      assertEquals(populatedTexts + " populated texts", 0, populatedTexts);
    }
}
