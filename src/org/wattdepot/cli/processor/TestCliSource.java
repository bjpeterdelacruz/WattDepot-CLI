package org.wattdepot.cli.processor;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.wattdepot.cli.CommandLineInterface;
import org.wattdepot.cli.command.CliCommandTest;
import org.wattdepot.resource.source.jaxb.Source;

/**
 * Tests the getSource method in the CliSource class.
 * 
 * @author BJ Peter DeLaCruz, Wahib Hanani, Lyneth Peou
 * @version 2.0
 */
public class TestCliSource extends CliCommandTest {
  private CliSource cliSource;

  // private boolean verboseMode = true;

  /**
   * Tests the getSource method in the CliSource class.
   * 
   * @throws Exception If a problem occurs.
   */
  @Before
  @Test
  public void testGetSourceValidSource() throws Exception {
    cliSource = new CliSource();
    Source powerSourceKahe = cliSource.getSource(new CommandLineInterface(), "SIM_KAHE_1");
    if (verboseMode) {
      System.out.println(CARRIAGE_RETURN + "Checking getSource method..." + CARRIAGE_RETURN);
      System.out.println("Checking if power source SIM_KAHE_1 exists...");
    }
    assertEquals("Checking source name", "SIM_KAHE_1", powerSourceKahe.getName());
    if (verboseMode) {
      System.out.println(OK_MESSAGE + CARRIAGE_RETURN);
    }
  }

  /**
   * Tests the getSource method in the CliSource class.
   * 
   * @throws Exception If a problem occurs.
   */
  @Test
  public void testGetSourceInvalidSource() throws Exception {
    // SEA = Seattle
    if (verboseMode) {
      System.out.println("Checking if power source SIM_SEA exists...");
    }
    Source powerSourceSea = cliSource.getSource(new CommandLineInterface(), "SIM_SEA");
    assertEquals("Checking source name", null, powerSourceSea);
    if (verboseMode) {
      System.out.println(CARRIAGE_RETURN + OK_MESSAGE + CARRIAGE_RETURN);
    }
  }

}