package test.command;

import command.Pushd;
import exception.ArityMismatchException;
import exception.FileNotExistException;
import fs.Directory;
import fs.FileSystem;
import org.junit.Before;
import org.junit.Test;
import util.DirectoryStack;
import util.IO;
import java.util.Set;
import static org.junit.Assert.assertEquals;


public class PushdTest {
  private FileSystem fs;
  private Pushd pushd;
  private IO io;
  private DirectoryStack ds;

  /**
   * setUp method of this JUnitTest.
   *
   * @throws Exception various Exception
   */
  @Before
  public void setUp() throws Exception {
    fs = new MockFileSystem();
    io = new IO();
    ds = new DirectoryStack();
    pushd = new Pushd(ds, fs);
  }

  @Test
  public void testGetDoc() {
    // Test whether getDoc() returns correctly the documentation
    String rst = pushd.getDoc();
    String expected
        = "NAME\n"
        + "    pushd - Saves the current working directory by pushing\n"
        + "    onto directory stack and then changes the new current\n"
        + "    working directory to DIR.\n\n "
        + "SYNOPSIS\n"
        + "    pushd DIR\n\n"
        + "DESCRIPTION\n"
        + "    Push the current directory into directoryStack before print\n"
        + "    a list of directories in the stack.\n";
    assertEquals(expected, rst);
  }

  @Test
  public void testExec() throws Exception {
    // Pushd with valid path
    pushd.exec(new String[]{"pushd", "path"}, io);
    DirectoryStack tmpStack = new DirectoryStack();
    tmpStack.push("root");
    assertEquals(tmpStack.size(), ds.size());
    assertEquals(tmpStack.pop(), ds.pop());
    assertEquals("path", fs.getWorkingDirectory());
  }

  @Test(expected = ArityMismatchException.class)
  public void testExecException1() throws Exception {
    // Exception of invalid number of arguments
    pushd.exec(new String[]{"pushd"}, io);
  }

  @Test(expected = FileNotExistException.class)
  public void testExecException2() throws Exception {
    // Exception of path does not exit
    pushd.exec(new String[]{"pushd", "notExistPath"}, io);
  }

  private class MockFileSystem implements FileSystem {

    private String workingDirectory = "root";

    @Override
    public boolean isDirectory(String path) {
      return false;
    }

    @Override
    public boolean isRegularFile(String path) {
      return false;
    }

    @Override
    public boolean removable(String path) throws Exception {
      return false;
    }

    /**
     * Check if path is valid.
     *
     * @param path The path to check
     * @throws Exception When path is invalid or path does not exist
     */
    @Override
    public void verifyPath(String path) throws Exception {

    }

    @Override
    public Directory getRoot() {
      return null;
    }

    @Override
    public Set<String> getDirectoryContent() {
      return null;
    }

    @Override
    public Set<String> getDirectoryContent(String path) throws Exception {
      return null;
    }

    @Override
    public Set<String> getDirectoryContentFullPath() {
      return null;
    }

    @Override
    public Set<String> getDirectoryContentFullPath(String path) throws Exception {
      return null;
    }

    @Override
    public String getFileContent(String path) throws Exception {
      return null;
    }

    @Override
    public String getWorkingDirectory() {
      return workingDirectory;
    }

    @Override
    public void appendToFile(String path, String content) throws Exception {

    }

    @Override
    public void createDirectory(String path) throws Exception {

    }

    @Override
    public void duplicateDirectory(String oldPath, String newPath) throws Exception {

    }

    @Override
    public void duplicateRegularFile(String oldPath, String newPath) throws Exception {

    }

    @Override
    public void removeFile(String path) throws Exception {

    }

    @Override
    public void setWorkingDirectory(String path) throws Exception {
      if (path.equals("notExistPath")) {
        throw new FileNotExistException("");
      }
      workingDirectory = path;
    }

    @Override
    public void writeToFile(String path, String content) throws Exception {

    }
  }

}