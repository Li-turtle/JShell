package test.command;

import static org.junit.Assert.*;

import command.History;
import exception.ArityMismatchException;
import exception.InvalidFormatException;
import org.junit.Before;
import org.junit.Test;
import util.CommandHistory;
import util.IO;


public class HistoryTest {
  private History history;
  private CommandHistory commandHistory;
  private IO io;

  /**
   * setUp method of this JUnitTest.
   *
   * @throws Exception various Exception
   */
  @Before
  public void setUp() throws Exception {
    commandHistory = new CommandHistory();
    history = new History(commandHistory);
    io = new IO();
  }

  @Test
  public void testGetDoc() {
    // Test whether getDoc() returns correctly the documentation
    String rst = history.getDoc();
    String expected
        = "NAME\n"
        + "    history - print recent commands\n\n"
        + "SYNOPSIS\n"
        + "    history [NUMBER]\n\n"
        + "DESCRIPTION\n"
        + "    The output contains two columns. The first column is the\n"
        + "    number such that the line with the highest number is the most\n"
        + "    recent commands. The second column is the actual command. \n";
    assertEquals(expected, rst);
  }

  @Test(expected = ArityMismatchException.class)
  public void testExecFileArityMismatchException() throws Exception {
    // Exception of invalid number of arguments
    history.exec(new String[]{"history", "0", "1"}, io);
  }

  @Test(expected = InvalidFormatException.class)
  public void testExecFileInvalidFormatException() throws Exception {
    // Exception of invalid argument
    history.exec(new String[]{"history", "-1"}, io);
  }

  @Test
  public void testExecNoArg() throws Exception {
    // history with no arg
    commandHistory.add("mkdir a");
    commandHistory.add("ls");
    commandHistory.add("cd a");
    commandHistory.add("history");
    history.exec(new String[]{"history"}, io);
    assertEquals("1. mkdir a\n"
        + "2. ls\n"
        + "3. cd a\n"
        + "4. history"
        + "\n", io.flush());
  }

  @Test
  public void testExecOneArg() throws Exception {
    // history with one arg
    commandHistory.add("mkdir a");
    commandHistory.add("ls");
    commandHistory.add("cd a");
    commandHistory.add("history");
    history.exec(new String[]{"history", "2"}, io);
    assertEquals("3. cd a\n"
        + "4. history"
        + "\n", io.flush());
  }
}
