package command;

import exception.ArityMismatchException;
import fs.FileSystem;
import java.io.Serializable;
import util.IO;

/**
 * The class to terminate the program.
 *
 * @author Yining Wang wyn.wang@mail.utoronto.ca
 */
public class Exit implements Command, Serializable {

  private FileSystem fs;

  /**
   * The documentation of Exit.
   */
  private String doc
      = "NAME\n"
      + "    exit - cause normal process termination\n\n"
      + "SYNOPSIS\n"
      + "    exit\n\n"
      + "DESCRIPTION\n"
      + "    terminate the program\n";

  /**
   * Initialize Exit with workingDirectory.
   *
   * @param fs The file system
   */
  public Exit(FileSystem fs) {
    this.fs = fs;
  }

  /**
   * Return the documentation of command exit.
   *
   * @return the string of exit documentation
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
    if (args.length != 1) {
      throw new ArityMismatchException("Cannot exit");
    }
    IO.writeLine("See you next time");
    System.exit(0);
  }
}
