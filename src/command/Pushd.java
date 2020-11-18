package command;

import exception.ArityMismatchException;
import fs.FileSystem;
import java.io.Serializable;
import util.DirectoryStack;
import util.IO;

/**
 * The class which making directory traversals.
 *
 * @author Sihao Chen sihao.chen@mail.utoronto.ca
 */
public class Pushd implements Command, Serializable {

  private FileSystem fs;

  /**
   * JShell's directoryStack.
   */
  private DirectoryStack directoryStack;

  /**
   * The texts which used for documentation.
   */
  private String doc
      = "NAME\n"
      + "    pushd - Saves the current working directory by pushing\n"
      + "    onto directory stack and then changes the new current\n"
      + "    working directory to DIR.\n\n "
      + "SYNOPSIS\n"
      + "    pushd DIR\n\n"
      + "DESCRIPTION\n"
      + "    Push the current directory into directoryStack before print\n"
      + "    a list of directories in the stack.\n";

  /**
   * Init Pushed with variable in JShell.
   *
   * @param ds JShell's DirectoryStack
   *        wd JShell's WorkingDirectory
   */
  public Pushd(DirectoryStack ds, FileSystem fs) {
    this.fs = fs;
    directoryStack = ds;
  }

  /**
   * Get documentation for Pushd.
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
      throw new ArityMismatchException("Cannot push directory");
    }
    String curDir = fs.getWorkingDirectory();
    fs.setWorkingDirectory(args[1]);
    directoryStack.push(curDir);
  }
}
