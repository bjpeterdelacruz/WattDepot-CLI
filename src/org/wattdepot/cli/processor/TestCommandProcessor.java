package org.wattdepot.cli.processor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.wattdepot.cli.CommandLineInterface;
import org.wattdepot.cli.command.CliCommandTest;

/**
 * Tests the CommandProcessor class methods.
 * 
 * @author BJ Peter DeLaCruz, Wahib Hanani, Lyneth Peou
 * @version 2.0
 */
public class TestCommandProcessor extends CliCommandTest {

  /**
   * Tests the CommandProcessor parser method.
   * 
   * @throws Exception If a problem occurs.
   */
  @Before
  @Test
  public void testParseCommand() throws Exception {
    List<String> commandList =
        (new CommandProcessor()).parseCommand("dailysensordata SIM_KAHE_1 day 2009-11-01");

    if (verboseMode) {
      System.out.println(CARRIAGE_RETURN + "Checking parseCommand method..." + CARRIAGE_RETURN);
      System.out.println("Command: dailysensordata SIM_KAHE_1 day 2009-11-01" + CARRIAGE_RETURN);
      System.out.println("Checking if command list contains 4 tokens...");
    }
    assertEquals("Command contains 4 tokens", 4, commandList.size());
    if (verboseMode) {
      System.out.println(OK_MESSAGE + CARRIAGE_RETURN);
    }
  }

  /**
   * Tests the CommandProcessor processCommand method.
   * 
   * @throws Exception If a problem occurs.
   */
  @Test
  public void testProcessCommand() throws Exception {
    CommandLineInterface cli = new CommandLineInterface();
    CommandProcessor commandProcessor = new CommandProcessor();

    if (verboseMode) {
      System.out.println("Checking processCommand method..." + CARRIAGE_RETURN);
      System.out.println("Command: " + CARRIAGE_RETURN);
      System.out.println("Checking if invalid input is handled correctly...");
    }
    List<String> commandList = commandProcessor.parseCommand("");
    commandProcessor.processCommand(cli, commandList);
    assertTrue("Checking if invalid input is handled correctly", cli.errorEncountered);
    if (verboseMode) {
      System.out.println(OK_MESSAGE + CARRIAGE_RETURN);
    }

    if (verboseMode) {
      System.out.println("Command: fumiko" + CARRIAGE_RETURN);
      System.out.println("Checking if invalid input is handled correctly...");
    }
    commandList = commandProcessor.parseCommand("fumiko");
    cli.errorEncountered = false;
    commandProcessor.processCommand(cli, commandList);
    assertTrue("Checking if invalid input is handled correctly", cli.errorEncountered);
    if (verboseMode) {
      System.out.println(OK_MESSAGE + CARRIAGE_RETURN + CARRIAGE_RETURN);
    }
  }

}