package command;

import exception.ArityMismatchException;
import exception.InvalidFormatException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import util.CommandHistory;
import util.IO;

/**
 * The class to print recent commands.
 *
 * @author Yining Wang wyn.wang@mail.utoronto.ca
 */
public class History implements Command, Serializable {

  /**
   * Documentation of History.
   */
  private String doc
      = "NAME\n"
      + "    history - print recent commands\n\n"
      + "SYNOPSIS\n"
      + "    history [NUMBER]\n\n"
      + "DESCRIPTION\n"
      + "    The output contains two columns. The first column is the\n"
      + "    number such that the line with the highest number is the most\n"
      + "    recent commands. The second column is the actual command. \n";

  /**
   * The variable that stores command history.
   */
  private CommandHistory commandHistory;

  /**
   * Initialize history with workingDirectory.
   *
   * @param commandHistory The command history
   */
  public History(CommandHistory commandHistory) {
    this.commandHistory = commandHistory;
  }

  /**
   * Return the documentation of command history.
   *
   * @return the string of history documentation
   */
  @Override
  public String getDoc() {
    return doc;
  }

  /**
   * Execution of command history.
   *
   * @param args the argument passed to history
   * @throws Exception incorrect number of args or invalid number
   */
  @Override
  public void exec(String[] args, IO io) throws Exception {
    if (args.length > 2) {
      // incorrect argument length
      throw new ArityMismatchException("history");
    } else if (args.length == 2) {
      // history NUMBER
      // if argument is not integer or negative, then throw exception
      try {
        int num = Integer.parseInt(args[1]);
        List<String> latestHistory = new ArrayList<>(
            commandHistory.getLatestHistory(num)
        );
        printHistory(
            latestHistory,
            Math.max(commandHistory.getAllHistory().size() - num, 0) + 1,
            io
        );
      } catch (Exception e) {
        throw new InvalidFormatException(args[1]);
      }
    } else {
      //history
      printHistory(commandHistory.getAllHistory(), 1, io);
    }
  }

  /**
   * Print the most recent commands.
   *
   * @param hist  the ArrayList that contains target commands
   * @param index the start index in the first column
   */
  private static void printHistory(List<String> hist, int index, IO io) {
    for (String sub : hist) {
      io.printLine(index + ". " + sub);
      index++;
    }
  }
}
