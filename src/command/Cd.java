package command;

import fs.FileSystem;
import java.io.Serializable;
import util.IO;
import exception.ArityMismatchException;

/**
 * The class to change directory.
 *
 * @author Yining Wang wyn.wang@mail.utoronto.ca
 */
public class Cd implements Command, Serializable {

  private FileSystem fs;

  private String doc
      = "NAME\n"
      + "    cd - change directories\n\n"
      + "SYNOPSIS\n"
      + "    cd DIR\n\n"
      + "DESCRIPTION\n"
      + "    Change the DIRECTORY to target place,\n"
      + "    which may be relative to the current directory\n"
      + "    or may be a full path.\n";

  /**
   * Initialize cd with workingDirectory.
   *
   * @param fs The file system
   */
  public Cd(FileSystem fs) {
    this.fs = fs;
  }

  /**
   * Return the documentation of command cd.
   *
   * @return the string of cd documentation
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
    fs.setWorkingDirectory(args[1]);
  }
}
