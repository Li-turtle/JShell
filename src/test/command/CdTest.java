package test.command;

import static org.junit.Assert.*;

import command.Cd;
import exception.ArityMismatchException;
import exception.InvalidFileException;
import fs.Directory;
import fs.FileSystem;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import util.IO;


public class CdTest {

  private Cd cd;
  private FileSystem fs;
  private IO io;

  /**
   * setUp method of this JUnitTest.
   *
   * @throws Exception various Exception
   */
  @Before
  public void setUp() throws Exception {
    fs = new MockFileSystem();
    cd = new Cd(fs);
    io = new IO();
  }

  @Test
  public void testGetDoc() {
    // Test whether getDoc() returns correctly the documentation
    String rst = cd.getDoc();
    String expected
        = "NAME\n"
        + "    cd - change directories\n\n"
        + "SYNOPSIS\n"
        + "    cd DIR\n\n"
        + "DESCRIPTION\n"
        + "    Change the DIRECTORY to target place,\n"
        + "    which may be relative to the current directory\n"
        + "    or may be a full path.\n";
    assertEquals(expected, rst);
  }

  @Test
  public void testExecValidRelativePath() throws Exception {
    // Test whether Cd runs successfully with a valid relative path
    cd.exec(new String[]{"cd", "a"}, io);
    assertEquals("/a", fs.getWorkingDirectory());
  }

  @Test
  public void testExecValidAbsolutePath() throws Exception {
    // Test whether Cd runs successfully with a valid absolute path
    cd.exec(new String[]{"cd", "/a"}, io);
    assertEquals("/a", fs.getWorkingDirectory());
  }

  @Test(expected = InvalidFileException.class)
  public void testExecInvalidFileException() throws Exception {
    // Exception of a invalid path
    cd.exec(new String[]{"cd", "b"}, io);
  }

  @Test(expected = ArityMismatchException.class)
  public void testExecArityMismatchException() throws Exception {
    // Exception of invalid number of argument
    cd.exec(new String[]{"cd"}, io);
  }


  private class MockFileSystem implements FileSystem {

    private Directory workingDirectory;

    public MockFileSystem() {
      workingDirectory = new Directory("/");
      workingDirectory.setParent(workingDirectory);
    }

    @Override
    public void writeToFile(String path, String content) throws Exception { }

    @Override
    public void setWorkingDirectory(String path) throws Exception {
      if (path.equals("/a") || path.equals("a")) {
        Directory a = new Directory("a");
        a.setParent(workingDirectory);
        workingDirectory = a;
      } else {
        throw new InvalidFileException("Not a directory");
      }
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
      return workingDirectory.toString();
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
