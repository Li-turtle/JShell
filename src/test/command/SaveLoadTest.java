package test.command;

import java.io.Serializable;
import org.junit.Test;
import command.Cat;
import command.Save;
import command.Load;
import driver.JShell;
import exception.*;
import fs.Directory;
import fs.FileSystem;
import static org.junit.Assert.assertEquals;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import org.junit.After;
import org.junit.Before;
import util.CommandHistory;
import util.DirectoryStack;
import util.IO;

public class SaveLoadTest implements Serializable {

  private Save save;
  private Load load;
  private JShell newJShell;
  private JShell savedJShell;
  private MockFileSystem fs;
  private CommandHistory commandHistory;
  private DirectoryStack directoryStack;
  private IO io;
  private String path;

  /**
   * setUp method of this JUnitTest.
   * 
   * @throws Exception various Exception
   */
  @Before
  public void setUp() throws Exception {
    newJShell = new JShell();
    savedJShell = new JShell();
    load = new Load(newJShell);
    io = new IO();
    fs = new MockFileSystem();
    commandHistory = new CommandHistory();
    directoryStack = new DirectoryStack();
  }

  @Test
  public void testGetDoc() {
    // Test whether getDoc() returns correctly the documentation
    String rst = load.getDoc();
    String expected
        = "NAME\n"
        + "    load - load the session of the JShell\n\n"
        + "SYNOPSIS\n"
        + "    load FileName\n\n"
        + "DESCRIPTION\n"
        + "    load the contents of the FileName and reinitialize\n"
        + "    everything of JShell that was saved previously into\n"
        + "    the FileName.\n";
    assertEquals(expected, rst);
  }
  
  
  /**
   * setup a JShell in which several commands are executed,
   * start a new JShell with no command is executed.
   * @throws Exception various exception
   */
  public void setTestExecute() throws Exception {
    commandHistory.add("mkdir dir1");
    commandHistory.add("echo \"hello\" > file1");
    directoryStack.push("dir1");
    savedJShell.setJShell(fs, commandHistory, directoryStack);
    save = new Save(savedJShell);
    newJShell.commandHistory.add("load");
  }

  @Test
  public void testExecute() throws Exception {
    // Test whether load runs successfully
//    System.out.println("enter a path to test:");
//    path = IO.readLine();
    path = "testloadsave";
    setTestExecute();
    save.exec(new String[]{"save", path}, io);
    load.exec(new String[]{"load", path}, io);
    assertEquals(savedJShell.commandHistory.getAllHistory(),
        newJShell.commandHistory.getAllHistory());
    assertEquals(savedJShell.directoryStack.getDirectoryStack(),
        newJShell.directoryStack.getDirectoryStack());
    assertEquals(savedJShell.fs.getWorkingDirectory(),
        newJShell.fs.getWorkingDirectory());
  }
  
  /**
   * setup a JShell in which several commands are executed,
   * start a new JShell with some commands are executed.
   * @throws Exception Exception various exception
   */
  public void setTestDisabled() throws Exception {
    commandHistory.add("mkdir dir1");
    commandHistory.add("echo \"hello\" > file1");
    directoryStack.push("dir1");
    savedJShell.setJShell(fs, commandHistory, directoryStack);
    save = new Save(savedJShell);
    newJShell.commandHistory.add("load");
    newJShell.commandHistory.add("mkdir dir1");
  }
  
  @Test (expected = Exception.class)
  public void testDisabled() throws Exception {
    // Test when load runs within a already started JShell
    setTestExecute();
    save.exec(new String[]{"save", path}, io);
    load.exec(new String[]{"load", path}, io);
  }
  
  @Test (expected = ArityMismatchException.class)
  public void testArguments() throws Exception {
    // Test pass the incorrect number of argumnents
    setTestExecute();
    save.exec(new String[]{"save", path, path}, io);
    load.exec(new String[]{"load", path, path}, io);
  }
  
  
  public class MockFileSystem implements FileSystem, Serializable {


    MockFileSystem(){

    }

    @Override
    public void writeToFile(String path, String content) throws Exception {

    }

    @Override
    public void setWorkingDirectory(String path) throws Exception {

    }

    @Override
    public void removeFile(String path) throws Exception {

    }

    @Override
    public void duplicateRegularFile(String oldPath, String newPath) throws Exception {

    }

    @Override
    public void duplicateDirectory(String oldPath, String newPath) throws Exception {

    }

    @Override
    public void createDirectory(String path) throws Exception {

    }

    @Override
    public void appendToFile(String path, String content) throws Exception {

    }

    @Override
    public String getWorkingDirectory() {
      return "curr";
    }

    @Override
    public String getFileContent(String path) throws Exception {
      return null;
    }

    @Override
    public Set<String> getDirectoryContentFullPath(String path) throws Exception {
      return null;
    }

    @Override
    public Set<String> getDirectoryContentFullPath() {
      return null;

    }


    public Set<String> getDirectoryContent(String path) throws Exception {
      return null;
    }

    @Override
    public Set<String> getDirectoryContent() {
      return null;
    }

    @Override
    public Directory getRoot() {
      return null;
    }

    @Override
    public boolean removable(String path) throws Exception {
      return false;
    }

    @Override
    public void verifyPath(String path) throws Exception {

    }

    @Override
    public boolean isRegularFile(String path) {
      return false;
    }

    @Override
    public boolean isDirectory(String path) {
      return false;
    }
  }
}
