package org.wattdepot.cli.command;

import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;
import org.wattdepot.cli.CommandLineInterface;
import org.wattdepot.client.ResourceNotFoundException;
import org.wattdepot.client.WattDepotClientException;
import org.wattdepot.util.tstamp.Tstamp;

/**
 * Computes the carbon content of a power source.
 * 
 * @author BJ Peter DeLaCruz, Wahib Hanani, Lyneth Peou
 * @version 2.0
 */
public class CarbonContent extends CliCommandInterface {

  /**
   * Carries out the command inputted by the user on the command prompt.
   * 
   * @param cli A CommandLineInterface object.
   * @param commandList Command inputted by the user on the command prompt.
   * @return Result after running the command.
   */
  public String doCommand(CommandLineInterface cli, List<String> commandList) {
    return carbonContent(cli, commandList);
  }

  /**
   * Computes the carbon content of a power source, assuming a constant rate of emission for the
   * total amount of carbon generated and a constant rate of generation for the total amount of
   * energy generated, given the following formula: (total carbon generated / total energy
   * generated). Units are lbs CO2 / MWh.
   * 
   * @param cli A CommandLineInterface object.
   * @param commandList Command inputted by the user on the command prompt.
   * @return Carbon content of a power source.
   */
  private final String carbonContent(CommandLineInterface cli, List<String> commandList) {
    try {
      cli.CLIENT.getSource(commandList.get(1));
    }
    catch (ResourceNotFoundException e) {
      if (cli.debuggingMode) {
        cli.errorEncountered = true;
      } // end if
      return ERROR_MESSAGE + CARRIAGE_RETURN + "Power source not found.";
    } // end catch
    catch (Exception e) {
      cli.errorEncountered = true;
      return ERROR_MESSAGE + CARRIAGE_RETURN + e.toString() + CARRIAGE_RETURN + EXIT_MESSAGE;
    }

    XMLGregorianCalendar startTime;
    try {
      startTime = Tstamp.makeTimestamp(commandList.get(2));
    }
    catch (Exception e) {
      if (cli.debuggingMode) {
        cli.errorEncountered = true;
      } // end if
      return ERROR_MESSAGE + CARRIAGE_RETURN + "Invalid timestamp string. Please try again.";
    } // end catch
    XMLGregorianCalendar endTime = Tstamp.incrementHours(startTime, 1);

    int interval;
    try {
      interval = Integer.parseInt((commandList.get(4)));
    }
    catch (NumberFormatException nfe) {
      if (cli.debuggingMode) {
        cli.errorEncountered = true;
      } // end if
      return ERROR_MESSAGE + CARRIAGE_RETURN
          + "Number for sampling interval is in wrong format. Please try again.";
    } // end catch

    if (interval <= 0) {
      if (cli.debuggingMode) {
        cli.errorEncountered = true;
      } // end if
      return ERROR_MESSAGE + CARRIAGE_RETURN
          + "Invalid sampling interval. Please enter a number greater than 0.";
    } // end if

    double carbonGenerated, energyGenerated;
    try {
      carbonGenerated =
          cli.CLIENT.getCarbonEmitted(commandList.get(1), startTime, endTime, interval);
      energyGenerated =
          cli.CLIENT.getEnergyGenerated(commandList.get(1), startTime, endTime, interval);
    }
    catch (WattDepotClientException wdce) {
      cli.errorEncountered = true;
      return ERROR_MESSAGE + CARRIAGE_RETURN + wdce.toString() + CARRIAGE_RETURN + EXIT_MESSAGE;
    }

    double carbonRate;
    if (energyGenerated == 0.0) {
      if (cli.debuggingMode) {
        cli.errorEncountered = true;
      } // end if
      return "Data regarding carbon content for " + commandList.get(1) + " is unavailable.";
    } // end if
    else {
      carbonRate = carbonGenerated / energyGenerated * 1000000.0;
    }

    return carbonRate + " lbs CO2 / MWh";
  }

}