package command;

import exception.ArityMismatchException;
import exception.InvalidFormatException;
import java.io.Serializable;
import java.util.Map;
import util.IO;

/**
 * The class that print the man page of commandse.
 *
 * @author Ziheng Zhuang ziheng.zhuang@mail.utoronto.ca
 * @version 1.4
 * @since 1.4
 */
public class Man implements Command, Serializable {

  /**
   * The documentation of Man command.
   */
  private String doc
      = "NAME\n"
      + "    man - list the documentation of commands\n"
      + "\n"
      + "SYNOPSIS\n"
      + "    man COMMAND\n"
      + "\n"
      + "DESCRIPTION\n"
      + "    Print the documentation of COMMAND\n";

  /**
   * The map that maps command name to command objects.
   */
  private Map<String, Command> dict;

  /**
   * Init Command with workingDirectory.
   */
  public Man(Map<String, Command> dict) {
    this.dict = dict;
  }

  /**
   * Get Documentation for the command Man.
   *
   * @return The string of documentation
   */
  @Override
  public String getDoc() {
    return doc;
  }

  /**
   * Execute the command.
   *
   * @param args Arguments that passed to the command
   * @param io IO object
   * @throws Exception various exceptions
   */
  @Override
  public void exec(String[] args, IO io) throws Exception {
    if (args.length != 2) {
      throw new ArityMismatchException();
    }
    if (!dict.containsKey(args[1])) {
      throw new InvalidFormatException("Command not found");
    }
    io.printLine(dict.get(args[1]).getDoc());
  }
}
