package org.wattdepot.cli.command;

import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;
import org.wattdepot.cli.CommandLineInterface;
import org.wattdepot.cli.processor.CliSource;
import org.wattdepot.util.tstamp.Tstamp;
import org.wattdepot.resource.source.jaxb.Source;

/**
 * Returns the total power generated by all non-virtual power sources associated with a given power
 * source that use the specified fuel type.
 * 
 * @author BJ Peter DeLaCruz, Wahib Hanani, Lyneth Peou
 * @version 2.0
 */
public class TotalPowerFuelType extends CliCommandInterface {

  /**
   * Carries out the command inputted by the user on the command prompt.
   * 
   * @param cli A CommandLineInterface object.
   * @param commandList Command inputted by the user on the command prompt.
   * @return Result after running the command.
   */
  public String doCommand(CommandLineInterface cli, List<String> commandList) {
    return totalPower(cli, commandList);
  }

  /**
   * Returns the total power generated by all non-virtual power sources associated with a given
   * power source that use the specified fuel type.
   * 
   * @param cli A CommandLineInterface object.
   * @param command Command inputted by the user on the command prompt.
   * @return Total power generated by all non-virtual power sources that use the specified fuel
   * type.
   */
  private final String totalPower(CommandLineInterface cli, List<String> command) {
    double total = 0.0;
    String fuel = command.get(4);
    XMLGregorianCalendar time = null;

    Source source = (new CliSource().getSource(cli, command.get(1)));

    if (cli.errorEncountered || source == null) {
      if (cli.debuggingMode) {
        cli.errorEncountered = true;
      } // end if
      if (source == null) {
        return ERROR_MESSAGE + "Power source not found.";
      } // end if
      return "Error encountered when processing name of power source.";
    } // end if

    try {
      time = Tstamp.makeTimestamp(command.get(2));
    }
    catch (Exception e1) {
      if (cli.debuggingMode) {
        cli.errorEncountered = true;
      } // end if
      return ERROR_MESSAGE + STRING_CONVERSION_ERROR;
    } // end catch

    try {
      // Get the list of all related non-virtual power sources.
      if (source.isVirtual()) {

        List<Source> listNonVirtualSource =
            NonVirtualSubsources.getSubsources(cli, source, new ArrayList<Source>());

        // Only add to the total power if subsource uses the same fuel type.
        for (Source s : listNonVirtualSource) {
          if (s.getProperty("fuelType").equalsIgnoreCase(fuel)) {
            try {
              total += cli.CLIENT.getPowerGenerated(s.getName(), time) / 1000000.0;
            } // end try
            catch (Exception e) {
              if (cli.debuggingMode) {
                cli.errorEncountered = true;
              } // end if
              return ERROR_MESSAGE + CARRIAGE_RETURN + e.toString() + EXIT_MESSAGE
                  + CARRIAGE_RETURN;
            } // end catch
          } // end if
        } // end for
      } // end if
      else if (!source.isVirtual()) {
        if (source.getProperty("fuelType").equalsIgnoreCase(fuel)) {
          total = cli.CLIENT.getPowerGenerated(source.getName(), time) / 1000000.0;
        } // end if
        else {
          return ERROR_MESSAGE + "Not the same fuel type that this power source uses.";
        } // end else
      } // end else if
    } // end try
    catch (Exception e) {
      cli.errorEncountered = true;
      return ERROR_MESSAGE + CARRIAGE_RETURN + e.toString() + CARRIAGE_RETURN + EXIT_MESSAGE;
    }
    return Double.toString(total) + " MW";
  }

}