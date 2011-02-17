package org.wattdepot.cli.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.wattdepot.cli.CommandLineInterface;
import org.wattdepot.cli.processor.CommandProcessor;

/**
 * Tests the listPowerDayStatistic method to see if power source exists on this server; timestamp,
 * sampling-interval, and statistic strings are valid; and maximum, minimum, and average statistic
 * values are returned.
 * 
 * @author BJ Peter DeLaCruz, Wahib Hanani, Lyneth Peou
 * @version 2.0
 */
public class TestPowerStatsDay extends CliCommandTest {
  private CommandProcessor commandProcessor;
  private static final String statMax = "statistic max";
  private List<String> commandList;
  private CommandLineInterface cli;
  private String result;
  private String command = null;

  // private boolean verboseMode = true;

  /**
   * Tests the listPowerDayStatistic method to see if power source exists on this server.
   * 
   * @throws Exception If a problem occurs.
   */
  @Before
  @Test
  public void testListPowerDayStatisticSource() throws Exception {
    command = "powerstats generated SIM_SEA day 2009-11-01 sampling-interval 120 " + statMax;
    commandProcessor = new CommandProcessor();
    commandList = commandProcessor.parseCommand(command);
    cli = new CommandLineInterface();
    commandProcessor.processCommand(cli, commandList);
    assertTrue(ASSERT_SOURCE_MESSAGE, cli.errorEncountered);
  }

  /**
   * Tests the listPowerDayStatistic method to see if day string is valid.
   * 
   * @throws Exception If a problem occurs.
   */
  @Test
  public void testListPowerDayStatisticDay() throws Exception {
    command = "powerstats generated SIM_KAHE_1 day 11-01-2009 sampling-interval 120 " + statMax;
    if (verboseMode) {
      System.out.println(CARRIAGE_RETURN + "> " + command);
      System.out.println("Checking if day is not a valid string...");
    }
    commandList = commandProcessor.parseCommand(command);
    cli.errorEncountered = false;
    commandProcessor.processCommand(cli, commandList);
    assertTrue(ASSERT_DAY_MESSAGE, cli.errorEncountered);
    if (verboseMode) {
      System.out.println(result);
      System.out.println(OK_MESSAGE);
    }
  }

  /**
   * Tests the listPowerDayStatistic method to see if sampling-interval string is valid.
   * 
   * @throws Exception If a problem occurs.
   */
  @Test
  public void testListPowerDayStatisticDaySamplingInterval() throws Exception {
    command = "powerstats generated SIM_KAHE_1 day 2009-11-01 sampling-interval lol " + statMax;
    if (verboseMode) {
      System.out.println(CARRIAGE_RETURN + "> " + command);
      System.out.println("Checking if sampling-interval is not a valid number...");
    }
    commandList = commandProcessor.parseCommand(command);
    cli.errorEncountered = false;
    commandProcessor.processCommand(cli, commandList);
    assertTrue(ASSERT_STRING_MESSAGE, cli.errorEncountered);
    if (verboseMode) {
      System.out.println(result);
      System.out.println(OK_MESSAGE);
    }
  }

  /**
   * Tests the listPowerDayStatistic method to see if statistic string is valid.
   * 
   * @throws Exception If a problem occurs.
   */
  @Test
  public void testListPowerDayStatisticDayStatistic() throws Exception {
    command =
        "powerstats generated SIM_KAHE_1 day 2009-11-01 sampling-interval 120 "
            + "statistic maximum";
    if (verboseMode) {
      System.out.println(CARRIAGE_RETURN + "> " + command);
      System.out.println("Checking if statistic is not a valid string...");
    }
    commandList = commandProcessor.parseCommand(command);
    cli.errorEncountered = false;
    commandProcessor.processCommand(cli, commandList);
    assertTrue(ASSERT_STRING_MESSAGE, cli.errorEncountered);
    if (verboseMode) {
      System.out.println(result);
      System.out.println(OK_MESSAGE);
    }
  }

