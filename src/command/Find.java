package command;

import exception.ArityMismatchException;
import exception.InvalidFormatException;
import fs.FileSystem;
import java.io.Serializable;
import util.ArgParser;
import util.IO;
import java.util.Set;

/**
 * The class implement the function of command Find.
 *
 * @author Sihao Chen sihao.chen@mail.utoronto.ca
 */
public class Find implements Command, Serializable {

  private FileSystem fs;

  private String doc
      = "NAME\n"
      + "    find - search for files in a directory hierarchy\n\n"
      + "SYNOPSIS\n"
      + "    find PATH ... -type [f|d] -name STRING\n\n"
      + "DESCRIPTION\n"
      + "    Find files or directories under PATH(s) with the given name\n"
      + "    STRING with a given option type for choosing regular files\n"
      + "    or directories.\n";

  private String erroMsg = "cannot find";

  /**
   * Init Find class with JShell's fileSystem.
   *
   * @param fs JShell's fileSystem
   */
  public Find(FileSystem fs) {
    this.fs = fs;
  }

  /**
   * Get documentation of command find.
   *
   * @return the documentation
   */
  @Override
  public String getDoc() {
    return doc;
  }

  private static String getName(String fullPath) {
    // Assume the pathname is valid
    String[] nameList = fullPath.split("/");
    return nameList[nameList.length - 1];
  }

  private void checkArguments(String[] args) throws Exception {
    int len = args.length;

    if (len < 6) {
      // Exception of invalid number of arguments
      throw new ArityMismatchException(erroMsg);
    }

    if (!args[len - 4].equals("-type") || !args[len - 2].equals("-name")) {
      // Exception of invalid argument format
      throw new InvalidFormatException(erroMsg);
    }

    if (!ArgParser.isString(args[len - 1])) {
      // Exception of name not a string
      throw new InvalidFormatException(erroMsg);
    }

    if (!args[len - 3].equals("f") && !args[len - 3].equals("d")) {
      // Exception of not valid type
      throw new InvalidFormatException(erroMsg);
    }
  }

  /**
   * Execute find command.
   *
   * @param args Arguments that passed to the command
   * @param io JShell's io
   * @throws Exception if arguments are not valid
   */
  @Override
  public void exec(String[] args, IO io) throws Exception {
    int len = args.length;

    checkArguments(args);

    String name = ArgParser.getContent(args[len - 1]);

    for (int i = 1; i < len - 4; i++) {
      try {
        Set<String> files = fs.getDirectoryContentFullPath(args[i]);
        for (String file : files) {
          if (fs.isDirectory(file)) {
            // Call exec() recursively
            String[] subArgs = {"find", file, args[len - 4], 
                args[len - 3], args[len - 2], args[len - 1]};
            exec(subArgs, io);
            if (args[len - 3].equals("d") && getName(file).equals(name)) {
              io.printLine(file);
            }
          } else if (fs.isRegularFile(file)
              && args[len - 3].equals("f") && getName(file).equals(name)) {
            io.printLine(file);
          }
        }
      } catch (Exception e) {
        io.setErr();
        io.printLine(args[i] + " is not a valid directory");
      }
    }
  }
}
