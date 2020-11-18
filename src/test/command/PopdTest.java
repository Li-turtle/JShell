package test.command;

import static org.junit.Assert.*;
import org.junit.Test;
import command.Popd;
import command.Pushd;
import exception.ArityMismatchException;
import exception.FileNotExistException;
import fs.Directory;
import fs.FileSystem;
import org.junit.Before;
import util.DirectoryStack;
import util.IO;
import java.util.Set;
import static org.junit.Assert.assertEquals;


public class PopdTest {
  private FileSystem fs;
  private Popd popd;
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
    ds.push("root");
    ds.push("dir1");
    popd = new Popd(ds, fs);
  }

  @Test
  public void testGetDoc() {
    // Test whether getDoc() returns correctly the documentation
    String rst = popd.getDoc();
    String expected
        = "NAME\n"
            + "    popd - Remove the top entry from the directory stack, "
            + "and cd into it.\n\n"
            + "SYNOPSIS\n"
            + "    popd \n\n"
            + "DESCRIPTION\n"
            + "    The popd command removes the top most directory from the\n"
            + "    directory stack and makes it the current working directory.\n"
            + "    If there is no directory onto the stack, then give\n"
            + "    appropriate error message."
            + "\n";
    assertEquals(expected, rst);
  }

  @Test
  public void testExec() throws Exception {
    // Test when popd runs in normal cases
    popd.exec(new String[]{"popd"}, io);
    DirectoryStack tmpStack = new DirectoryStack();
    tmpStack.push("root");
    assertEquals(tmpStack.size(), ds.size());
    assertEquals(tmpStack.pop(), ds.pop());
    assertEquals("root", fs.getWorkingDirectory());
  }

  @Test(expected = ArityMismatchException.class)
  public void testExecException1() throws Exception {
    // Exception of invalid number of arguments
    popd.exec(new String[]{"popd", "path"}, io);
  }

  private class MockFileSystem implements FileSystem {

    private String workingDirectory = "dir1";

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
      workingDirectory = "root";
    }

    @Override
    public void writeToFile(String path, String content) throws Exception {

    }
  }
}

