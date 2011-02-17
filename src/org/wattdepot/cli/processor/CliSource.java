package org.wattdepot.cli.processor;

import org.wattdepot.cli.CommandLineInterface;
import org.wattdepot.cli.command.CliCommandInterface;
import org.wattdepot.client.ResourceNotFoundException;
import org.wattdepot.resource.source.jaxb.Source;

/**
 * Returns a Source object given the name of a power source.
 * 
 * @author BJ Peter DeLaCruz, Wahib Hanani, Lyneth Peou
 * @version 2.0
 */
public class CliSource {

  /**
   * Creates an object that can be used to return a Source object given the name of a power source.
   */
  public CliSource() {
    // empty constructor
  }

  /**
   * Returns a Source object given the name of a power source.
   * 
   * @param cli A CommandLineInterface object.
   * @param source The name of a public power source that is available on this server.
   * @return A Source object representing a public power source.
   */
  public final Source getSource(CommandLineInterface cli, String source) {
    try {
      return cli.CLIENT.getSource(source);
    }
    catch (ResourceNotFoundException rnfe) {
      if (cli.debuggingMode) {
        cli.errorEncountered = true;
      } // end if
      return null;
    } // end catch
    catch (Exception e) {
      System.out.println(CliCommandInterface.ERROR_MESSAGE + CliCommandInterface.CARRIAGE_RETURN
          + e.toString() + CliCommandInterface.CARRIAGE_RETURN + CliCommandInterface.EXIT_MESSAGE);
      cli.errorEncountered = true;
      return null;
    }
  }

}