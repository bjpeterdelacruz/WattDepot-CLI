package org.wattdepot.cli.command;

/**
 * Contains constant strings used to display messages during testing. Although not abstract, class
 * cannot be instantiated.
 * 
 * @author BJ Peter DeLaCruz, Wahib Hanani, Lyneth Peou
 * @version 2.0
 */
public class CliCommandTest {

  /** Carriage return on this operating system. */
  protected static final String CARRIAGE_RETURN = System.getProperty("line.separator");
  /** Success message that is displayed when a test passes. */
  protected static final String OK_MESSAGE = "OK.";
  /**
   * Message that is displayed when a test that checks to see if a power source does not exist or is
   * unavailable runs.
   */
  protected static final String CHECKING_SOURCE_MESSAGE =
      "Checking if power source does not exist or is unavailable... ";
  /** Check command for invalid day string. */
  protected static final String ASSERT_DAY_MESSAGE = "Checking invalid day";
  /** Check command for invalid power source. */
  protected static final String ASSERT_SOURCE_MESSAGE = "Checking invalid power source";
  /** Check command for invalid string. */
  protected static final String ASSERT_STRING_MESSAGE = "Checking invalid string";
  /** Check command for invalid timestamp string. */
  protected static final String ASSERT_TIMESTAMP_MESSAGE = "Checking invalid timestamp";
  /** If true, prints detailed information as to what the current test is doing. */
  protected boolean verboseMode = false;

  /** Protected constructor to prevent this class from being instantiated. */
  protected CliCommandTest() {
    // empty constructor, not to be used for any other purpose besides testing
  }

}