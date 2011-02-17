package org.wattdepot.cli.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.wattdepot.cli.CommandLineInterface;
import org.wattdepot.cli.processor.CommandProcessor;

/**
 * Tests the getHelp method to see if all commands have been explained.
 * 
 * @author BJ Peter DeLaCruz, Wahib Hanani, Lyneth Peou
 * @version 2.0
 */
public class TestHelp extends CliCommandTest {
  // private boolean verboseMode = true;

  /**
   * Tests the getHelp method to see if all commands have been explained.
   * 
   * @throws Exception If a problem occurs.
   */
  @Before
  @Test
  public void testHelpValidInput() throws Exception {
    CommandProcessor commandProcessor = new CommandProcessor();
    List<String> commandList = commandProcessor.parseCommand("help");
    CommandLineInterface cli = new CommandLineInterface();
    commandProcessor.processCommand(cli, commandList);
    String result = (new Help()).doCommand(cli, commandList);
    int numberOfCommands = result.replaceAll("[^>]", "").length();
    assertEquals("Checking number of commands", numberOfCommands, 13);
  }

  /**
   * Tests the getHelp method to see if command is valid.
   * 
   * @throws Exception If a problem occurs.
   */
  @Test
  public void testMispelledInput() throws Exception {
    CommandProcessor commandProcessor = new CommandProcessor();
    List<String> commandList = commandProcessor.parseCommand("helped");
    CommandLineInterface cli = new CommandLineInterface();
    commandProcessor.processCommand(cli, commandList);
    assertTrue("Checking command", cli.errorEncountered);
  }

}