package org.wattdepot.cli.command;

import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;
import org.wattdepot.cli.CommandLineInterface;
import org.wattdepot.client.ResourceNotFoundException;
import org.wattdepot.client.WattDepotClientException;
import org.wattdepot.util.tstamp.Tstamp;

/**
 * Prints the power generated or consumed by a power source at a given timestamp. The power value is
 * interpolated if there is no corresponding sensor data instance.
 * 
 * @author BJ Peter DeLaCruz, Wahib Hanani, Lyneth Peou
 * @version 2.0
 */
public class PowerTimestamp extends CliCommandInterface {

  /**
   * Carries out the command inputted by the user on the command prompt.
   * 
   * @param cli A CommandLineInterface object.
   * @param commandList Command inputted by the user on the command prompt.
   * @return Result after running the command.
   */
  public String doCommand(CommandLineInterface cli, List<String> commandList) {
    return listPowerTimestamp(cli, commandList);
  }

  /**
   * Prints the power generated or consumed by a power source at a given timestamp. The power value
   * is interpolated if there is no corresponding sensor data instance.
   * 
   * @param cli A CommandLineInterface object.
   * @param command Command inputted by the user on the command prompt.
   * @return Information about the power generated or consumed by a power source.
   */
  private final String listPowerTimestamp(CommandLineInterface cli, List<String> command) {
    XMLGregorianCalendar now = null;
    try {
      now = Tstamp.makeTimestamp(command.get(4));
    }
    catch (Exception e) {
      if (cli.debuggingMode) {
        cli.errorEncountered = true;
      } // end if
      return ERROR_MESSAGE + STRING_CONVERSION_ERROR;
    } // end catch

    try {
      cli.CLIENT.getSource(command.get(2));
    }
    catch (ResourceNotFoundException rnfe) {
      if (cli.debuggingMode) {
        cli.errorEncountered = true;
      } // end if
      return ERROR_MESSAGE + "Power source not found.";
    } // end catch
    // quit the program if a serious runtime exception is caught
    catch (Exception e) {
      cli.errorEncountered = true;
      return ERROR_MESSAGE + CARRIAGE_RETURN + e.toString() + CARRIAGE_RETURN + EXIT_MESSAGE;
    }

    String powerSource = command.get(2);
    StringBuffer buffer = new StringBuffer(255);
    String result = "0.0";
    try {
      if (command.get(1).equalsIgnoreCase(GENERATED_COMMAND)) {
        result = ONE_DECIMAL_PLACE.format(cli.CLIENT.getPowerGenerated(powerSource, now));
      } // end if
      else if (command.get(1).equalsIgnoreCase(CONSUMED_COMMAND)) {
        result = ONE_DECIMAL_PLACE.format(cli.CLIENT.getPowerConsumed(powerSource, now));
      } // end else if
      else {
        buffer.append(ERROR_MESSAGE);
        buffer.append("Invalid command. Please try again.");
        return buffer.toString();
      } // end else

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
    } // end try
    catch (WattDepotClientException wdce) {
      cli.errorEncountered = true;
      return ERROR_MESSAGE + CARRIAGE_RETURN + wdce.toString() + CARRIAGE_RETURN + EXIT_MESSAGE;
    }

    return buffer.toString();
  }

}