package command;

import exception.ArityMismatchException;
import fs.FileSystem;
import java.io.Serializable;
import util.IO;

/**
 * The class to copy contents from old path to new path
 *
 * @author Ziheng Zhuang ziheng.zhuang@mail.utoronto.ca
 * @version 1.9
 * @since 1.8
 */
public class Cp implements Command, Serializable {

  private FileSystem fs;

  private String doc
      = "NAME\n"
      + "    cp - Copy content at old path to new path\n\n"
      + "SYNOPSIS\n"
      + "    cp OLDPATH NEWPATH\n\n"
      + "DESCRIPTION\n"
      + "    Copy the content at old path to new path.\n"
      + "    If path represents file, copy the content.\n"
      + "    If path represents directory, recursively copy\n"
      + "    content inside directory.\n";

  /**
   * Initialize Cp command with filesystem object
   *
   * @param fs The filesystem object
   */
  public Cp(FileSystem fs) {
    this.fs = fs;
  }

  /**
   * Get Documentation for the command.
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
   * @param io IO class
   * @throws Exception various exceptions
   */
  @Override
  public void exec(String[] args, IO io) throws Exception {
    if (args.length != 3) {
      throw new ArityMismatchException("Cp");
    }

    String oldPath = args[1];
    String newPath = args[2];

    if (fs.isDirectory(oldPath)) {
      // If source path is a directory, then copy the directory
      fs.duplicateDirectory(oldPath, newPath);
    } else if (fs.isRegularFile(oldPath)) {
      // If source path is a regular file, then copy the file
      fs.duplicateRegularFile(oldPath, newPath);
    } else {
      // Throw the error because source path is invalid
      fs.verifyPath(oldPath);
    }
  }
}
