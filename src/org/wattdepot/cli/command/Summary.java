package org.wattdepot.cli.command;

import java.util.List;
import org.wattdepot.cli.CommandLineInterface;
import org.wattdepot.cli.processor.CliSource;
import org.wattdepot.client.WattDepotClientException;
import org.wattdepot.resource.property.jaxb.Property;
import org.wattdepot.resource.source.jaxb.Source;
import org.wattdepot.resource.source.summary.jaxb.SourceSummary;

/**
 * Lists information about a public power source that is available on this server.
 * 
 * @author BJ Peter DeLaCruz, Wahib Hanani, Lyneth Peou
 * @version 2.0
 */
public class Summary extends CliCommandInterface {

  /**
   * Carries out the command inputted by the user on the command prompt.
   * 
   * @param cli A CommandLineInterface object.
   * @param commandList Command inputted by the user on the command prompt.
   * @return Result after running the command.
   */
  public String doCommand(CommandLineInterface cli, List<String> commandList) {
    return listSummary(cli, commandList.get(1));
  }

  /**
   * Lists information about a public power source that is available on this server.
   * 
   * @param cli A CommandLineInterface object.
   * @param sourceName Name of a public power source.
   * @return Information about a public power source.
   */
  private final String listSummary(CommandLineInterface cli, String sourceName) {

    Source source = (new CliSource()).getSource(cli, sourceName);

    if (cli.errorEncountered || source == null) {
      if (cli.debuggingMode) {
        cli.errorEncountered = true;
      } // end if
      return "Error encountered when processing name of power source.";
    } // end if

    String format = "  %1$-20s %2$-100s" + CARRIAGE_RETURN;
    StringBuffer buffer = new StringBuffer(2500);

    if (source.isSetSubSources()) {
      String subsources = "";
      for (String href : source.getSubSources().getHref()) {
        buffer.append(href.substring(href.indexOf("SIM")));
        buffer.append(", ");
      } // end for
      subsources = buffer.toString().substring(0, buffer.toString().length() - 2);
      buffer.setLength(0);
      buffer.append(String.format(format, "SubSources: ", subsources));
    } // end if
    else {
      buffer.append(String.format(format, "SubSources: ", "[ None ]"));
    }

    buffer.append(String.format(format, "Description: ", source.getDescription()));
    buffer.append(String.format(format, "Owner: ", source.getOwner()));
    buffer.append(String.format(format, "Location: ", source.getLocation()));
    buffer.append(String.format(format, "Coordinates: ", source.getCoordinates()));

    StringBuffer tempBuffer = new StringBuffer(255);
    String properties = "";
    if (source.isSetProperties()) {
      for (Property property : source.getProperties().getProperty()) {
        tempBuffer.append('(');
        tempBuffer.append(property.getKey());
        tempBuffer.append(" : ");
        tempBuffer.append(property.getValue());
        tempBuffer.append("), ");
      } // end for
      properties = tempBuffer.toString().substring(0, tempBuffer.toString().length() - 2);
    } // end if
    else {
      properties = "None";
    }
    buffer.append(String.format(format, "Properties: ", properties));

    SourceSummary sourceSummary = null;
    try {
      sourceSummary = cli.CLIENT.getSourceSummary(sourceName);
    }
    catch (WattDepotClientException wdce) {
      cli.errorEncountered = true;
      return ERROR_MESSAGE + CARRIAGE_RETURN + wdce.toString() + CARRIAGE_RETURN + EXIT_MESSAGE;
    }

    buffer.append(String.format(format, "Earliest data: ", sourceSummary.getFirstSensorData()
        .toString()));
    buffer.append(String.format(format, "Latest data: ", sourceSummary.getLastSensorData()
        .toString()));
    buffer
        .append(String.format(format, "Total data points: ", sourceSummary.getTotalSensorDatas()));

    return buffer.toString();
  }

}