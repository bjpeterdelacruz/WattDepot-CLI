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
 * Tests the carbonContent method to see if power source exists on this server; timestamp and
 * sampling-interval strings are valid; and formula used to calculate carbon content of a power
 * source is correct.
 * 
 * @author BJ Peter DeLaCruz, Wahib Hanani, Lyneth Peou
 * @version 2.0
 */
public class TestCarbonContent extends CliCommandTest {
  private CommandProcessor commandProcessor;
  private List<String> commandList;
  private CommandLineInterface cli;
  // private boolean verboseMode = true;
  private String result = null;
  private String command;

  /**
   * Tests the carbonContent method to see if power source exists on this server.
   * 
   * @throws Exception If a problem occurs.
   */
  @Before
  @Test
  public void testCarbonContentSource() throws Exception {
    command = "carboncontent SIM_SEA 2009-11-01T00:00:00.0000 sampling-interval 180";
    commandProcessor = new CommandProcessor();
    commandList = commandProcessor.parseCommand(command);
    cli = new CommandLineInterface();
    commandProcessor.processCommand(cli, commandList);
    assertTrue(ASSERT_SOURCE_MESSAGE, cli.errorEncountered);
  }

  /**
   * Tests the carbonContent method to see if timestamp string is valid.
   * 
   * @throws Exception If a problem occurs.
   */
  @Test
  public void testCarbonContentTimestamp() throws Exception {
    command = "carboncontent SIM_OAHU_GRID 11-01-2009T00:00:00.0000 sampling-interval 120";
    if (verboseMode) {
      System.out.println("> " + command);
    }
    commandList = commandProcessor.parseCommand(command);
    cli = new CommandLineInterface();
    result = commandProcessor.processCommand(cli, commandList);
    if (verboseMode) {
      System.out.println(result + CARRIAGE_RETURN);
    }
    assertTrue(ASSERT_TIMESTAMP_MESSAGE, cli.errorEncountered);
  }

  /**
   * Tests the carbonContent method to see if sampling-interval string is valid.
   * 
   * @throws Exception If a problem occurs.
   */
  @Test
  public void testCarbonContentSamplingInterval() throws Exception {
    command = "carboncontent SIM_OAHU_GRID 2009-11-01T00:00:00.0000 sampling-interval lol";
    if (verboseMode) {
      System.out.println("> " + command);
    }
    commandList = commandProcessor.parseCommand(command);
    cli = new CommandLineInterface();
    result = commandProcessor.processCommand(cli, commandList);
    if (verboseMode) {
      System.out.println(result + CARRIAGE_RETURN);
    }
    assertTrue("Checking if sampling-interval string is valid", cli.errorEncountered);
  }

  /**
   * Tests the carbonContent method to check if the value of sampling interval is negative.
   * 
   * @throws Exception If a problem occurs.
   */
  @Test
  public void testCarbonContentNegativeInterval() throws Exception {
    command = "carboncontent SIM_OAHU_GRID 2009-11-01T00:00:00.0000 sampling-interval -20";
    if (verboseMode) {
      System.out.println("> " + command);
    }
    commandList = commandProcessor.parseCommand(command);
    cli = new CommandLineInterface();
    result = commandProcessor.processCommand(cli, commandList);
    if (verboseMode) {
      System.out.println(result + CARRIAGE_RETURN);
    }
    assertTrue("Checking if sampling interval value is negative", cli.errorEncountered);
  }

  /**
   * Tests the carbonContent method to see if formula used to calculate carbon content is correct.
   * 
   * @throws Exception If a problem occurs.
   */
  @Test
  public void testCarbonContentFormula() throws Exception {
    command = "carboncontent SIM_WAIAU_8 2009-11-15T12:00:00.0000 sampling-interval 60";
    if (verboseMode) {
      System.out.println("> " + command);
    }
    commandList = commandProcessor.parseCommand(command);
    cli = new CommandLineInterface();
    result = commandProcessor.processCommand(cli, commandList);
    if (verboseMode) {
      System.out.println(result + CARRIAGE_RETURN);
    }
    assertEquals("Checking if data is correct", Double.parseDouble(result.substring(0, result
        .indexOf(' '))), 1800.0, 0.1);
    assertFalse("Checking if any errors were encountered", cli.errorEncountered);
  }

  /**
   * Tests the carbonContent method to check if denominator is zero.
   * 
   * @throws Exception If a problem occurs.
   */
  @Test
  public void testCarbonContentNaN() throws Exception {
    command = "carboncontent SIM_HPOWER 2009-11-01T00:00:00.0000 sampling-interval 60";
    if (verboseMode) {
      System.out.println("> " + command);
    }
    commandList = commandProcessor.parseCommand(command);
    cli = new CommandLineInterface();
    result = commandProcessor.processCommand(cli, commandList);
    if (verboseMode) {
      System.out.println(result + CARRIAGE_RETURN);
    }
    assertTrue("Checking if the denominator is zero", cli.errorEncountered);
  }

}