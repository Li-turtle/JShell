package command;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import driver.JShell;
import exception.ArityMismatchException;
import exception.FileNotExistException;
import util.IO;
import java.io.Serializable;

/**
 * load the contents of the FileName and reinitialize 
 * everything of JShell that was saved previously into the FileName.
 *
 * @author Jiayu Lu jiay.lu@mail.utoronto.ca
 */

public class Load implements Command, Serializable {
  private JShell jshell;
    
  private String doc
      = "NAME\n"
      + "    load - load the session of the JShell\n\n"
      + "SYNOPSIS\n"
      + "    load FileName\n\n"
      + "DESCRIPTION\n"
      + "    load the contents of the FileName and reinitialize\n" 
      + "    everything of JShell that was saved previously into\n"
      + "    the FileName.\n";

    
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
   * The constructor for the Load command.
   * 
   * @param jshell JShell class
   *
   */
  public Load(JShell jshell) {
    this.jshell = jshell;
  }

  /**
   * Executes the load commands features.
   * 
   * @throws Exception various exceptions
   */
  public void exec(String[] args, IO io) throws Exception {
    //Throw exception if any command has been typed
    if (jshell.commandHistory.getAllHistory().size() == 1) {
      try {
        //Throw ArityMismatchException if the number of arguments is not two
        if (args.length != 2) {
          throw new ArityMismatchException("load");
        }
        //Read file FileName
        java.io.File path = new java.io.File(args[1]);
        FileInputStream fis = new FileInputStream(path);
        ObjectInputStream ois = new ObjectInputStream(fis);
        //Save the previously running JShell to savedJShell
        JShell savedJShell;
        savedJShell = (JShell) ois.readObject();
        //Reset current JShell
        jshell.setJShell(savedJShell.fs, savedJShell.commandHistory, 
            savedJShell.directoryStack);
        //Close file FileName
        fis.close();
      } catch (FileNotFoundException e) {
        throw new FileNotExistException(args[1]);
      } catch (IOException e) {
        IO.writeLine("load failed");
      }
    } else {
      throw
          new Exception("Load disabled: other commands have been executed");
    }
  }
}
