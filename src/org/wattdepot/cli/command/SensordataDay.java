package org.wattdepot.cli.command;

import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;
import org.wattdepot.cli.CommandLineInterface;
import org.wattdepot.client.ResourceNotFoundException;
import org.wattdepot.resource.property.jaxb.Property;
import org.wattdepot.resource.sensordata.jaxb.SensorData;
import org.wattdepot.util.tstamp.Tstamp;

/**
 * Prints information about a single sensor data instance over a period of an entire day.
 * 
 * @author BJ Peter DeLaCruz, Wahib Hanani, Lyneth Peou
 * @version 2.0
 */
public class SensordataDay extends CliCommandInterface {

  /**
   * Carries out the command inputted by the user on the command prompt.
   * 
   * @param cli A CommandLineInterface object.
   * @param commandList Command inputted by the user on the command prompt.
   * @return Result after running the command.
   */
  public String doCommand(CommandLineInterface cli, List<String> commandList) {
    return listSensordataDay(cli, commandList);
  }

  /**
   * Prints information about a single sensor data instance over a period of an entire day.
   * 
   * @param cli A CommandLineInterface object.
   * @param command Command inputted by the user on the command prompt.
   * @return Information about a single sensor data instance.
   */
  private final String listSensordataDay(CommandLineInterface cli, List<String> command) {
    if (command.get(3).contains("T")) {
      if (cli.debuggingMode) {
        cli.errorEncountered = true;
      } // end if
      String temp = ERROR_MESSAGE + CARRIAGE_RETURN;
      temp += "Invalid string format for day. Please enter a valid string that does not";
      return temp + " contain the time (e.g., 2009-11-01).";
    } // end if

    List<SensorData> sensorDataList = null;
    XMLGregorianCalendar startTime = null;
    XMLGregorianCalendar endTime = null;
    try {
      startTime = Tstamp.makeTimestamp(command.get(3));
      endTime = Tstamp.makeTimestamp(command.get(3));
    }
    catch (Exception e) {
      if (cli.debuggingMode) {
        cli.errorEncountered = true;
      } // end if
      return ERROR_MESSAGE + STRING_CONVERSION_ERROR;
    } // end catch
    endTime = Tstamp.incrementDays(endTime, 1);

    try {
      sensorDataList = cli.CLIENT.getSensorDatas(command.get(1), startTime, endTime);
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

    StringBuffer result = new StringBuffer(2500);
    if (sensorDataList.isEmpty()) {
      result.append("No data for ");
      result.append(command.get(1));
      result.append(" on ");
      result.append(command.get(3));
      result.append('.');
    }
    else {
      sensorDataList.remove(sensorDataList.size() - 1);
      StringBuffer buffer = new StringBuffer(2500);
      for (SensorData sensorData : sensorDataList) {
        buffer.append(sensorData.getTimestamp());
        buffer.append("\tTool: ");
        buffer.append(sensorData.getTool());
        buffer.append("\tProperties: (");

        for (Property property : sensorData.getProperties().getProperty()) {
          buffer.append(property.getKey());
          buffer.append(" : ");
          // convert to megawatts
          buffer.append((Double.parseDouble(property.getValue()) / 1000000.0));
          buffer.append(" MW , ");
        } // end for

        result.append(buffer.toString().substring(0, buffer.toString().length() - 3) + ")");
        result.append(CARRIAGE_RETURN);
        buffer.setLength(0);
      } // end for
    } // end else

    return result.toString();
  }

}