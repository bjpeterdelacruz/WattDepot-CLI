package org.wattdepot.cli.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.wattdepot.cli.CommandLineInterface;
import org.wattdepot.cli.processor.CommandProcessor;

/**
 * Tests the listSensorDataTimestamp method to see if power source exists on this server and also if
 * timestamp string is valid.
 * 
 * @author BJ Peter DeLaCruz, Wahib Hanani, Lyneth Peou
 * @version 2.0
 */
public class TestSensordataTimestamp extends CliCommandTest {
  private CommandProcessor commandProcessor;
  private List<String> commandList;
  private CommandLineInterface cli;
  private String result;
  private String command;

  // private boolean verboseMode = true;

  /**
   * Tests the listSensorDataTimestamp method to see if power source exists on this server.
   * 
   * @throws Exception If a problem occurs.
   */
  @Before
  @Test
  public void testListSensorDataTimestampSource() throws Exception {
    command = "sensordata SIM_SEA timestamp 2009-11-01T00:00:00.0000";
    commandProcessor = new CommandProcessor();
    commandList = commandProcessor.parseCommand(command);
    cli = new CommandLineInterface();
    commandProcessor.processCommand(cli, commandList);
    assertTrue(ASSERT_SOURCE_MESSAGE, cli.errorEncountered);
  }

  /**
   * Tests the listSensorDataTimestamp method to see if timestamp string is valid.
   * 
   * @throws Exception If a problem occurs.
   */
  @Test
  public void testListSensorDataTimestampTimestamp() throws Exception {
    command = "sensordata SIM_KAHE_1 timestamp 11-01-2009T00:00:00.0000";
    if (verboseMode) {
      System.out.println("> " + command);
      System.out.println("Checking if timestamp is not a valid string...");
    }
    commandList = commandProcessor.parseCommand(command);
    cli = new CommandLineInterface();
    commandProcessor.processCommand(cli, commandList);
    assertTrue(ASSERT_TIMESTAMP_MESSAGE, cli.errorEncountered);
    if (verboseMode) {
      System.out.println(result);
      System.out.println(OK_MESSAGE + CARRIAGE_RETURN);
    }
  }

  /**
   * Tests the listSensorDataTimestamp method to see if data about a power source exists.
   * 
   * @throws Exception If a problem occurs.
   */
  @Test
  public void testListSensorDataTimestampCurrentYear() throws Exception {
    command = "sensordata SIM_KAHE_1 timestamp 2009-11-01T00:00:00.0000";
    if (verboseMode) {
      System.out.println("> " + command);
      System.out.println("Checking if data about power source exists...");
    }
    commandList = commandProcessor.parseCommand(command);
    cli = new CommandLineInterface();
    commandProcessor.processCommand(cli, commandList);
    result = (new SensordataTimestamp()).doCommand(cli, commandList);
    if (verboseMode) {
      System.out.println(result);
    }
    assertTrue("Checking if data about power source exists", result.contains("Tool")
        && result.contains("Source") && result.contains("Properties"));
    if (verboseMode) {
      System.out.println(OK_MESSAGE + CARRIAGE_RETURN);
    }
  }

  /**
   * Tests the listSensorDataTimestamp method to see if data about a power source exists.
   * 
   * @throws Exception If a problem occurs.
   */
  @Test
  public void testListSensorDataTimestampPreviousYear() throws Exception {
    command = "sensordata SIM_KAHE_1 timestamp 2008-11-01T00:00:00.0000";
    if (verboseMode) {
      System.out.println("> " + command);
      System.out.println("Checking if data about power source exists...");
    }
    commandList = commandProcessor.parseCommand(command);
    cli = new CommandLineInterface();
    commandProcessor.processCommand(cli, commandList);
    result = (new SensordataTimestamp()).doCommand(cli, commandList);
    if (verboseMode) {
      System.out.println(result);
    }
    assertEquals("Checking if data about power source exists",
        "No data for SIM_KAHE_1 on 2008-11-01T00:00:00.0000.", result);
    if (verboseMode) {
      System.out.println(OK_MESSAGE + CARRIAGE_RETURN);
    }
  }

}