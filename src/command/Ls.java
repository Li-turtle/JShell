package command;

import exception.FileNotExistException;
import fs.FileSystem;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;
import util.IO;

/**
 * The class that prints contents of the directory.
 *
 * @author Ziheng Zhuang ziheng.zhuang@mail.utoronto.ca Yining Wang wyn.wang@mail.utoronto.ca
 * @version 1.2
 * @since 1.2
 */
public class Ls implements Command, Serializable {

  private FileSystem fs;

  /**
   * The documentation of Ls.
   */
  protected String doc
      = "NAME\n"
      + "    ls - list the content of the directory or name of file\n"
      + "\n"
      + "SYNOPSIS\n"
      + "    ls [-R][PATH ...]\n"
      + "\n"
      + "DESCRIPTION\n"
      + "    List information about the FILEs "
      + "(the current directory by default)\n";

  /**
   * Return the documentation of command ls.
   *
   * @return the string of ls documentation
   */
  @Override
  public String getDoc() {
    return doc;
  }

  /**
   * Init Command ls with FileSystem.
   *
   * @param fs The file system
   */
  public Ls(FileSystem fs) {
    this.fs = fs;
  }

  /**
   * Print the path names of the files in current working directory.
   * @param path The target path name
   * @param io IO object
   * @throws Exception When path is invalid, path does not exist, or path does not represent
   *                   directory
   */
  private void printDirectoryContent(String path, IO io) throws Exception {
    for (String item : fs.getDirectoryContent(path)) {
      io.printLine(item);
    }
  }

  /**
   * Return the relative path of path.
   * @param path The target path name
   * @return String og relative path
   */
  private String getRelative(String path) {
    String prefix = "";
    for (int i = 0; i < path.length(); i++) {
      if (path.charAt(i) == '/') {
        prefix += "\t";
      }
    }
    return prefix + path.substring(path.lastIndexOf("/") + 1);
  }

  /**
   * Print all files and subfiles in q recursively.
   * @param q Queue with path names inside
   * @param io IO object
   * @throws Exception When path is invalid, path does not exist, or path does not represent
   *                   directory
   */
  private void printDirectoryContentRecursively(Queue<String> q, IO io)
      throws Exception {
    while (!q.isEmpty()) {
      String path = q.poll();
      if (fs.isDirectory(path)) {
        for (String item : fs.getDirectoryContentFullPath(path)) {
          io.printLine(getRelative(item));
          if (fs.isDirectory(item)) {
            q.add(item);
          }
        }
      } else if (fs.isRegularFile(path)) {
        io.printLine(getRelative(path));
      } else {
        throw new FileNotExistException(path);
      }
    }
  }

  /**
   * Execute the command.
   *
   * @param args Arguments that passed to the command ls
   * @param io   IO object
   * @throws Exception When given path is invalid, path does not exist, or path does not represent
   *                   directory
   */
  @Override
  public void exec(String[] args, IO io) throws Exception {
    Queue<String> q = new LinkedList<>();
    if (args.length == 1) {
      // Print every sub file except for current directory & parent directory
      printDirectoryContent(fs.getWorkingDirectory(), io);
    } else if (args.length == 2 && args[1].equals("-R")) {
      // print files in working directory recursively
      q.add(fs.getWorkingDirectory());
      printDirectoryContentRecursively(q, io);
    } else if (!args[1].equals("-R")) {
      // For every argument, print it by rules
      for (int i = 1; i < args.length; i++) {
        String path = args[i];
        if (fs.isRegularFile(path)) {
          io.printLine(path);
        } else if (fs.isDirectory(path)) {
          io.printLine(args[i] + ":");
          printDirectoryContent(path, io);
          io.printLine();
        } else {
          throw new FileNotExistException(path);
        }
      }
    } else {
      // print sub files after -R recursively
      for (int i = 2; i < args.length; i++) {
        q.add(args[i]);
      }
      printDirectoryContentRecursively(q, io);
    }
  }
}