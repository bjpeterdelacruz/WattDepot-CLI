package org.wattdepot.cli.command;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;
import org.wattdepot.cli.CommandLineInterface;
import org.wattdepot.util.tstamp.Tstamp;

/**
 * Creates an HTML file that will show a chart representation of the power generated or consumed by
 * a given power source from startday to endday. The data points are generated at a sampling
 * interval of minutes.
 * 
 * @author BJ Peter DeLaCruz, Wahib Hanani, Lyneth Peou
 * @version 2.0
 */
public class ChartPower extends CliCommandInterface {

  /**
   * Carries out the command inputted by the user on the command prompt.
   * 
   * @param cli A CommandLineInterface object.
   * @param commandList Command inputted by the user on the command prompt.
   * @return Result after running the command.
   */
  public String doCommand(CommandLineInterface cli, List<String> commandList) {
    return getChartData(cli, commandList);
  }

  /**
   * Creates an HTML file that will show a chart representation of the power generated or consumed
   * by a given power source from startday to endday. The data points are generated at a sampling
   * interval of minutes.
   * 
   * @param cli A CommandLineInterface object.
   * @param command Command inputted by the user on the command prompt.
   * @return Success or failure message.
   */
  private final String getChartData(CommandLineInterface cli, List<String> command) {
    XMLGregorianCalendar startTime = null;
    XMLGregorianCalendar endTime = null;

    try {
      startTime = Tstamp.makeTimestamp(command.get(4));
      endTime = Tstamp.makeTimestamp(command.get(5));
    }
    catch (Exception e) {
      if (cli.debuggingMode) {
        cli.errorEncountered = true;
      } // end if
      return ERROR_MESSAGE + STRING_CONVERSION_ERROR;
    } // end catch

    if (endTime.compare(startTime) <= 0) {
      if (cli.debuggingMode) {
        cli.errorEncountered = true;
      } // end if
      return ERROR_MESSAGE + CARRIAGE_RETURN
          + "End day is less than or equal to start day. Please try again.";
    } // end if

    String powerCommand = command.get(2);
    if (!powerCommand.equalsIgnoreCase(GENERATED_COMMAND)
        && !powerCommand.equalsIgnoreCase(CONSUMED_COMMAND)) {
      if (cli.debuggingMode) {
        cli.errorEncountered = true;
      } // end if
      return ERROR_MESSAGE + CARRIAGE_RETURN
          + "Type either generated or consumed (e.g., chart power generated).";
    } // end if

    String powerSource = command.get(3);
    String fileName = command.get(9);

    StringBuffer buffer = new StringBuffer(255);
    String fileInfo = "";

    buffer.append("http://chart.apis.google.com/chart?chs=250x100&cht=lc&chd=t:");

    Integer interval = null;
    try {
      interval = Integer.parseInt(command.get(7));
    }
    catch (NumberFormatException nfe) {
      if (cli.debuggingMode) {
        cli.errorEncountered = true;
      } // end if
      return ERROR_MESSAGE + CARRIAGE_RETURN
          + "Number for sampling interval is in wrong format. Please try again.";
    } // end catch
    double power = 0.0;
    double minPower = Double.MAX_VALUE;
    double maxPower = Double.MIN_VALUE;

    try {
      while (Tstamp.diff(startTime, endTime) > 1) {
        if (powerCommand.equalsIgnoreCase(GENERATED_COMMAND)) {
          power = cli.CLIENT.getPowerGenerated(powerSource, startTime) / 1000000.0;
        } // end if
        else if (powerCommand.equalsIgnoreCase(CONSUMED_COMMAND)) {
          power = cli.CLIENT.getPowerConsumed(powerSource, startTime) / 1000000.0;
        } // end else if
        buffer.append(String.format("%.1f,", power));
        if (power < minPower) {
          minPower = power;
        } // end if
        if (power > maxPower) {
          maxPower = power;
        } // end if
        startTime = Tstamp.incrementMinutes(startTime, interval);
      } // end while
    } // end try
    catch (Exception e) {
      cli.errorEncountered = true;
      return ERROR_MESSAGE + CARRIAGE_RETURN + e.toString() + CARRIAGE_RETURN + EXIT_MESSAGE;
    }

    buffer.deleteCharAt(buffer.length() - 1);

    buffer.append(String.format("&chds=%.1f,%.1f&chxt=y&chxr=0,%.1f,%.1f", minPower, maxPower,
        minPower, maxPower));

    // create HTML file
    fileInfo =
        "<HTML>" + CARRIAGE_RETURN + "<HEAD></HEAD>" + CARRIAGE_RETURN + "<BODY>" + CARRIAGE_RETURN
            + "<H2 STYLE=\"font-family: Arial\">Power " + command.get(2) + " for " + command.get(3)
            + " from " + command.get(4) + " to " + command.get(5) + "</H2>" + CARRIAGE_RETURN
            + "<IMG SRC=" + buffer.toString() + ">" + CARRIAGE_RETURN + "</BODY>" + CARRIAGE_RETURN
            + "</HTML>";

    if (!getFileHTML(fileName, fileInfo)) {
      cli.errorEncountered = true;
      return "Error encountered when trying to write " + fileName + " to disk." + CARRIAGE_RETURN
          + EXIT_MESSAGE;
    }

    return "Chart data was successfully written to " + fileName + ".";
  }

  /**
   * Creates an HTML file containing chart data.
   * 
   * @param fileName Output file name.
   * @param fileInfo Power chart data.
   * 
   * @return True if HTML file was created successfully, false otherwise.
   */
  private final Boolean getFileHTML(String fileName, String fileInfo) {

    if (!fileName.substring(fileName.length() - 4).equalsIgnoreCase("HTML")) {
      return false;
    }

    FileOutputStream fos = null;
    try {
      fos = new FileOutputStream(fileName);
    }
    catch (FileNotFoundException fnfe) {
      return false;
    }

    BufferedOutputStream bos = new BufferedOutputStream(fos);
    try {
      bos.write(fileInfo.getBytes());
      bos.flush();
      bos.close();
      fos.close();
    }
    catch (IOException ie) {
      return false;
    }
    return true;
  }

}