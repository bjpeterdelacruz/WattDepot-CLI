package org.wattdepot.cli.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.wattdepot.cli.CommandLineInterface;
import org.wattdepot.cli.processor.CommandProcessor;

/**
 * Tests the totalPowerFuelType command to see if the timestamp string is valid, the total power of
 * a valid power source exists, an invalid fuel type returns null, and a power source exists.
 * 
 * @author BJ Peter DeLaCruz, Wahib Hanani, Lyneth Peou
 * @version 2.0
 */
public class TestTotalPowerFuelType extends CliCommandTest {
  private CommandProcessor commandProcessor;
  private List<String> commandList;
  private CommandLineInterface cli;

  /**
   * Tests the totalPower method to check if the timestamp string is valid.
   * 
   * @throws Exception If a problem occurs.
   */
  @Before
  @Test
  public void testTotalPowerTimestamp() throws Exception {
    String command = "totalpower SIM_IPP 2009-10-32T18:15:00.000-10:00 fueltype waste";
    commandProcessor = new CommandProcessor();
    commandList = commandProcessor.parseCommand(command);
    cli = new CommandLineInterface();
    commandProcessor.processCommand(cli, commandList);
    assertTrue(ASSERT_TIMESTAMP_MESSAGE, cli.errorEncountered);
  }

  /**
   * Tests the totalPower method to check if the total power of a valid power source exists.
   * 
   * @throws Exception If a problem occurs.
   */
  @Test
  public void testTotalPowerValidResult() throws Exception {
    String command = "totalpower SIM_OAHU_GRID 2009-10-28T18:15:00.000-10:00 fueltype waste";
    commandProcessor = new CommandProcessor();
    commandList = commandProcessor.parseCommand(command);

    cli = new CommandLineInterface();
    commandProcessor.processCommand(cli, commandList);
    assertFalse(ASSERT_TIMESTAMP_MESSAGE, cli.errorEncountered);
  }

  /**
   * Tests the totalPower method to check if an invalid fuel type returns null.
   * 
   * @throws Exception If a problem occurs.
   */
  @Test
  public void testTotalPowerInvalidFuelType() throws Exception {
    String command = "totalpower SIM_OAHU_GRID 2009-10-28T18:15:00.000-10:00 fueltype wa";
    commandProcessor = new CommandProcessor();
    commandList = commandProcessor.parseCommand(command);

    cli = new CommandLineInterface();
    commandProcessor.processCommand(cli, commandList);
    String result = (new TotalPowerFuelType().doCommand(cli, commandList));
    double total = Double.parseDouble(result.substring(0, result.indexOf(' ')));
    assertEquals("Assert Fuel Type: ", Double.compare(0.0, total), 0);
  }

  /**
   * Tests the totalPower method to check if a power source exists.
   * 
   * @throws Exception If a problem occurs.
   */
  @Test
  public void testTotalPowerInvalidSource() throws Exception {
    String command = "totalpower SIMOAHU_GRID 2009-10-28T18:15:00.000-10:00 fueltype waste";
    commandProcessor = new CommandProcessor();
    commandList = commandProcessor.parseCommand(command);

    cli = new CommandLineInterface();
    commandProcessor.processCommand(cli, commandList);
    assertTrue(ASSERT_SOURCE_MESSAGE, cli.errorEncountered);
  }

  /**
   * Tests the totalPower method to check all arguments wae inputted.
   * 
   * @throws Exception If a problem occurs.
   */
  @Test
  public void testTotalPowerNotEnoughArgument() throws Exception {
    String command = "totalpower SIMOAHU_GRID 2009-10-28T18:15:00.000-10:00";
    commandProcessor = new CommandProcessor();
    commandList = commandProcessor.parseCommand(command);

    cli = new CommandLineInterface();
    commandProcessor.processCommand(cli, commandList);
    assertTrue(ASSERT_SOURCE_MESSAGE, cli.errorEncountered);
  }

}