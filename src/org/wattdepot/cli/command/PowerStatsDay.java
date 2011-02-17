package org.wattdepot.cli.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;
import org.wattdepot.cli.CommandLineInterface;
import org.wattdepot.client.ResourceNotFoundException;
import org.wattdepot.client.WattDepotClientException;
import org.wattdepot.util.tstamp.Tstamp;

/**
 * Returns the maximum, minimum, or average power generated or consumed by a power source for a
 * given day. The statistic is obtained by sampling the power source at the specified minute
 * intervals for the course of the day.
 * 
 * @author BJ Peter DeLaCruz, Wahib Hanani, Lyneth Peou
 * @version 2.0
 */
public class PowerStatsDay extends CliCommandInterface {

  /**
   * Carries out the command inputted by the user on the command prompt.
   * 
   * @param cli A CommandLineInterface object.
   * @param commandList Command inputted by the user on the command prompt.
   * @return Result after running the command.
   */
  public String doCommand(CommandLineInterface cli, List<String> commandList) {
    return listPowerDayStatistic(cli, commandList);
  }

  /**
   * Returns the maximum, minimum, or average power generated or consumed by a power source for a
   * given day. The statistic is obtained by sampling the power source at the specified minute
   * intervals for the course of the day.
   * 
   * @param cli A CommandLineInterface object.
   * @param command Command inputted by the user on the command prompt.
   * @return Information about the maximum, minimum, or average power generated or consumed by a
   * power source.
   */
  private final String listPowerDayStatistic(CommandLineInterface cli, List<String> command) {
    XMLGregorianCalendar startTime = null;
    try {
      startTime = Tstamp.makeTimestamp(command.get(4));
    }
    catch (Exception e) {
      if (cli.debuggingMode) {
        cli.errorEncountered = true;
      } // end if
      return ERROR_MESSAGE + STRING_CONVERSION_ERROR;
    } // end catch

    String powerSource = command.get(2);
    StringBuffer buffer = new StringBuffer(255);

    int interval;
    try {
      interval = Integer.parseInt((command.get(6)));
    }
    catch (NumberFormatException nfe) {
      if (cli.debuggingMode) {
        cli.errorEncountered = true;
      } // end if
      return ERROR_MESSAGE + "Number for sampling interval is in wrong format. Please try again.";
    } // end catch
    if (interval <= 0) {
      if (cli.debuggingMode) {
        cli.errorEncountered = true;
      } // end if
      return ERROR_MESSAGE + "Invalid sampling interval. Please enter a number greater than 0.";
    } // end if
    ArrayList<Double> powerList = new ArrayList<Double>();
    double power = 0.0;
    double totalPower = 0.0;

    try {
      cli.CLIENT.getSource(powerSource);
    }
    catch (ResourceNotFoundException e) {
      if (cli.debuggingMode) {
        cli.errorEncountered = true;
      } // end if
      return ERROR_MESSAGE + "Power source not found.";
    } // end catch
    catch (Exception e) {
      cli.errorEncountered = true;
      return ERROR_MESSAGE + CARRIAGE_RETURN + e.toString() + CARRIAGE_RETURN + EXIT_MESSAGE;
    }

    String powerCommand = command.get(1);
    String statistic = command.get(8);

    if (!statistic.equalsIgnoreCase("max") && !statistic.equalsIgnoreCase("min")
        && !statistic.equalsIgnoreCase("average")) {
      if (cli.debuggingMode) {
        cli.errorEncountered = true;
      } // end if
      return ERROR_MESSAGE + "Invalid statistic command. Please try again.";
    } // end if

    XMLGregorianCalendar endTime = Tstamp.incrementDays(startTime, 1);

    try {
      while (Tstamp.greaterThan(endTime, startTime)) {
        if (powerCommand.equalsIgnoreCase(GENERATED_COMMAND)) {
          power = cli.CLIENT.getPowerGenerated(powerSource, startTime);
        } // end if
        else if (powerCommand.equalsIgnoreCase(CONSUMED_COMMAND)) {
          power = cli.CLIENT.getPowerConsumed(powerSource, startTime);
        } // end else if
        totalPower += power;
        powerList.add(power);
        startTime = Tstamp.incrementMinutes(startTime, interval);
      } // end while
    } // end try
    catch (WattDepotClientException wdce) {
      cli.errorEncountered = true;
      return ERROR_MESSAGE + CARRIAGE_RETURN + wdce.toString() + CARRIAGE_RETURN + EXIT_MESSAGE;
    }

    Collections.sort(powerList);
    String result = "0.0";
    if (statistic.equalsIgnoreCase("max")) {
      result = ONE_DECIMAL_PLACE.format(Collections.max(powerList));
    }
    else if (statistic.equalsIgnoreCase("min")) {
      result = ONE_DECIMAL_PLACE.format(Collections.min(powerList));
    }
    else if (statistic.equalsIgnoreCase("average")) {
      result = ONE_DECIMAL_PLACE.format(totalPower / powerList.size());
    }

    if ("0.0".equals(result)) {
      buffer.append("No data for ");
      buffer.append(command.get(2));
      buffer.append(" on ");
      buffer.append(command.get(4));
      buffer.append('.');
    }
    else {
      buffer.append(result);
      buffer.append(" W");
    }
    return buffer.toString();
  }

}