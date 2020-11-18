package command;

import exception.ArityMismatchException;
import fs.FileSystem;
import java.io.Serializable;
import util.IO;

import java.util.Set;

/**
 * The class implement the function of command Find.
 *
 * @author Sihao Chen sihao.chen@mail.utoronto.ca
 */
public class Tree implements Command, Serializable {

  private FileSystem fs;

  private String doc
      = "NAME\n"
      + "    tree - tree display of file hierarchy\n\n"
      + "SYNOPSIS\n"
      + "    tree\n\n"
      + "DESCRIPTION\n"
      + "    Display the entire file system as a tree.\n";

  private String erroMsg = "cannot show tree";

  /**
   * Init Tree class with FileSystem.
   *
   * @param fs JShell's fileSystem
   */
  public Tree(FileSystem fs) {
    this.fs = fs;
  }

  /**
   * Get documentation of tree command.
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

  private static String shownName(String originalName, int layer) {
    // layer start from 0
    String result = originalName;
    for (int i = 0; i < layer; i++) {
      result = "\t" + result;
    }
    return result;
  }

  private void showRecursive(String fullPath, int layer, IO io)
      throws Exception {
    if (fullPath.equals("/")) {
      io.printLine("/");
    } else {
      io.printLine(shownName(getName(fullPath), layer));
    }
    Set<String> files = fs.getDirectoryContentFullPath(fullPath);
    for (String file : files) {
      if (fs.isRegularFile(file)) {
        io.printLine(shownName(getName(file), layer + 1));
      } else {
        showRecursive(file, layer + 1, io);
      }
    }
  }

  /**
   * Execute tree command.
   *
   * @param args Arguments that passed to the command
   * @param io JShell's io
   * @throws Exception if has more arguments
   */
  @Override
  public void exec(String[] args, IO io) throws Exception {
    if (args.length != 1) {
      // Exception of extra useless arguments
      throw new ArityMismatchException(erroMsg);
    }

    showRecursive("/", 0, io);
  }
}
