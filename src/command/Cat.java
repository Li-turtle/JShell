package command;

import exception.ArityMismatchException;
import fs.FileSystem;
import java.io.Serializable;
import util.IO;

/**
 * The Command class to Display the contents of
 * files (with concatenation) in the JShell.
 *
 * @author jiayu lu jiay.lu@mail.utoronto.ca
 */
public class Cat implements Command, Serializable {

  private FileSystem fs;

  private String doc
      = "NAME\n"
      + "    cat - Display the contents of FILE or FILEs\n\n"
      + "SYNOPSIS\n"
      + "    cat FILE1 [FILE2 ...] \n\n"
      + "DESCRIPTION\n"
      + "    Display the contents of FILE or FILEs concatenated in the\n"
      + "    shell. There are three line breaks separating the contents of\n"
      + "    FILEs from the others. If FILE is invalid, display an error.\n";

  /**
   * Init Cat with working directory.
   *
   * @param fs The current file system
   */
  public Cat(FileSystem fs) {
    this.fs = fs;
  }

  /**
   * The method that returns man page of cat.
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
   * @param args Arguments that passed to the command
   * @param io IO object
   * @throws Exception various exceptions
   */
  @Override
  public void exec(String[] args, IO io) throws Exception {
    if (args.length == 1) {
      throw new ArityMismatchException();
    }
    for (int i = 1; i < args.length; i++) {
      if (i >= 2) {
        io.print("\n\n\n");
      }
      if (fs.isDirectory(args[i])) {
        io.printLine(args[i] + ": Not a regular file");
        io.setErr();
        continue;
      }
      try {
        io.print(fs.getFileContent(args[i]));
      } catch (Exception e) {
        io.setErr();
        io.printLine(args[i] + ": File Not Exist");
      }
    }
  }
}
