package org.wattdepot.cli.command;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;
import javax.xml.datatype.XMLGregorianCalendar;
import org.wattdepot.cli.CommandLineInterface;
import org.wattdepot.cli.processor.CommandProcessor;
import org.wattdepot.util.tstamp.Tstamp;

/**
 * Gets data about the Oahu power grid.
 * 
 * @author BJ Peter DeLaCruz, Wahib Hanani, Lyneth Peou
 * @version 2.0
 */
public abstract class OahuData extends CliCommandTest {
  /** Number of days in November. */
  private static final int NUMBER_OF_MONTHS = 30;

  /**
   * Gets data about the highest and lowest amount of carbon emitted.
   * 
   * @throws Exception If a problem occurs.
   */
  public void testGetCarbonGenerated() throws Exception {
    CommandProcessor commandProcessor = new CommandProcessor();
    CommandLineInterface cli = null;
    String result = "";
    String command = "";

    Map<XMLGregorianCalendar, String> carbonRecords = new HashMap<XMLGregorianCalendar, String>();

    carbonRecords.clear();

    XMLGregorianCalendar basedDay = Tstamp.makeTimestamp("2009-11-01");
    XMLGregorianCalendar newDay = basedDay;

    ArrayList<String> c = new ArrayList<String>();

    for (int i = 1; i <= NUMBER_OF_MONTHS; i++) {
      command = "total carbon generated SIM_OAHU_GRID day " + newDay + " sampling-interval 60";

      List<String> commandList = commandProcessor.parseCommand(command);
      cli = new CommandLineInterface();

      result = (new TotalCarbonOrEnergy()).doCommand(cli, commandList);

      String g =
          (new DecimalFormat("0.0")).format(Float.parseFloat(result.substring(
              result.indexOf(':') + 2, result.indexOf('.') + 1)));

      c.add(g);
      carbonRecords.put(newDay, g);
      newDay = Tstamp.incrementDays(newDay, 1);
    }
    Collections.sort(c);

    String min = Collections.min(c);
    String max = Collections.max(c);

    Set<Map.Entry<XMLGregorianCalendar, String>> s = carbonRecords.entrySet();

    Iterator<Map.Entry<XMLGregorianCalendar, String>> i = s.iterator();

    while (i.hasNext()) {
      Map.Entry<XMLGregorianCalendar, String> m = i.next();
      String v = m.getValue();
      if (v.compareTo(min) == 0) {
        System.out.println("Lowest carbon emitted day: " + m.getKey() + "=" + m.getValue());
      } // end if
      if (v.compareTo(max) == 0) {
        System.out.println("Highest carbon emitted day: " + m.getKey() + " = " + m.getValue());
      } // end if
    } // end while
  }

}