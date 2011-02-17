package org.wattdepot.cli.command;

import static org.junit.Assert.assertTrue;
import java.io.File;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.wattdepot.cli.CommandLineInterface;
import org.wattdepot.cli.processor.CommandProcessor;

/**
 * Tests the getChartData method to see if power source exists on this server; startday, endday, and
 * sampling-interval strings are valid; endday comes after startday; and HTML files are generated
 * for power generated and power consumed.
 * 
 * @author BJ Peter DeLaCruz, Wahib Hanani, Lyneth Peou
 * @version 2.0
 */
public class TestChartPower extends CliCommandTest {
  private CommandProcessor commandProcessor;
  private List<String> commandList;
  private CommandLineInterface cli;
  private String result;

  // private boolean verboseMode = true;

  /**
   * Tests the getChartData method to check if the power source is valid.
   * 
   * @throws Exception If a problem occurs.
   */
  @Before
  @Test
  public void testgetChartDataInvalidSource() throws Exception {
    String command =
        "chart power generated SIM_SEA 2009-11-01 2009-11-02 "
            + "sampling-interval 15 file test.html";

    commandProcessor = new CommandProcessor();
    commandList = commandProcessor.parseCommand(command);

    cli = new CommandLineInterface();
    commandProcessor.processCommand(cli, commandList);
    assertTrue(ASSERT_SOURCE_MESSAGE, cli.errorEncountered);
  }

  /**
   * Tests the getChartData method to check if the startday string is valid.
   * 
   * @throws Exception If a problem occurs.
   */
  @Test
  public void testgetChartDataStartDay() throws Exception {
    String command =
        "chart power generated SIM_KAHE_1 11-01-2009 2009-11-02 sampling-interval 30 "
            + "file simkahe1.html";
    if (verboseMode) {
      System.out.println("> " + command);
      System.out.println("Checking if startday is not a valid string...");
    }
    commandList = commandProcessor.parseCommand(command);
    cli = new CommandLineInterface();
    result = commandProcessor.processCommand(cli, commandList);
    assertTrue(ASSERT_DAY_MESSAGE, cli.errorEncountered);
    if (verboseMode) {
      System.out.println(result);
      System.out.println(OK_MESSAGE + CARRIAGE_RETURN);
    }
  }

  /**
   * Tests the getChartData method to check if the endday string is valid.
   * 
   * @throws Exception If a problem occurs.
   */
  @Test
  public void testgetChartDataEndDay() throws Exception {
    String command =
        "chart power generated SIM_KAHE_1 2009-11-01 11-02-2009 sampling-interval 20"
            + " file simkahe1_2.html";

    if (verboseMode) {
      System.out.println("> " + command);
      System.out.println("Checking if endday is not a valid string...");
    }
    commandList = commandProcessor.parseCommand(command);
    cli = new CommandLineInterface();
    result = commandProcessor.processCommand(cli, commandList);
    assertTrue(ASSERT_DAY_MESSAGE, cli.errorEncountered);
    if (verboseMode) {
      System.out.println(result);
      System.out.println(OK_MESSAGE + CARRIAGE_RETURN);
    }
  }

  /**
   * Tests the getChartData method to check if the sampling-interval string is valid.
   * 
   * @throws Exception If a problem occurs.
   */
  @Test
  public void testgetChartDataSamplingInterval() throws Exception {
    String command =
        "chart power generated SIM_KAHE_1 2009-11-01 2009-11-02 sampling-interval 6.0 "
            + "file power.html";

    if (verboseMode) {
      System.out.println("> " + command);
      System.out.println("Checking if sampling-interval is not a valid number...");
    }
    commandList = commandProcessor.parseCommand(command);
    cli = new CommandLineInterface();
    result = commandProcessor.processCommand(cli, commandList);
    assertTrue(ASSERT_STRING_MESSAGE, cli.errorEncountered);
    if (verboseMode) {
      System.out.println(result);
      System.out.println(OK_MESSAGE + CARRIAGE_RETURN);
    }
  }

  /**
   * Tests the getChartData method to check if the endday string comes after the startday string.
   * 
   * @throws Exception If a problem occurs.
   */
  @Test
  public void testgetChartDataInvalidPeriod() throws Exception {
    String command =
        "chart power generated SIM_KAHE_1 2009-11-02 2009-11-01 sampling-interval 45"
            + " file gen1.html";

    if (verboseMode) {
      System.out.println("> " + command);
      System.out.println("Checking if endday comes after startday...");
    }
    commandList = commandProcessor.parseCommand(command);
    cli = new CommandLineInterface();
    result = commandProcessor.processCommand(cli, commandList);
    assertTrue("Checking endday and startday strings", cli.errorEncountered);
    if (verboseMode) {
      System.out.println(result);
      System.out.println(OK_MESSAGE + CARRIAGE_RETURN);
    }
  }

