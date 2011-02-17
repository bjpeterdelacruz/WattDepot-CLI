package org.wattdepot.cli.command;

import static org.junit.Assert.assertTrue;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.wattdepot.cli.CommandLineInterface;
import org.wattdepot.cli.processor.CommandProcessor;

/**
 * Tests the Quit command.
 * 
 * @author BJ Peter DeLaCruz, Wahib Hanani, Lyneth Peou
 * @version 2.0
 */
public class TestQuit extends CliCommandTest {

  /**
   * Tests the doCommand method to check if the system exits.
   * 
   * @throws Exception If a problem occurs.
   */
  @Before
  @Test
  public void testExitValidInput() throws Exception {
    CommandProcessor commandProcessor = new CommandProcessor();
    List<String> commandList = commandProcessor.parseCommand("quit");
    CommandLineInterface cli = new CommandLineInterface();
    commandProcessor.processCommand(cli, commandList);
    assertTrue("Exiting the system...", cli.exit);
  }

  /**
   * Tests the doCommand method to check if the system runs into an error.
   * 
   * @throws Exception If a problem occurs.
   */
  @Test
  public void testExitInvalidInput() throws Exception {
    CommandProcessor commandProcessor = new CommandProcessor();
    List<String> commandList = commandProcessor.parseCommand("quits");
    CommandLineInterface cli = new CommandLineInterface();
    commandProcessor.processCommand(cli, commandList);
    assertTrue("Exiting the system...", cli.errorEncountered);
  }

}