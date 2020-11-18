package command;

import driver.JShell;
import exception.ArityMismatchException;
import exception.FileNotExistException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import util.IO;

/**
 * The class to save the session of the JShell before the user closes it down.
 *
 * @author Yining Wang wyn.wang@mail.utoronto.ca
 */
public class Save implements Command, Serializable {

  private JShell jshell;

  /**
   * Documentation of Save.
   */
  private String doc
      = "NAME\n"
      + "    save - save the session of the JShell\n\n"
      + "SYNOPSIS\n"
      + "    save FileName\n\n"
      + "DESCRIPTION\n"
      + "    Save the session of the JShell before the user closes it down.\n";

  /**
   * Return the documentation of command save.
   *
   * @return the string of save documentation
   */
  @Override
  public String getDoc() {
    return doc;
  }

  /**
   * Init Command save with jshell.
   *
   * @param jshell The JShell
   */
  public Save(JShell jshell) {
    this.jshell = jshell;
  }

  /**
   * Execution of command save.
   *
   * @param args the argument passed to save
   * @throws Exception incorrect number of args or invalid path name
   */
  @Override
  public void exec(String[] args, IO io) throws Exception {
    if (args.length != 2) {
      throw new ArityMismatchException("save");
    }

    java.io.File path = new java.io.File(args[1]);
    try {
      if (!path.isFile()) {
        //if path not exist, create the file
        path.createNewFile();
      }
    } catch (Exception e) {
      //if path is invalid, throw exception
      throw new FileNotExistException(args[1]);
    }

    FileOutputStream file = new FileOutputStream(args[1], false);
    ObjectOutputStream out = new ObjectOutputStream(file);
    out.writeObject(jshell);
    out.flush();
    out.close();
    file.close();
  }
}
