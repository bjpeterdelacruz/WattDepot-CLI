package org.wattdepot.cli.command;

import java.text.DecimalFormat;
import java.util.List;
import org.wattdepot.cli.CommandLineInterface;

/**
 * Provides a prototype for the method that will be called by objects stored in a HashMap.
 * 
 * @author BJ Peter DeLaCruz, Wahib Hanani, Lyneth Peou
 * @version 2.0
 */
public abstract class CliCommandInterface {
  /** Carriage return on this operating system. */
  public static final String CARRIAGE_RETURN = System.getProperty("line.separator");
  /** Error message. */
  public static final String ERROR_MESSAGE = "The following error was encountered: ";
  /** Exit message. */
  public static final String EXIT_MESSAGE = "Program will now exit.";
  /** String conversion error message. */
  public static final String STRING_CONVERSION_ERROR =
      "String cannot be converted to timestamp. Please try again.";
  /** Power generated command option. */
  public static final String GENERATED_COMMAND = "generated";
  /** Power consumed command option. */
  public static final String CONSUMED_COMMAND = "consumed";
  /** Convert scientific notation to regular number format. */
  public static final DecimalFormat ONE_DECIMAL_PLACE = new DecimalFormat("0.0");

  /**
   * Carries out the command inputted by the user on the command prompt.
   * 
   * @param cli A CommandLineInterface object.
   * @param commandList Command inputted by the user on the command prompt.
   * @return Result after running the command.
   */
  public abstract String doCommand(CommandLineInterface cli, List<String> commandList);

}