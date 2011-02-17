package org.wattdepot.cli.command;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.wattdepot.cli.CommandLineInterface;
import org.wattdepot.cli.processor.CommandProcessor;

/**
 * Tests the listSensorDataDay method to see if power source exists on this server, day string is
 * valid, and data about power source exists.
 * 
 * @author BJ Peter DeLaCruz, Wahib Hanani, Lyneth Peou
 * @version 2.0
 */
public class TestSensordataDay extends CliCommandTest {
  private CommandProcessor commandProcessor;
  private List<String> commandList;
  private CommandLineInterface cli;
  private String result;
  private String command;

  // private boolean verboseMode = true;

  /**
   * Tests the listSensorDataDay method to see if power source exists on this server.
   * 
   * @throws Exception If a problem occurs.
   */
  @Before
  @Test
  public void testListSensorDataDaySource() throws Exception {
    command = "dailysensordata SIM_SEA day 2009-11-01";
    commandProcessor = new CommandProcessor();
    commandList = commandProcessor.parseCommand(command);
    cli = new CommandLineInterface();
    commandProcessor.processCommand(cli, commandList);
    assertTrue(ASSERT_SOURCE_MESSAGE, cli.errorEncountered);
  }

  /**
   * Tests the listSensorDataDay method to see if day string is valid.
   * 
   * @throws Exception If a problem occurs.
   */
  @Test
  public void testListSensorDataDayInvalidDay() throws Exception {
    command = "dailysensordata SIM_KAHE_1 day 2009-11-32";
    if (verboseMode) {
      System.out.println("> " + command);
      System.out.println("Checking day string...");
    }
    commandList = commandProcessor.parseCommand(command);
    cli = new CommandLineInterface();
    result = commandProcessor.processCommand(cli, commandList);
    assertTrue("Checking if date is invalid", cli.errorEncountered);
    if (verboseMode) {
      System.out.println(result);
      System.out.println(OK_MESSAGE + CARRIAGE_RETURN);
    }
  }

  /**
   * Tests the listSensorDataDay method to see if data about a power source exists.
   * 
   * @throws Exception If a problem occurs.
   */
  @Test
  public void testListSensorDataDayInvalidData() throws Exception {
    command = "dailysensordata SIM_KAHE_1 day 2008-11-01";
    if (verboseMode) {
      System.out.println("> " + command);
      System.out.println("Checking if data about power source exists...");
    }
    commandList = commandProcessor.parseCommand(command);
    cli = new CommandLineInterface();
    commandProcessor.processCommand(cli, commandList);
    cli = new CommandLineInterface();
    result = (new SensordataDay()).doCommand(cli, commandList);
    assertTrue("Checking if data about power source exists",
        "No data for SIM_KAHE_1 on 2008-11-01.".equalsIgnoreCase(result));
    if (verboseMode) {
      System.out.println(result);
      System.out.println(OK_MESSAGE + CARRIAGE_RETURN);
    }
  }

  /**
   * Tests the listSensorDataDay method to check if the date is valid.
   * 
   * @throws Exception If a problem occurs.
   */
  @Test
  public void testListSensorDataDayNotTimestamp() throws Exception {
    command = "dailysensordata SIM_KAHE_1 day 2009-10-30T10:10:10.000-10:00";
    if (verboseMode) {
      System.out.println("> " + command);
      System.out.println("Checking if day is not a timestamp...");
    }
    commandList = commandProcessor.parseCommand(command);
    cli = new CommandLineInterface();
    result = commandProcessor.processCommand(cli, commandList);
    assertTrue("Checking if day is not a timestamp", cli.errorEncountered);
    if (verboseMode) {
      System.out.println(result);
      System.out.println(OK_MESSAGE + CARRIAGE_RETURN);
    }
  }

  /**
   * Tests the listSensorDataDay method to check if the power source is valid.
   * 
   * @throws Exception If a problem occurs.
   */
  @Test
  public void testListSensorDataDayValidSourceData() throws Exception {
    command = "dailysensordata SIM_KAHE_1 day 2009-10-25";
    if (verboseMode) {
      System.out.println("> " + command);
      System.out.println("Checking if data about power source exists...");
    }

    System.out.println(cli.errorEncountered);
    commandList = commandProcessor.parseCommand(command);
    cli = new CommandLineInterface();
    result = commandProcessor.processCommand(cli, commandList);
    assertFalse("Checking if data about power source exists", cli.errorEncountered);
    if (verboseMode) {
      System.out.println(OK_MESSAGE + CARRIAGE_RETURN);
    }
  }

  /**
   * Tests the listSensorDataDay method to check mispelled input.
   * 
   * @throws Exception If a problem occurs.
   */
  @Test
  public void testListSensorDataDayValidData() throws Exception {
    command = "dailysensordatas SIM_KAHE_1 day 2009-11-01";
    if (verboseMode) {
      System.out.println("> " + command);
    }
    commandList = commandProcessor.parseCommand(command);
    cli = new CommandLineInterface();
    result = commandProcessor.processCommand(cli, commandList);
    assertTrue("Checking invalid command", cli.errorEncountered);
    if (verboseMode) {
      System.out.println(result);
      System.out.println(OK_MESSAGE + CARRIAGE_RETURN);
    }
  }

  /**
   * Tests the listSensorDataDay method to check if all of the arguments have been supplied.
   * 
   * @throws Exception If a problem occurs.
   */
  @Test
  public void testListSensorDataDayInvalidSource() throws Exception {
    command = "dailysensordata SIM_KAHE_1";
    if (verboseMode) {
      System.out.println("> " + command);
    }
    commandList = commandProcessor.parseCommand(command);
    cli = new CommandLineInterface();
    result = commandProcessor.processCommand(cli, commandList);
    assertTrue("Checking number of arguments", cli.errorEncountered);
    if (verboseMode) {
      System.out.println(result);
      System.out.println(OK_MESSAGE + CARRIAGE_RETURN);
    }
  }

}