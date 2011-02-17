package org.wattdepot.cli.command;

import static org.junit.Assert.assertTrue;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.wattdepot.cli.CommandLineInterface;
import org.wattdepot.cli.processor.CommandProcessor;

/**
 * Tests the fuelTypes command to see if a power source exists and the fuel types of two power
 * sources are correct.
 * 
 * @author BJ Peter DeLaCruz, Wahib Hanani, Lyneth Peou
 * @version 2.0
 */
public class TestFuelTypes extends CliCommandTest {
  private CommandProcessor commandProcessor;
  private List<String> commandList;
  private CommandLineInterface cli;
  // private boolean verboseMode = true;
  private String command;

  /**
   * Tests the fuelTypes command to see if a power source exists.
   * 
   * @throws Exception If a problem occurs.
   */
  @Before
  @Test
  public void testFuelTypesInvalidSource() throws Exception {
    command = "fueltypes SIM_SEA";
    commandProcessor = new CommandProcessor();
    commandList = commandProcessor.parseCommand(command);

    cli = new CommandLineInterface();
    FuelTypes fuelTypes = new FuelTypes();
    fuelTypes.doCommand(cli, commandList);

    assertTrue("Checking power source", cli.errorEncountered);
  }

  /**
   * Tests the fuelTypes command to see if the fuel type of SIM_KAHE_1 is LSFO.
   * 
   * @throws Exception If a problem occurs.
   */
  @Test
  public void testFuelTypesAllSubsources() throws Exception {
    command = "fueltypes SIM_OAHU_GRID";
    commandProcessor = new CommandProcessor();
    commandList = commandProcessor.parseCommand(command);

    cli = new CommandLineInterface();

    if (verboseMode) {
      System.out.println("> " + command);
      System.out.println("Checking if fuel type of SIM_KAHE_1 is LSFO...");
    }

    FuelTypes fuelTypes = new FuelTypes();
    fuelTypes.doCommand(cli, commandList);
    String fuelType = fuelTypes.getSubsources().get("LSFO");
    assertTrue("Checking fuel type", fuelType.contains("SIM_KAHE_1"));
  }

  /**
   * Tests the fuelTypes command to see if the fuel type of SIM_KAHE_2 is LSFO.
   * 
   * @throws Exception If a problem occurs.
   */
  @Test
  public void testFuelTypesSubsource() throws Exception {
    command = "fueltypes SIM_KAHE_2";
    commandProcessor = new CommandProcessor();
    commandList = commandProcessor.parseCommand(command);

    cli = new CommandLineInterface();

    if (verboseMode) {
      System.out.println("> " + command);
      System.out.println("Checking if fuel type of SIM_KAHE_2 is LSFO...");
    }

    FuelTypes fuelTypes = new FuelTypes();
    String result = fuelTypes.doCommand(cli, commandList);
    assertTrue("Checking fuel type", result.contains("SIM_KAHE_2"));
  }

}