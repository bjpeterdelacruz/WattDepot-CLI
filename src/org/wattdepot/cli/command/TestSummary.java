package org.wattdepot.cli.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.wattdepot.cli.CommandLineInterface;
import org.wattdepot.cli.processor.CliSource;
import org.wattdepot.cli.processor.CommandProcessor;
import org.wattdepot.resource.source.jaxb.Source;
import org.wattdepot.resource.source.jaxb.SubSources;

/**
 * Tests the listSummary method to see if invalid input is handled correctly; power sources SIM_HND
 * and SIM_OAHU_GRID exist on this server; and the number of subsources of SIM_OAHU_GRID is four.
 * 
 * @author BJ Peter DeLaCruz, Wahib Hanani, Lyneth Peou
 * @version 2.0
 */
public class TestSummary extends CliCommandTest {
  private CommandProcessor commandProcessor;
  private CommandLineInterface cli;
  private List<String> commandList;
  private String result;
  private String command;

  // private boolean verboseMode = true;

  /**
   * Tests the listSummary method to see if power source SIM_HND exists on this server.
   * 
   * @throws Exception If a problem occurs.
   */
  @Before
  @Test
  public void testListSummaryInvalidSource() throws Exception {
    command = "summary SIM_HND";
    // HND = Haneda, Japan
    commandProcessor = new CommandProcessor();
    commandList = commandProcessor.parseCommand(command);
    cli = new CommandLineInterface();
    commandProcessor.processCommand(cli, commandList);
    assertTrue(ASSERT_SOURCE_MESSAGE, cli.errorEncountered);
  }

  /**
   * Tests the listSummary method to see if power source SIM_KAHE_1 exists on this server.
   * 
   * @throws Exception If a problem occurs.
   */
  @Test
  public void testListSummaryValidSource() throws Exception {
    command = "summary SIM_KAHE_1";
    if (verboseMode) {
      System.out.println("> " + command);
      System.out.println("Checking if power source exists...");
    }
    commandProcessor = new CommandProcessor();
    commandList = commandProcessor.parseCommand(command);
    cli = new CommandLineInterface();
    result = (new Summary()).doCommand(cli, commandList);
    assertFalse(ASSERT_SOURCE_MESSAGE, cli.errorEncountered);
    if (verboseMode) {
      System.out.println(result);
      System.out.println(OK_MESSAGE + CARRIAGE_RETURN);
    }
  }

  /**
   * Tests the listSummary method to see if power source SIM_OAHU_GRID exists on this server.
   * 
   * @throws Exception If a problem occurs.
   */
  @Test
  public void testListSummaryOahuGrid() throws Exception {
    command = "summary SIM_OAHU_GRID";
    if (verboseMode) {
      System.out.println("> " + command);
      System.out.println("Checking if power source exists...");
    }
    commandProcessor = new CommandProcessor();
    commandList = commandProcessor.parseCommand(command);
    cli = new CommandLineInterface();
    commandProcessor.processCommand(cli, commandList);
    assertFalse(ASSERT_SOURCE_MESSAGE, cli.errorEncountered);
    if (verboseMode) {
      System.out.println(result);
      System.out.println(OK_MESSAGE + CARRIAGE_RETURN);
    }
  }

  /**
   * Tests the listSummary method to see if the number of subsources of SIM_OAHU_GRID is four.
   * 
   * @throws Exception If a problem occurs.
   */
  @Test
  public void testListSummaryNumberOfSources() throws Exception {
    if (verboseMode) {
      System.out.println("Checking if SIM_OAHU_GRID has four subsources "
          + "(SIM_HONOLULU, SIM_IPP, SIM_KAHE, SIM_WAIAU)...");
    }
    Source simOahuGridSource = (new CliSource()).getSource(cli, "SIM_OAHU_GRID");
    SubSources simOahuGridSubSources = simOahuGridSource.getSubSources();
    ArrayList<String> virtualSourcesList = new ArrayList<String>();
    virtualSourcesList.add("SIM_WAIAU");
    virtualSourcesList.add("SIM_KAHE");
    virtualSourcesList.add("SIM_IPP");
    virtualSourcesList.add("SIM_HONOLULU");
    int count = 0;
    for (String simOahuGrid : simOahuGridSubSources.getHref()) {
      for (String virtualSource : virtualSourcesList) {
        if (simOahuGrid.contains(virtualSource)) {
          count++;
          break;
        } // end if
      } // end for
    } // end for
    assertEquals("Checking number of subsources of SIM_OAHU_GRID", 4, count);
    if (verboseMode) {
      System.out.println(OK_MESSAGE + CARRIAGE_RETURN + CARRIAGE_RETURN);
    }
  }

}