  /**
   * Tests the listPowerDayStatistic method to see if maximum statistic value is returned.
   * 
   * @throws Exception If a problem occurs.
   */
  @Test
  public void testListPowerDayStatisticDayMaximum() throws Exception {
    command = "powerstats generated SIM_OAHU_GRID day 2009-11-01 sampling-interval 120 " + statMax;
    if (verboseMode) {
      System.out.println(CARRIAGE_RETURN + "> " + command);
      System.out.println("Checking if maximum statistic is returned...");
    }
    commandList = commandProcessor.parseCommand(command);
    cli.errorEncountered = false;
    commandProcessor.processCommand(cli, commandList);
    result = (new PowerStatsDay()).doCommand(cli, commandList);
    int max = Integer.parseInt(result.substring(0, result.indexOf('.')));
    assertTrue("Checking max value", max >= (Integer.MIN_VALUE + 1));
    if (verboseMode) {
      System.out.println(result);
      System.out.println(OK_MESSAGE);
    }
  }

  /**
   * Tests the listPowerDayStatistic method to see if minimum statistic value is returned.
   * 
   * @throws Exception If a problem occurs.
   */
  @Test
  public void testListPowerDayStatisticDayMinimum() throws Exception {
    command =
        "powerstats generated SIM_OAHU_GRID day 2009-11-02 sampling-interval 120 "
            + "statistic min";
    if (verboseMode) {
      System.out.println(CARRIAGE_RETURN + "> " + command);
      System.out.println("Checking if minimum statistic is returned...");
    }
    commandList = commandProcessor.parseCommand(command);
    cli.errorEncountered = false;
    commandProcessor.processCommand(cli, commandList);
    result = (new PowerStatsDay()).doCommand(cli, commandList);
    int min = Integer.parseInt(result.substring(0, result.indexOf('.')));
    assertTrue("Checking min value", min <= (Integer.MAX_VALUE - 1));
    if (verboseMode) {
      System.out.println(result);
      System.out.println(OK_MESSAGE);
    }
  }

  /**
   * Tests the listPowerDayStatistic method to see if average statistic value is returned.
   * 
   * @throws Exception If a problem occurs.
   */
  @Test
  public void testListPowerDayStatisticDayAverage() throws Exception {
    command =
        "powerstats generated SIM_OAHU_GRID day 2009-11-03 sampling-interval 120 "
            + "statistic average";
    if (verboseMode) {
      System.out.println(CARRIAGE_RETURN + "> " + command);
      System.out.println("Checking if average statistic is returned...");
    }
    commandList = commandProcessor.parseCommand(command);
    cli.errorEncountered = false;
    commandProcessor.processCommand(cli, commandList);
    result = (new PowerStatsDay()).doCommand(cli, commandList);
    int ave = Integer.parseInt(result.substring(0, result.indexOf('.')));
    assertTrue("Checking average value", ave <= (Integer.MAX_VALUE - 1)
        || ave >= (Integer.MIN_VALUE + 1));
    if (verboseMode) {
      System.out.println(result);
      System.out.println(OK_MESSAGE);
    }
  }

  /**
   * Tests the listPowerDayStatistic method to see if maximum statistic value is returned.
   * 
   * @throws Exception If a problem occurs.
   */
  @Test
  public void testListPowerDayStatisticDayNoDataMaximum() throws Exception {
    command = "powerstats consumed SIM_KAHE_1 day 2009-11-01 sampling-interval 60 " + statMax;
    if (verboseMode) {
      System.out.println(CARRIAGE_RETURN + "> " + command);
      System.out.println("Checking if maximum statistic is returned...");
    }
    commandList = commandProcessor.parseCommand(command);
    cli.errorEncountered = false;
    commandProcessor.processCommand(cli, commandList);
    result = (new PowerStatsDay()).doCommand(cli, commandList);
    assertEquals("Checking max value", "No data for SIM_KAHE_1 on 2009-11-01.", result);
    if (verboseMode) {
      System.out.println(result);
      System.out.println(OK_MESSAGE);
    }
  }

  /**
   * Tests the listPowerDayStatistic method to see if the value of sampling interval is positive.
   * 
   * @throws Exception If a problem occurs.
   */
  @Test
  public void testListPowerDayStatisticDayNegativeInterval() throws Exception {
    command = "powerstats consumed SIM_KAHE_1 day 2009-11-01 sampling-interval -60 " + statMax;
    if (verboseMode) {
      System.out.println(CARRIAGE_RETURN + "> " + command);
      System.out.println("Checking if maximum statistic is returned...");
    }
    commandList = commandProcessor.parseCommand(command);
    cli.errorEncountered = false;
    commandProcessor.processCommand(cli, commandList);
    assertTrue("Checking sampling interval", cli.errorEncountered);
    if (verboseMode) {
      System.out.println(result);
      System.out.println(OK_MESSAGE);
    }
  }

}