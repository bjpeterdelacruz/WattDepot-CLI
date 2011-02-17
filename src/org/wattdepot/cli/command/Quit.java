package org.wattdepot.cli.command;

import java.util.List;
import org.wattdepot.cli.CommandLineInterface;

/**
 * Quits the system.
 * 
 * @author BJ Peter DeLaCruz, Wahib Hanani, Lyneth Peou
 * @version 2.0
 */
public class Quit extends CliCommandInterface {

  /**
   * Carries out the command inputted by the user on the command prompt.
   * 
   * @param cli A CommandLineInterface object.
   * @param commandList Command inputted by the user on the command prompt.
   * @return Result after running the command.
   */
  public String doCommand(CommandLineInterface cli, List<String> commandList) {
    cli.exit = true;
    return "";
  }

}