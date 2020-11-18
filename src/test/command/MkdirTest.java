package test.command;

import static org.junit.Assert.*;

import exception.ArityMismatchException;
import exception.FileExistException;
import exception.FileNotExistException;
import exception.InvalidFormatException;
import fs.Directory;
import fs.FileSystem;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import command.Mkdir;
import util.IO;


public class MkdirTest {
  private Mkdir mkdir;
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
    mkdir = new Mkdir(fs);
    io = new IO();
  }

  @Test
  public void testGetDoc() {
    // Test whether getDoc() returns correctly the documentation
    String rst = mkdir.getDoc();
    String expected
        = "NAME\n"
        + "    mkdir - make directories\n\n"
        + "SYNOPSIS\n"
        + "    mkdir DIR ...\n\n"
        + "DESCRIPTION\n"
        + "    Create the DIRECTORY, if it does not already exist.\n"
        + "    The new directory may be relative to the current directory\n"
        + "    or may be a full path. mkdir can take in more than one DIR.\n";
    assertEquals(expected, rst);
  }

  @Test
  public void testExecOneArg() throws Exception {
    // mkdir with one directory created
    mkdir.exec(new String[]{"mkdir", "a"}, io);
    Set<String> target = new HashSet<>(Arrays. asList("a"));
    assertEquals(target, fs.getDirectoryContent());
  }

  @Test
  public void testExecThreeArgs() throws Exception {
    // mkdir with multiple directories created
    mkdir.exec(new String[]{"mkdir", "b", "c", "d"}, io);
    Set<String> target = new HashSet<>(Arrays. asList("b", "c", "d"));
    assertEquals(target, fs.getDirectoryContent());
  }

  @Test(expected = FileExistException.class)
  public void testExecFileExistException() throws Exception {
    // Exception of target path already exist
    mkdir.exec(new String[]{"mkdir", "exist"}, io);
  }

  @Test(expected = ArityMismatchException.class)
  public void testExecArityMismatchException() throws Exception {
    // Exception of invalid number of arguments
    mkdir.exec(new String[]{"mkdir"}, io);
  }

  @Test(expected = InvalidFormatException.class)
  public void testExecInvalidFormatException() throws Exception {
    // Exception of target path has invalid file name (contains invalid char)
    mkdir.exec(new String[]{"mkdir", "invalidName"}, io);
  }

  @Test(expected = FileNotExistException.class)
  public void testExecFileNotExistException() throws Exception {
    // Exception of target path is invalid (parent not exist)
    mkdir.exec(new String[]{"mkdir", "invalidPath"}, io);
  }

  private class MockFileSystem implements FileSystem {

    Set<String> dirContent = new HashSet<>();

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
      if (path.equals("exist")) {
        throw new FileExistException("");
      }
      if (path.equals("invalidName")) {
        throw new InvalidFormatException("");
      }
      if (path.equals("invalidPath")) {
        throw new FileNotExistException("");
      }
      dirContent.add(path);
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
      return dirContent;
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
