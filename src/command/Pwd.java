package command;

import exception.ArityMismatchException;
import fs.FileSystem;
import java.io.Serializable;
import util.IO;

/**
 * The Command class to print current working directory.
 *
 * @author Ziheng Zhuang ziheng.zhuang@mail.utoronto.ca
 * @version 1.2
 * @since 1.2
 */
public class Pwd implements Command, Serializable {

  private FileSystem fs;

  /**
   * The documentation for Pwd.
   */
  private String doc
      = "NAME\n"
      + "    pwd - print the current working directory\n"
      + "\n"
      + "SYNOPSIS\n"
      + "    pwd\n"
      + "\n"
      + "DESCRIPTION\n"
      + "    Print the current working directory to the screen\n";

  /**
   * Return the detailed document of pwd command.
   *
   * @return The man page
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
    io.printLine(fs.getWorkingDirectory());
  }

  /**
   * Init Command with workingDirectory.
   *
   * @param fs The file system
   */
  public Pwd(FileSystem fs) {
    this.fs = fs;
  }
}
