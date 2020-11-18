package command;

import exception.ArityMismatchException;
import fs.FileSystem;
import java.io.Serializable;
import util.IO;

/**
 * The class implement the function of command mkdir.
 *
 * @author Sihao Chen sihao.chen@mail.utoronto.ca Yining Wang wyn.wang@mail.utoronto.ca
 */

public class Mkdir implements Command, Serializable {

  private FileSystem fs;

  /**
   * The texts which used for documentation.
   */
  private String doc
      = "NAME\n"
      + "    mkdir - make directories\n\n"
      + "SYNOPSIS\n"
      + "    mkdir DIR ...\n\n"
      + "DESCRIPTION\n"
      + "    Create the DIRECTORY, if it does not already exist.\n"
      + "    The new directory may be relative to the current directory\n"
      + "    or may be a full path. mkdir can take in more than one DIR.\n";


  /**
   * Init Mkdir with workingDirectory.
   *
   * @param fs The file system
   */
  public Mkdir(FileSystem fs) {
    this.fs = fs;
  }


  /**
   * Get the documentation of command mkdir.
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
   * @param io   IO object
   * @throws Exception various exceptions
   */
  @Override
  public void exec(String[] args, IO io) throws Exception {
    if (args.length == 1) {
      throw new ArityMismatchException("mkdir");
    }
    for (int i = 1; i < args.length; i++) {
      fs.createDirectory(args[i]);
    }
  }
}
