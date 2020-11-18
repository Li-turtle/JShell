package test.command;

import static org.junit.Assert.*;

import command.Exit;
import exception.ArityMismatchException;
import fs.Directory;
import fs.FileSystem;
import java.util.Set;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import util.IO;


public class ExitTest {
  private Exit exit;
  private FileSystem fs;
  private IO io;
  
  @Test
  public void testGetDoc() {
    // Test whether getDoc() returns correctly the documentation
    String rst = exit.getDoc();
    String expected
        = "NAME\n"
            + "    exit - cause normal process termination\n\n"
            + "SYNOPSIS\n"
            + "    exit\n\n"
            + "DESCRIPTION\n"
            + "    terminate the program\n";

    assertEquals(expected, rst);
  }

  /**
   * setUp method of this JUnitTest.
   *
   * @throws Exception various Exception
   */
  @Before
  public void setUp() throws Exception {
    fs = new MockFileSystem();
    exit = new Exit(fs);
    io = new IO();
  }

  @Test(expected = ArityMismatchException.class)
  public void testExecFileArityMismatchException() throws Exception {
    // Exception of invalid number of argument
    exit.exec(new String[]{"exit", "0"}, io);
  }

  private class MockFileSystem implements FileSystem {

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
      return null;
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

    @Override
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
