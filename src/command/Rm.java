package command;

import exception.ArityMismatchException;
import exception.InvalidFileException;
import fs.FileSystem;
import java.io.Serializable;
import util.IO;

/**
 *  removes the DIR from the file system.
 * 
 * @author Jiayu Lu jiay.lu@mail.utoronto.ca
 */
public class Rm implements Command, Serializable {

  private FileSystem fs;

  private String doc
      = "NAME\n"
      + "    rm -  removes the DIR from the file system\n\n"
      + "SYNOPSIS\n"
      + "    rm DIR \n\n"
      + "DESCRIPTION\n"
      + "    removes the DIR from the file system\n"
      + "    throws an error if there is no such DIR.\n";

  /**
   * Init Rm with working directory.
   *
   * @param fs The current file system
   */
  public Rm(FileSystem fs) {
    this.fs = fs;
  }

  /**
   * The method that returns man page of Rm.
   *
   * @return Documentation
   */
  @Override
  public String getDoc() {
    return doc;
  }

  /**
   * Execute the command.
   *
   * @param args Arguments that passed to the command.
   * @param io IO object
   * @throws Exception ArityMismatchException
   */
  @Override
  public void exec(String[] args, IO io) throws Exception {
    if (args.length != 2) {
      throw new ArityMismatchException();
    }
    if (fs.isRegularFile(args[1])) {
      throw new InvalidFileException("Not a directory");
    }
    fs.removeFile(args[1]);
  }
}

