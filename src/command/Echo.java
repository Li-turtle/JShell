package command;

import exception.ArityMismatchException;
import exception.InvalidFormatException;
import fs.FileSystem;
import java.io.Serializable;
import util.ArgParser;
import util.IO;

/**
 * The class implement the function of command echo.
 *
 * @author Sihao Chen sihao.chen@mail.utoronto.ca
 */
public class Echo implements Command, Serializable {

  private FileSystem fs;

  /**
   * The texts which used for documentation.
   */
  private String doc
      = "NAME\n"
      + "    echo - display a line of text\n\n"
      + "SYNOPSIS\n"
      + "    echo STRING [> OUTFILE]\n"
      + "    echo STRING >> OUTFILE\n\n"
      + "DESCRIPTION\n"
      + "    Echo the STRING to standard output if no more arguments\n"
      + "    given. If '>' is given, then write STRING to OUTFILE. IF '>>'\n"
      + "    is given, then append STRING to OUTFILE. If OUTFILE does not\n"
      + "    exist, create a new file named OUTFILE.\n";

  /**
   * Init Echo class.
   *
   * @param fs The file system
   */
  public Echo(FileSystem fs) {
    this.fs = fs;
  }

  /**
   * Get the documentation of echo command.
   *
   * @return the documentation of echo
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
    if (!ArgParser.isString(args[1])) {
      throw new InvalidFormatException(args[1]);
    }
    io.printLine(ArgParser.getContent(args[1]));
  }
}
