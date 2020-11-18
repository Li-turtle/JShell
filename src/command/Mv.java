package command;

import exception.ArityMismatchException;
import exception.FileNotExistException;
import fs.FileSystem;
import java.io.Serializable;
import util.IO;

/**
 * The class implement the function of command mv.
 *
 * @author Sihao Chen sihao.chen@mail.utoronto.ca
 */

public class Mv implements Command, Serializable {

  private FileSystem fs;

  private String doc
      = "NAME\n"
      + "    mv - move (rename) files\n\n"
      + "SYNOPSIS\n"
      + "    mv OLDPATH NEWPATH\n\n"
      + "DESCRIPTION\n"
      + "    Rename OLDPATH to NEWPATH if OLDPATH and NEWPATH\n"
      + "    have same type or NEWPATH not created, or move OLDPATH\n"
      + "    to NEWPATH if NEWPATH is regular file and NEWPATH\n"
      + "    is directory.\n";

  private String erroMsg = "cannot move";

  /**
   * Init Mv class with JShell's file system passed in.
   *
   * @param fs JShell's existing file system
   */
  public Mv(FileSystem fs) {
    this.fs = fs;
  }

  /**
   * Get documentation of mv command.
   *
   * @return the documentation of mv command
   */
  @Override
  public String getDoc() {
    return doc;
  }

  /**
   * Execute mv command.
   *
   * @param args Arguments that passed to the command
   * @param io JShell's IO
   * @throws Exception exception of source and dest not compatible or invalid
   */
  @Override
  public void exec(String[] args, IO io) throws Exception {
    // Exception of invalid number of arguments
    if (args.length != 3) {
      throw new ArityMismatchException(erroMsg);
    }

    if (fs.removable(args[1])) {
      if (fs.isRegularFile(args[1])) {
        fs.duplicateRegularFile(args[1], args[2]);
      } else if (fs.isDirectory(args[1])) {
        fs.duplicateDirectory(args[1], args[2]);
      } else {
        throw new FileNotExistException(args[1]);
      }
      fs.removeFile(args[1]);
    }
  }
}
