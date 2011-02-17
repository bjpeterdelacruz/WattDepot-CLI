package org.wattdepot.cli.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.wattdepot.cli.CommandLineInterface;
import org.wattdepot.cli.processor.CliSource;
import org.wattdepot.resource.source.jaxb.Source;

/**
 * Lists information about the fuel type that a power source uses.
 * 
 * @author BJ Peter DeLaCruz, Wahib Hanani, Lyneth Peou
 * @version 2.0
 */
public class FuelTypes extends CliCommandInterface {
  /** Contains fuel types (keys) and subsources (values). */
  private Map<String, String> mapFuelTypes = new HashMap<String, String>();

  /**
   * Carries out the command inputted by the user on the command prompt.
   * 
   * @param cli A CommandLineInterface object.
   * @param commandList Command inputted by the user on the command prompt.
   * @return Result after running the command.
   */
  public String doCommand(CommandLineInterface cli, List<String> commandList) {
    return fuelTypes(cli, commandList.get(1));
  }

  /**
   * Lists information about the fuel type that a power source uses.
   * 
   * @param cli A CommandLineInterface object.
   * @param sourceName The name of a power source.
   * @return Information about the fuel type that a power source uses.
   */
  private final String fuelTypes(CommandLineInterface cli, String sourceName) {

    Source source = (new CliSource()).getSource(cli, sourceName);

    if (cli.errorEncountered || source == null) {
      if (cli.debuggingMode) {
        cli.errorEncountered = true;
      } // end if
      return "Error encountered when processing name of power source.";
    } // end if

    String format = "  %1$-10s %2$-20s" + CARRIAGE_RETURN;
    StringBuffer buffer = new StringBuffer(2500);
    String fuelType = "fuelType";

    mapFuelTypes.clear();

    if (source.isVirtual()) {
      List<Source> subsources =
          NonVirtualSubsources.getSubsources(cli, source, new ArrayList<Source>());

      String tempValue = "";
      for (Source temp : subsources) {
        if (mapFuelTypes.containsKey(temp.getProperty(fuelType))) {
          tempValue = mapFuelTypes.get(temp.getProperty(fuelType));
          mapFuelTypes.put(temp.getProperty(fuelType), tempValue + ", " + temp.getName());
        } // end if
        else {
          mapFuelTypes.put(temp.getProperty(fuelType), temp.getName());
        } // end else
      } // end for

      for (Entry<String, String> subsource : mapFuelTypes.entrySet()) {
        buffer.append(String.format(format, subsource.getKey(), subsource.getValue()));
      } // end for
    } // end if
    else {
      buffer.append(String.format(format, source.getProperty(fuelType), source.getName()));
    }

    return buffer.toString();
  }

  /**
   * Returns a hash map of fuel types (keys) and subsources (values). For testing purposes only.
   * 
   * @return A hash map of fuel types (keys) and subsources (values).
   */
  public final Map<String, String> getSubsources() {
    return mapFuelTypes;
  }

}