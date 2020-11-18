package command;

import exception.ArityMismatchException;
import exception.InvalidFormatException;
import fs.FileSystem;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import util.IO;

/**
 * The class to retrieve file at URL and add to working directory.
 *
 * @author Yining Wang wyn.wang@mail.utoronto.ca
 */
public class Curl implements Command, Serializable {

  private FileSystem fs;

  /**
   * Documentation of Curl.
   */
  private String doc
      = "NAME\n"
      + "    curl - transfer a URL\n\n"
      + "SYNOPSIS\n"
      + "    curl URL\n\n"
      + "DESCRIPTION\n"
      + "    Retrieve the file at that URL and add it to the current\n"
      + "     working directory.\n";

  /**
   * Return the documentation of command curl.
   *
   * @return the string of curl documentation
   */
  @Override
  public String getDoc() {
    return doc;
  }

  /**
   * Init Command curl with FileSystem.
   *
   * @param fs The file system
   */
  public Curl(FileSystem fs) {
    this.fs = fs;
  }

  /**
   * Execution of command curl.
   *
   * @param args the argument passed to curl
   * @throws Exception incorrect number of args or invalid URL
   */
  @Override
  public void exec(String[] args, IO io) throws Exception {
    if (args.length != 2) {
      //incorrect number of args
      throw new ArityMismatchException("curl");
    }
    try {
      String path = args[1];
      String name = path.substring(
          path.lastIndexOf("/") + 1, path.lastIndexOf("."));
      fs.writeToFile(name, "");

      URL website = new URL(path);
      BufferedReader input = new BufferedReader(
          new InputStreamReader(website.openStream()));

      String inputLine;
      while ((inputLine = input.readLine()) != null) {
        fs.appendToFile(name, inputLine + "\n");
      }
    } catch (Exception e) {
      //invalid URL
      throw new InvalidFormatException("Invalid URL");
    }
  }
}
