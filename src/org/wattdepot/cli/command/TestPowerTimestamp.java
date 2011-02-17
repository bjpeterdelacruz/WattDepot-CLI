package org.wattdepot.cli.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.wattdepot.cli.CommandLineInterface;
import org.wattdepot.cli.processor.CommandProcessor;

/**
 * Tests the listPowerTimestamp method to see if power source exists on this server; timestamp
 * string is valid; and data about power source exists.
 * 
 * @author BJ Peter DeLaCruz, Wahib Hanani, Lyneth Peou
 * @version 2.0
 */
public class TestPowerTimestamp extends CliCommandTest {
  private CommandProcessor commandProcessor;
  private List<String> commandList;
  private CommandLineInterface cli;
  private String result;
  private String command = null;

  // private boolean verboseMode = true;

  /**
   * Tests the listPowerTimestamp method to see if power source exists on this server.
   * 
   * @throws Exception If a problem occurs.
   */
  @Before
  @Test
  public void testListPowerTimestampInvalidSourceGenerated() throws Exception {
    command = "power generated SIM_SEA timestamp 2009-11-01T00:00:00.0000";
    commandProcessor = new CommandProcessor();
    commandList = commandProcessor.parseCommand(command);
    cli = new CommandLineInterface();
    commandProcessor.processCommand(cli, commandList);
    assertTrue(ASSERT_SOURCE_MESSAGE, cli.errorEncountered);
  }

  /**
   * Tests the listPowerTimestamp method to see if power source exists on this server.
   * 
   * @throws Exception If a problem occurs.
   */
  @Test
  public void testListPowerTimestampInvalidSourceConsumed() throws Exception {
    command = "power consumed SIM_SEA timestamp 2009-11-01T00:00:00.0000";
    if (verboseMode) {
      System.out.println("> " + command);
      System.out.println(CHECKING_SOURCE_MESSAGE);
    }
    commandList = commandProcessor.parseCommand(command);
    cli.errorEncountered = false;
    commandProcessor.processCommand(cli, commandList);
    assertTrue(ASSERT_SOURCE_MESSAGE, cli.errorEncountered);
    if (verboseMode) {
      System.out.println(result);
      System.out.println(OK_MESSAGE + CARRIAGE_RETURN);
    }
  }

  /**
   * Tests the listPowerTimestamp method to see if timestamp string is valid.
   * 
   * @throws Exception If a problem occurs.
   */
  @Test
  public void testListPowerTimestampTimestamp() throws Exception {
    command = "power generated SIM_KAHE_1 timestamp 11-01-2009T00:00:00.0000";
    if (verboseMode) {
      System.out.println("> " + command);
      System.out.println("Checking if timestamp is not a valid string...");
    }
    commandList = commandProcessor.parseCommand(command);
    cli.errorEncountered = false;
    commandProcessor.processCommand(cli, commandList);
    assertTrue(ASSERT_TIMESTAMP_MESSAGE, cli.errorEncountered);
    if (verboseMode) {
      System.out.println(result);
      System.out.println(OK_MESSAGE + CARRIAGE_RETURN);
    }
  }

  /**
   * Tests the listPowerTimestamp method to see if data about power source exists.
   * 
   * @throws Exception If a problem occurs.
   */
  @Test
  public void testListPowerTimestampGenerated() throws Exception {
    command = "power generated SIM_OAHU_GRID timestamp 2009-11-01T00:00:00.0000";
    if (verboseMode) {
      System.out.println("> " + command);
      System.out.println("Checking if data about power source exists...");
    }
    commandList = commandProcessor.parseCommand(command);
    commandProcessor.processCommand(cli, commandList);
    result = (new PowerTimestamp()).doCommand(cli, commandList);
    int generated = Integer.parseInt(result.substring(0, result.indexOf('.')));
    assertTrue("Checking if data about power source exists", generated >= (Integer.MIN_VALUE + 1));
    if (verboseMode) {
      System.out.println(result);
      System.out.println(OK_MESSAGE + CARRIAGE_RETURN);
    }
  }

  /**
   * Tests the listPowerTimestamp method to see if data about power source exists.
   * 
   * @throws Exception If a problem occurs.
   */
  @Test
  public void testListPowerTimestampGeneratedNoData() throws Exception {
    command = "power generated SIM_KAHE_1 timestamp 2009-11-01T00:00:00.0000";
    if (verboseMode) {
      System.out.println("> " + command + CARRIAGE_RETURN);
      System.out.println("Checking if data about power source exists...");
    }
    commandList = commandProcessor.parseCommand(command);
    commandProcessor.processCommand(cli, commandList);
    result = (new PowerTimestamp()).doCommand(cli, commandList);
    assertEquals("Checking if data about power source exists",
        "No data for SIM_KAHE_1 on 2009-11-01T00:00:00.0000.", result);
    if (verboseMode) {
      System.out.println(result);
      System.out.println(OK_MESSAGE + CARRIAGE_RETURN);
    }
  }

  /**
   * Tests the listPowerTimestamp method to see if data about power source exists.
   * 
   * @throws Exception If a problem occurs.
   */
  @Test
  public void testListPowerTimestampOahuGridConsumed() throws Exception {
    command = "power consumed SIM_OAHU_GRID timestamp 2009-11-01T00:00:00.0000";
    if (verboseMode) {
      System.out.println("> " + command);
      System.out.println("Checking if data about power source exists...");
    }
    commandList = commandProcessor.parseCommand(command);
    commandProcessor.processCommand(cli, commandList);
    result = (new PowerTimestamp()).doCommand(cli, commandList);
    assertEquals("Checking if data about power source exists",
        "No data for SIM_OAHU_GRID on 2009-11-01T00:00:00.0000.", result);
    if (verboseMode) {
      System.out.println(result);
      System.out.println(OK_MESSAGE + CARRIAGE_RETURN);
    }
  }

  /**
   * Tests the listPowerTimestamp method to see if the command is valid.
   * 
   * @throws Exception If a problem occurs.
   */
  @Test
  public void testListPowerTimestampInvalidPowerCommand() throws Exception {
    command = "power consumer SIM_OAHU_GRID timestamp 2009-11-01T00:00:00.0000";
    if (verboseMode) {
      System.out.println("> " + command);
    }
    commandList = commandProcessor.parseCommand(command);
    commandProcessor.processCommand(cli, commandList);
    result = (new PowerTimestamp()).doCommand(cli, commandList);
    assertTrue("Checking command", cli.errorEncountered);
    if (verboseMode) {
      System.out.println(result);
      System.out.println(OK_MESSAGE + CARRIAGE_RETURN);
    }
  }
}