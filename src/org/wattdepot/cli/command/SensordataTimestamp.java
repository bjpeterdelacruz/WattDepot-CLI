package org.wattdepot.cli.command;

import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;
import org.wattdepot.resource.property.jaxb.Property;
import org.wattdepot.resource.sensordata.jaxb.SensorData;
import org.wattdepot.util.tstamp.Tstamp;
import org.wattdepot.cli.CommandLineInterface;
import org.wattdepot.client.ResourceNotFoundException;

/**
 * Prints information about a single sensor data instance at a given timestamp.
 * 
 * @author BJ Peter DeLaCruz, Wahib Hanani, Lyneth Peou
 * @version 2.0
 */
public class SensordataTimestamp extends CliCommandInterface {

  /**
   * Carries out the command inputted by the user on the command prompt.
   * 
   * @param cli A CommandLineInterface object.
   * @param commandList Command inputted by the user on the command prompt.
   * @return Result after running the command.
   */
  public String doCommand(CommandLineInterface cli, List<String> commandList) {
    return listSensordataTimestamp(cli, commandList);
  }

  /**
   * Prints information about a single sensor data instance at a given timestamp.
   * 
   * @param cli A CommandLineInterface object.
   * @param command Command inputted by the user on the command prompt.
   * @return Information about a single sensor data instance.
   */
  private final String listSensordataTimestamp(CommandLineInterface cli, List<String> command) {
    List<SensorData> sensorData = null;

    XMLGregorianCalendar timestamp = null;
    try {
      timestamp = Tstamp.makeTimestamp(command.get(3));
    }
    catch (Exception e) {
      if (cli.debuggingMode) {
        cli.errorEncountered = true;
      } // end if
      return ERROR_MESSAGE + STRING_CONVERSION_ERROR;
    } // end catch

    try {
      sensorData = cli.CLIENT.getSensorDatas(command.get(1), timestamp, timestamp);
    }
    catch (ResourceNotFoundException rnfe) {
      if (cli.debuggingMode) {
        cli.errorEncountered = true;
      } // end if
      return ERROR_MESSAGE + "Power source not found.";
    } // end catch
    catch (Exception e) {
      cli.errorEncountered = true;
      return ERROR_MESSAGE + CARRIAGE_RETURN + e.toString() + CARRIAGE_RETURN + EXIT_MESSAGE;
    }

    StringBuffer buffer = new StringBuffer(255);
    if (sensorData.isEmpty()) {
      buffer.append("No data for ");
      buffer.append(command.get(1));
      buffer.append(" on ");
      buffer.append(command.get(3));
      buffer.append('.');
    }
    else {
      buffer.append("Tool: " + sensorData.get(0).getTool() + CARRIAGE_RETURN + "Source: "
          + sensorData.get(0).getSource() + CARRIAGE_RETURN);
      buffer.append("Properties: (");
      for (Property property : sensorData.get(0).getProperties().getProperty()) {
        buffer.append(property.getKey());
        buffer.append(" : ");
        // convert to megawatts
        buffer.append((Double.parseDouble(property.getValue()) / 1000000.0));
        buffer.append(" MW , ");
      } // end for
      String temp = buffer.toString().substring(0, buffer.toString().length() - 3) + ")";
      buffer.setLength(0);
      buffer.append(temp);
    } // end else

    return buffer.toString();
  }

}