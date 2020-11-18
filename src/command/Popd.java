package command;

import exception.ArityMismatchException;
import exception.StackEmptyException;
import fs.FileSystem;
import java.io.Serializable;
import util.DirectoryStack;
import util.IO;

/**
 * The Command class to remove the top entry and cd into it.
 *
 * @author jiayu lu jiay.lu@mail.utoronto.ca
 */
public class Popd implements Command, Serializable {

  private FileSystem fs;

  /**
   * The instance variable that holds directory stack.
   */
  private DirectoryStack directoryStack;

  /**
   * The documentation of Popd.
   */
  private String doc
      = "NAME\n"
      + "    popd - Remove the top entry from the directory stack, "
      + "and cd into it.\n\n"
      + "SYNOPSIS\n"
      + "    popd \n\n"
      + "DESCRIPTION\n"
      + "    The popd command removes the top most directory from the\n"
      + "    directory stack and makes it the current working directory.\n"
      + "    If there is no directory onto the stack, then give\n"
      + "    appropriate error message."
      + "\n";

  /**
   * Init Popd command with directory stack and working directory.
   *
   * @param ds The directory stack
   *        ws The working directory
   */
  public Popd(DirectoryStack ds, FileSystem fs) {
    this.fs = fs;
    directoryStack = ds;
  }

  /**
   * Get Documentation for the command Popd.
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
    if (args.length != 1) {
      throw new ArityMismatchException();
    }
    if (directoryStack.isEmpty()) {
      throw new StackEmptyException();
    }
    fs.setWorkingDirectory(directoryStack.pop());
  }
}
