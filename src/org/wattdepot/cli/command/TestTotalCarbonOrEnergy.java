package org.wattdepot.cli.command;

import static org.junit.Assert.assertTrue;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.wattdepot.cli.CommandLineInterface;
import org.wattdepot.cli.processor.CommandProcessor;

/**
 * Tests the listTotal method to see if power source exists on this server, day and sampling-
 * interval strings are valid, and carbon and energy data exist.
 * 
 * @author BJ Peter DeLaCruz, Wahib Hanani, Lyneth Peou
 * @version 2.0
 */
public class TestTotalCarbonOrEnergy extends CliCommandTest {
  private CommandProcessor commandProcessor;
  private List<String> commandList;
  private CommandLineInterface cli;
  private String result;

  /**
   * Tests the listTotal method to see if power source exists on this server.
   * 
   * @throws Exception If a problem occurs.
   */
  @Before
  @Test
  public void testTotalCarbonOrEnergySource() throws Exception {
    commandProcessor = new CommandProcessor();
    if (verboseMode) {
      System.out.println("Command: total carbon generated SIM_SEA day 2009-11-01 "
          + "sampling-interval 120" + CARRIAGE_RETURN);
      System.out.println(CHECKING_SOURCE_MESSAGE);
    }
    commandList =
        commandProcessor.parseCommand("total carbon generated SIM_SEA day 2009-11-01"
            + " sampling-interval 120");
    cli = new CommandLineInterface();
    String result = (new TotalCarbonOrEnergy()).doCommand(cli, commandList);
    if (verboseMode) {
      System.out.println(result);
    }
    assertTrue(ASSERT_SOURCE_MESSAGE, cli.errorEncountered);
    if (verboseMode) {
      System.out.println(OK_MESSAGE + CARRIAGE_RETURN);
    }
  }

  /**
   * Tests the listTotal method to see if day string is valid.
   * 
   * @throws Exception If a problem occurs.
   */
  @Test
  public void testTotalCarbonOrEnergyDay() throws Exception {
    if (verboseMode) {
      System.out.println("Command: total carbon generated SIM_KAHE_1 day 11-01-2009 "
          + "sampling-interval 60" + CARRIAGE_RETURN);
      System.out.println("Checking if day is not a valid string...");
    }
    commandList =
        commandProcessor.parseCommand("total carbon generated SIM_KAHE_1 day 11-01-2009"
            + " sampling-interval 60");
    cli.errorEncountered = false;
    result = (new TotalCarbonOrEnergy()).doCommand(cli, commandList);
    if (verboseMode) {
      System.out.println(result);
    }
    assertTrue(ASSERT_DAY_MESSAGE, cli.errorEncountered);
    if (verboseMode) {
      System.out.println(OK_MESSAGE + CARRIAGE_RETURN);
    }
  }

  /**
   * Tests the listTotal method to see if sampling-interval string is valid.
   * 
   * @throws Exception If a problem occurs.
   */
  @Test
  public void testTotalCarbonOrEnergySamplingInterval() throws Exception {
    if (verboseMode) {
      System.out.println("Command: total carbon generated SIM_KAHE_1 day 2009-11-01 "
          + "sampling-interval lol" + CARRIAGE_RETURN);
      System.out.println("Checking if sampling-interval is not a valid number...");
    }
    commandList =
        commandProcessor.parseCommand("total carbon generated SIM_KAHE_1 day 2009-11-01"
            + " sampling-interval lol");
    cli.errorEncountered = false;
    result = (new TotalCarbonOrEnergy()).doCommand(cli, commandList);
    if (verboseMode) {
      System.out.println(result);
    }
    assertTrue(ASSERT_STRING_MESSAGE, cli.errorEncountered);
    if (verboseMode) {
      System.out.println(OK_MESSAGE + CARRIAGE_RETURN);
    }
  }

  /**
   * Tests the listTotal method to see if carbon data exists.
   * 
   * @throws Exception If a problem occurs.
   */
  @Test
  public void testTotalCarbonOrEnergyCarbonGenerated() throws Exception {
    if (verboseMode) {
      System.out.println("Command: total carbon generated SIM_OAHU_GRID day 2009-11-01 "
          + "sampling-interval 120" + CARRIAGE_RETURN);
      System.out.println("Checking if data about a power source exists...");
    }
    commandList =
        commandProcessor.parseCommand("total carbon generated SIM_OAHU_GRID day 2009-11-01"
            + " sampling-interval 120");
    cli.errorEncountered = false;
    result = (new TotalCarbonOrEnergy()).doCommand(cli, commandList);
    if (verboseMode) {
      System.out.println(result);
    }
    float generated =
        Float.parseFloat(result.substring(result.indexOf(':') + 2, result.indexOf('.') + 1));
    assertTrue("Checking carbon data", generated >= 0.0);
    if (verboseMode) {
      System.out.println(OK_MESSAGE + CARRIAGE_RETURN);
    }
  }

  /**
   * Tests the listTotal method to see if energy data exists.
   * 
   * @throws Exception If a problem occurs.
   */
  @Test
  public void testTotalCarbonOrEnergyEnergyGenerated() throws Exception {
    if (verboseMode) {
      System.out.println("Command: total energy generated SIM_OAHU_GRID day 2009-11-01 "
          + "sampling-interval 60" + CARRIAGE_RETURN);
      System.out.println("Checking if data about a power source exists...");
    }
    commandList =
        commandProcessor.parseCommand("total energy generated SIM_OAHU_GRID day 2009-11-01"
            + " sampling-interval 60");
    cli.errorEncountered = false;
    result = (new TotalCarbonOrEnergy()).doCommand(cli, commandList);
    if (verboseMode) {
      System.out.println(result);
    }
    float generated =
        Float.parseFloat(result.substring(result.indexOf(':') + 2, result.indexOf('.') + 1));
    assertTrue("Checking energy data", generated >= 0.0);
    if (verboseMode) {
      System.out.println(OK_MESSAGE + CARRIAGE_RETURN);
    }
  }

}