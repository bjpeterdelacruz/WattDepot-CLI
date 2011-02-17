package org.wattdepot.cli;

import org.wattdepot.cli.processor.CommandProcessor;
import org.wattdepot.client.WattDepotClient;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

/**
 * A command-line program that displays information about public power sources on the island of Oahu
 * in Hawaii.
 * 
 * @author BJ Peter DeLaCruz, Wahib Hanani, Lyneth Peou
 * @version 2.0
 */
public class CommandLineInterface {
  /** The URL to the public WattDepot server. */
  public String HOST_URI = "http://server.wattdepot.org:8182/wattdepot/";
  /** Holds an instance of the WattDepot client. */
  public WattDepotClient CLIENT = new WattDepotClient(HOST_URI);
  /** Indicates whether an error is encountered when processing a command. */
  public boolean errorEncountered = false;
  /** For debugging purposes only. */
  public boolean debuggingMode = true;
  /** For exiting the system. */
  public boolean exit = false;

  /**
   * Connects to the WattDepot service. Returns immediately if a connection to the service could not
   * be obtained.
   * 
   * @throws Exception If a connection cannot be made.
   */
  public CommandLineInterface() throws Exception {

    // Stop right away if server cannot be contacted.
    if (!CLIENT.isHealthy()) {
      System.out.print("Failed to connect to " + CLIENT.getWattDepotUri());
      throw new Exception();
    }

  }

  /**
   * The main program that is invoked by the command 'java -jar wattdepot-cli-ekolu.jar'.
   * 
   * @param args URL of the WattDepot server.
   * @throws Exception If a connection to the server cannot be made.
   * @throws Exception If there is a problem reading in a command.
   */
  public static void main(String[] args) throws Exception {
    String command = "";
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    System.out.println("Starting CLI.");
    CommandLineInterface cli = null;
    try {
      cli = new CommandLineInterface();
      if (args.length > 0) {
        cli.CLIENT = new WattDepotClient(args[0]);
        if (!cli.CLIENT.isHealthy()) {
          System.out.print("Failed to connect to " + cli.CLIENT.getWattDepotUri());
          System.exit(1);
        } // end if
      } // end if
      cli.debuggingMode = false;
      System.out.println("Connected to " + cli.CLIENT.getWattDepotUri());
    } // end try
    catch (Exception e) {
      System.out.println("Error encountered when trying to connect to server.");
      return;
    }

    CommandProcessor commandProcessor = new CommandProcessor();

    List<String> commandList = null;
    String result = "";
    while (true) {
      System.out.print("> ");
      try {
        if ((command = br.readLine()) == null) {
          throw new Exception();
        } // end if
      } // end try
      catch (Exception e) {
        result = "Error encountered when trying to read in command.";
        System.out.println(result);
        System.out.println("Exiting CLI.");
        break;
      } // end catch

      commandList = commandProcessor.parseCommand(command);

      result = commandProcessor.processCommand(cli, commandList);

      if (cli.errorEncountered || cli.exit) {
        if (cli.errorEncountered) {
          System.out.println(result);
        } // end if
        System.out.println("Exiting CLI.");
        break;
      } // end if

      System.out.println(result);
    } // end while
  }

}