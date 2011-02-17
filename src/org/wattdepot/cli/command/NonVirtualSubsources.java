package org.wattdepot.cli.command;

import java.util.List;
import org.wattdepot.cli.CommandLineInterface;
import org.wattdepot.cli.processor.CliSource;
import org.wattdepot.resource.source.jaxb.Source;

/**
 * Returns a list of all power sources that are subsources of a given virtual power source.
 * 
 * @author BJ Peter DeLaCruz, Wahib Hanani, Lyneth Peou
 * @version 2.0
 */
public class NonVirtualSubsources {

  /**
   * Private constructor to prevent this class from being instantiated.
   */
  private NonVirtualSubsources() {
    // empty constructor
  }

  /**
   * Returns a list of all power sources that are subsources of a given virtual power source.
   * 
   * @param cli A CommandLineInterface object.
   * @param source A virtual power source.
   * @param subsources An empty list that will eventually contain non-virtual power sources.
   * @return A list of non-virtual power sources.
   */
  public static List<Source> getSubsources(CommandLineInterface cli, Source source,
      List<Source> subsources) {
    for (String subsource : source.getSubSources().getHref()) {
      Source subSource =
          (new CliSource()).getSource(cli, subsource.substring(subsource.indexOf("SIM")));
      if (subSource.isVirtual()) {
        getSubsources(cli, subSource, subsources);
      } // end if
      else {
        subsources.add(subSource);
      } // end else
    } // end for
    return subsources;
  }

}