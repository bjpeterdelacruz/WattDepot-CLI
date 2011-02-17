package org.wattdepot.cli.command;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.wattdepot.cli.CommandLineInterface;
import org.wattdepot.cli.processor.CommandProcessor;

/**
 * Tests the listSources method to see if correct information is being returned to the user.
 * 
 * @author BJ Peter DeLaCruz, Wahib Hanani, Lyneth Peou
 * @version 2.0
 */
public class TestSources extends CliCommandTest {
  private CommandProcessor commandProcessor;
  private CommandLineInterface cli;
  private List<String> commandList;
  private boolean verboseMode = true;
  private static final String SOURCE_STRING = "sources";
  private Sources sources;
  private boolean isFound;

  /**
   * Tests the listSources method to check if a valid command was inputted.
   * 
   * @throws Exception If a problem occurs.
   */
  @Before
  @Test
  public void testListSourcesValidInput() throws Exception {
    commandProcessor = new CommandProcessor();
    commandList = commandProcessor.parseCommand(SOURCE_STRING);
    cli = new CommandLineInterface();
    commandProcessor.processCommand(cli, commandList);
    assertFalse(ASSERT_SOURCE_MESSAGE, cli.errorEncountered);
  }

  /**
   * Tests the listSources method to see if correct information is being returned to the user.
   * 
   * @throws Exception If a problem occurs.
   */
  @Test
  public void testListSourcesOahuGrid() throws Exception {
    commandList = commandProcessor.parseCommand(SOURCE_STRING);
    List<String> commandList = new ArrayList<String>();
    commandList.add(SOURCE_STRING);
    sources = new Sources();
    sources.doCommand(cli, commandList);
    isFound = false;
    for (String sourceInformation : sources.getSourceInformationList()) {
      if (sourceInformation.contains("SIM_OAHU_GRID") && sourceInformation.contains("[ None ]")) {
        isFound = true;
        break;
      } // end if
    } // end for
    assertTrue("Checking if SIM_OAHU_GRID has no parent", isFound);
  }

  /**
   * Tests the listSources method to see if correct information is being returned to the user.
   * 
   * @throws Exception If a problem occurs.
   */
  @Test
  public void testListSourcesWaiauVirtual() throws Exception {
    commandList = commandProcessor.parseCommand(SOURCE_STRING);
    cli = new CommandLineInterface();
    sources = new Sources();
    sources.doCommand(cli, commandList);
    isFound = false;
    for (String sourceInformation : sources.getSourceInformationList()) {
      if (sourceInformation.contains("SIM_WAIAU") && sourceInformation.contains("SIM_OAHU_GRID")) {
        isFound = true;
        break;
      } // end if
    } // end for
    assertTrue("Checking if SIM_WAIAU is a subsource of SIM_OAHU_GRID", isFound);
    if (verboseMode) {
      System.out.println(OK_MESSAGE + CARRIAGE_RETURN);
    }
  }

  /**
   * Tests the listSources method to see if correct information is being returned to the user.
   * 
   * @throws Exception If a problem occurs.
   */
  @Test
  public void testListSourcesWaiau() throws Exception {
    commandList = commandProcessor.parseCommand(SOURCE_STRING);
    cli = new CommandLineInterface();
    sources = new Sources();
    sources.doCommand(cli, commandList);
    isFound = false;
    for (String sourceInformation : sources.getSourceInformationList()) {
      if (sourceInformation.contains("SIM_WAIAU_10") && sourceInformation.contains("SIM_WAIAU")) {
        isFound = true;
        break;
      } // end if
    } // end for
    assertTrue("Checking if SIM_WAIAU_10 is a subsource of SIM_WAIAU", isFound);
    if (verboseMode) {
      System.out.println(OK_MESSAGE + CARRIAGE_RETURN);
    }
  }

}