  /**
   * Tests the getFileHTML method to check if the HTML file is not found.
   * 
   * @throws Exception If a problem occurs.
   */
  @Test
  public void testgetFileHTMLFileNotFound() throws Exception {
    String command =
        "chart power generated SIM_KAHE_1 2009-11-01 2009-11-02 sampling-interval 60"
            + " file test/gen2.html";

    if (verboseMode) {
      System.out.println("> " + command);
    }
    commandList = commandProcessor.parseCommand(command);
    cli = new CommandLineInterface();
    result = commandProcessor.processCommand(cli, commandList);
    assertTrue("Checking invalid HTML file", cli.errorEncountered);
    if (verboseMode) {
      System.out.println(result);
      System.out.println(OK_MESSAGE + CARRIAGE_RETURN);
    }
  }

  /**
   * Tests the getFileHTML method to check if the file name is valid.
   * 
   * @throws Exception If a problem occurs.
   */
  @Test
  public void testgetFileHTMLInvalidFileExt() throws Exception {
    String command =
        "chart power generated SIM_KAHE_1 2009-11-01 2009-11-02 sampling-interval 60"
            + " file gen.abc";

    if (verboseMode) {
      System.out.println("> " + command);
    }
    commandList = commandProcessor.parseCommand(command);
    cli = new CommandLineInterface();
    result = commandProcessor.processCommand(cli, commandList);
    assertTrue("Checking invalid HTML file", cli.errorEncountered);
    if (verboseMode) {
      System.out.println(result);
      System.out.println(OK_MESSAGE + CARRIAGE_RETURN);
    }
  }

  /**
   * Tests the getChartData method to check if the chart showing the power generated for a power
   * source is written to an HTML file.
   * 
   * @throws Exception If a problem occurs.
   */
  @Test
  public void testgetChartDataPowerGenerated() throws Exception {
    String command =
        "chart power generated SIM_KAHE_1 2009-11-01 2009-11-03 sampling-interval 35"
            + " file gen3.html";

    if (verboseMode) {
      System.out.println("> " + command);
    }
    commandList = commandProcessor.parseCommand(command);
    cli = new CommandLineInterface();
    result = commandProcessor.processCommand(cli, commandList);
    File genFile = new File("gen3.html");
    assertTrue("Checking existence of file", genFile.exists());
    if (verboseMode) {
      System.out.println(result);
      System.out.println(OK_MESSAGE + CARRIAGE_RETURN);
    }
  }

  /**
   * Tests the getChartData method to check if the chart showing the power consumed for a power
   * source is written to an HTML file.
   * 
   * @throws Exception If a problem occurs.
   */
  @Test
  public void testgetChartDataPowerConsumed() throws Exception {
    String command =
        "chart power consumed SIM_KAHE_1 2009-11-03 2009-11-04 sampling-interval 25 "
            + "file test1.html";

    if (verboseMode) {
      System.out.println("> " + command);
    }
    commandList = commandProcessor.parseCommand(command);
    cli = new CommandLineInterface();
    result = commandProcessor.processCommand(cli, commandList);
    File conFile = new File("test1.html");
    assertTrue("Checking existence of file", conFile.exists());
    if (verboseMode) {
      System.out.println(result);
      System.out.println(OK_MESSAGE + CARRIAGE_RETURN + CARRIAGE_RETURN);
    }
  }

  /**
   * Tests the getChartData method to check if the command is valid.
   * 
   * @throws Exception If a problem occurs.
   */
  @Test
  public void testgetChartDataInvalidCommand() throws Exception {
    String command =
        "chart power generates SIM_KAHE_1 2009-11-03 2009-11-04 sampling-interval 25 "
            + "file test2.html";
    if (verboseMode) {
      System.out.println("> " + command);
    }
    commandList = commandProcessor.parseCommand(command);
    cli = new CommandLineInterface();
    commandProcessor.processCommand(cli, commandList);
    assertTrue("Checking invalid command", cli.errorEncountered);
    if (verboseMode) {
      System.out.println(OK_MESSAGE + CARRIAGE_RETURN + CARRIAGE_RETURN);
    }
  }

  /**
   * Tests the getChartData method to check if the command is valid.
   * 
   * @throws Exception If a problem occurs.
   */
  @Test
  public void testgetChartDataNotEnoughArguments() throws Exception {
    String command = "chart power generated SIM_KAHE_1 2009-11-03 2009-11-04 file test3.html";
    if (verboseMode) {
      System.out.println("> " + command);
    }
    commandList = commandProcessor.parseCommand(command);
    commandProcessor.processCommand(cli, commandList);
    assertTrue("Checking invalid command", cli.errorEncountered);
    if (verboseMode) {
      System.out.println(OK_MESSAGE + CARRIAGE_RETURN + CARRIAGE_RETURN);
    }
  }

}