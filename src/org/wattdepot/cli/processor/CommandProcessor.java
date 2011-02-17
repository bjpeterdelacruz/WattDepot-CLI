package org.wattdepot.cli.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import org.wattdepot.cli.CommandLineInterface;
import org.wattdepot.cli.command.CarbonContent;
import org.wattdepot.cli.command.ChartPower;
import org.wattdepot.cli.command.CliCommandInterface;
import org.wattdepot.cli.command.FuelTypes;
import org.wattdepot.cli.command.Help;
import org.wattdepot.cli.command.PowerStatsDay;
import org.wattdepot.cli.command.PowerTimestamp;
import org.wattdepot.cli.command.Quit;
import org.wattdepot.cli.command.SensordataDay;
import org.wattdepot.cli.command.SensordataTimestamp;
import org.wattdepot.cli.command.Summary;
import org.wattdepot.cli.command.Sources;
import org.wattdepot.cli.command.TotalCarbonOrEnergy;
import org.wattdepot.cli.command.TotalPowerFuelType;

/**
 * Parses a command inputted by the user on the command prompt and then calls a method to carry out
 * that command.
 * 
 * @author BJ Peter DeLaCruz, Wahib Hanani, Lyneth Peou
 * @version 2.0
 */
public class CommandProcessor {

  /** Contains commands. */
  protected Map<String, CliCommandInterface> mapCommands;

  /**
   * Creates an object that can be used to call a method to carry out the command inputted by the
   * user on the command prompt.
   * 
   * @throws Exception If a problem occurs.
   */
  public CommandProcessor() throws Exception {
    mapCommands = new HashMap<String, CliCommandInterface>();

    mapCommands.put("quit", new Quit());
    mapCommands.put("help", new Help());
    mapCommands.put("sources", new Sources());
    mapCommands.put("summary", new Summary());
    mapCommands.put("fueltypes", new FuelTypes());
    mapCommands.put("dailysensordata", new SensordataDay());
    mapCommands.put("sensordata timestamp", new SensordataTimestamp());
    mapCommands.put("power timestamp", new PowerTimestamp());
    mapCommands.put("total carbon", new TotalCarbonOrEnergy());
    mapCommands.put("powerstats", new PowerStatsDay());
    mapCommands.put("chart", new ChartPower());
    mapCommands.put("totalpower", new TotalPowerFuelType());
    mapCommands.put("carboncontent", new CarbonContent());
  }

  /**
   * Calls a method to carry out the command inputted by the user on the command prompt.
   * 
   * @param cli A CommandLineInterface object.
   * @param commandList Command inputted by the user on the command prompt.
   * @return Output from one of the commands.
   */
  public String processCommand(CommandLineInterface cli, List<String> commandList) {
    String samplingInterval = "sampling-interval";
    String result = "";
    String day = "day";
    String timestamp = "timestamp";
    if (commandList.isEmpty()) {
      result =
          "Please enter a command or type help for information about the commands"
              + " that are available.";
      if (cli.debuggingMode) {
        cli.errorEncountered = true;
      } // end if
    } // end if
    else if (commandList.size() == 1 && commandList.get(0).equalsIgnoreCase("quit")) {
      result = mapCommands.get("quit").doCommand(cli, commandList);
    } // end else if
    else if (commandList.size() == 1 && commandList.get(0).equalsIgnoreCase("help")) {
      result = mapCommands.get("help").doCommand(cli, commandList);
    } // end else if
    else if (commandList.size() == 1 && commandList.get(0).equalsIgnoreCase("sources")) {
      result = mapCommands.get("sources").doCommand(cli, commandList);
    } // end else if
    else if (commandList.size() == 2 && commandList.get(0).equalsIgnoreCase("summary")) {
      result = mapCommands.get("summary").doCommand(cli, commandList);
    } // end else if
    else if (commandList.size() == 2 && commandList.get(0).equalsIgnoreCase("fueltypes")) {
      result = mapCommands.get("fueltypes").doCommand(cli, commandList);
    } // end else if
    else if (commandList.size() == 4 && commandList.get(0).equalsIgnoreCase("dailysensordata")
        && commandList.get(2).equalsIgnoreCase(day)) {
      result = mapCommands.get("dailysensordata").doCommand(cli, commandList);
    } // end else if
    else if (commandList.size() == 4 && commandList.get(0).equalsIgnoreCase("sensordata")
        && commandList.get(2).equalsIgnoreCase(timestamp)) {
      result = mapCommands.get("sensordata timestamp").doCommand(cli, commandList);
    } // end else if
    else if (commandList.size() == 5 && commandList.get(0).equalsIgnoreCase("power")
        && commandList.get(3).equalsIgnoreCase("timestamp")) {
      result = mapCommands.get("power timestamp").doCommand(cli, commandList);
    } // end else if
    else if (commandList.size() == 8
        && commandList.get(0).equalsIgnoreCase("total")
        && (commandList.get(1).equalsIgnoreCase("carbon") || commandList.get(1).equalsIgnoreCase(
            "energy")) && commandList.get(4).equalsIgnoreCase("day")
        && commandList.get(6).equalsIgnoreCase(samplingInterval)) {
      result = mapCommands.get("total carbon").doCommand(cli, commandList);
    } // end else if
    else if (commandList.size() == 9 && commandList.get(0).equalsIgnoreCase("powerstats")
        && commandList.get(3).equalsIgnoreCase(day)
        && commandList.get(5).equalsIgnoreCase(samplingInterval)
        && commandList.get(7).equalsIgnoreCase("statistic")) {
      result = mapCommands.get("powerstats").doCommand(cli, commandList);
    } // end else if
    else if (commandList.size() == 10 && commandList.get(0).equalsIgnoreCase("chart")
        && commandList.get(1).equalsIgnoreCase("power")
        && commandList.get(6).equalsIgnoreCase(samplingInterval)
        && commandList.get(8).equalsIgnoreCase("file")) {
      result = mapCommands.get("chart").doCommand(cli, commandList);
    } // end else if
    else if (commandList.size() == 5 && commandList.get(0).equalsIgnoreCase("totalpower")
        && commandList.get(3).equalsIgnoreCase("fueltype")) {
      result = mapCommands.get("totalpower").doCommand(cli, commandList);
    } // end else if
    else if (commandList.size() == 5 && commandList.get(0).equalsIgnoreCase("carboncontent")
        && commandList.get(3).equalsIgnoreCase(samplingInterval)) {
      result = mapCommands.get("carboncontent").doCommand(cli, commandList);
    } // end else if
    else {
      result = "Invalid command. Type help for information about the commands that are available.";
      if (cli.debuggingMode) {
        cli.errorEncountered = true;
      } // end if
    } // end else if

    return result;
  }

  /**
   * Parses the string inputted by the user on the command prompt.
   * 
   * @param command Command inputted by the user on the command prompt.
   * @return An ArrayList containing the command name and parameters.
   */
  public List<String> parseCommand(String command) {
    List<String> commandList = new ArrayList<String>();
    StringTokenizer st = new StringTokenizer(command);
    while (st.hasMoreTokens()) {
      commandList.add(st.nextToken());
    }
    return commandList;
  }

}