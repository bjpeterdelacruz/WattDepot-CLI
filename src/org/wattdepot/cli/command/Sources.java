package org.wattdepot.cli.command;

import java.util.ArrayList;
import java.util.List;
import org.wattdepot.cli.CommandLineInterface;
import org.wattdepot.client.WattDepotClientException;
import org.wattdepot.resource.source.jaxb.Source;

/**
 * Lists information about all of the public power sources that are available on this server.
 * 
 * @author BJ Peter DeLaCruz, Wahib Hanani, Lyneth Peou
 * @version 2.0
 */
public class Sources extends CliCommandInterface {
  /** Contains a list of power sources, their parents, and the power sources' descriptions. */
  private List<String> sourceInformationList = new ArrayList<String>();

  /**
   * Carries out the command inputted by the user on the command prompt.
   * 
   * @param cli A CommandLineInterface object.
   * @param commandList Command inputted by the user on the command prompt.
   * @return Result after running the command.
   */
  public String doCommand(CommandLineInterface cli, List<String> commandList) {
    return listSources(cli);
  }

  /**
   * Lists information about all of the public power sources that are available on this server.
   * 
   * @param cli A CommandLineInterface object.
   * @return Information about all of the public power sources.
   */
  private final String listSources(CommandLineInterface cli) {
    String[] info = new String[2];
    List<Source> originalList = null;
    try {
      originalList = cli.CLIENT.getSources();
    }
    catch (WattDepotClientException wdce) {
      cli.errorEncountered = true;
      return ERROR_MESSAGE + CARRIAGE_RETURN + wdce.toString() + CARRIAGE_RETURN + EXIT_MESSAGE;
    }
    List<Source> parentList = new ArrayList<Source>();

    for (Source source : originalList) {
      if (source.getSubSources() != null) {
        parentList.add(source);
      } // end if
    } // end for

    String format = "| %1$-20s| %2$-20s| %3$-50s" + CARRIAGE_RETURN;
    StringBuffer buffer = new StringBuffer(2500);
    buffer.append(String.format(format, "Name", "Parent", "Description"));
    buffer.append(String.format(format, "", "", ""));

    String temp = "";
    for (Source child : originalList) {
      info[0] = child.getName();
      if (info[0].equalsIgnoreCase("SIM_OAHU_GRID")) {
        temp = String.format(format, info[0], "[ None ]", child.getDescription());
        buffer.append(temp);
        sourceInformationList.add(temp);
      } // end if
      else if (info[0].equalsIgnoreCase("SIM_WAIAU")) {
        temp = String.format(format, info[0], "SIM_OAHU_GRID", child.getDescription());
        buffer.append(temp);
        sourceInformationList.add(temp);
      } // end else if
      else {
        for (Source parent : parentList) {
          if (parent.getSubSources().toString().matches(".*" + child.getName() + ".*")) {
            info[1] = parent.getName();
          } // end if
        } // end for
        temp = String.format(format, info[0], info[1], child.getDescription());
        buffer.append(temp);
        sourceInformationList.add(temp);
      } // end else
    } // end for

    return buffer.toString();
  }

  /**
   * Returns a list of power sources, their parents, and the power sources' descriptions.
   * 
   * @return A list of power sources, their parents, and the power sources' descriptions.
   */
  public List<String> getSourceInformationList() {
    return sourceInformationList;
  }

}