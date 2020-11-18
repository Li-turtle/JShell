package test.command;

import command.Find;
import exception.ArityMismatchException;
import exception.InvalidFileException;
import exception.InvalidFormatException;
import fs.Directory;
import fs.FileSystem;
import org.junit.Before;
import org.junit.Test;
import util.IO;
import java.util.*;

import static org.junit.Assert.*;

public class FindTest {
  private Find find;
  private IO io;
  private FileSystem fs;

  /**
   * setUp method of this JUnitTest.
   *
   * @throws Exception various Exception
   */
  @Before
  public void setUp() {
    fs = new MockFileSystem();
    find = new Find(fs);
    io = new IO();
  }

  @Test
  public void testGetDoc() {
    // Test whether getDoc() returns correctly the documentation
    String rst = find.getDoc();
    String expected
        = "NAME\n"
        + "    find - search for files in a directory hierarchy\n\n"
        + "SYNOPSIS\n"
        + "    find PATH ... -type [f|d] -name STRING\n\n"
        + "DESCRIPTION\n"
        + "    Find files or directories under PATH(s) with the given name\n"
        + "    STRING with a given option type for choosing regular files\n"
        + "    or directories.\n";
    assertEquals(expected, rst);
  }

  @Test
  public void testExec1() throws Exception {
    // Test in normal cases for directory
    find.exec(new String[]{"find", "path1", "path2", 
        "-type", "d", "-name", "\"dir\""}, io);
    String expected = "path1/path3/dir\npath1/dir\npath2/dir\n";
    assertEquals(expected, io.flush());
  }

  @Test
  public void testExec2() throws Exception {
    // Test in normal cases for regular files
    find.exec(new String[]{"find", "path1", "path2", 
        "-type", "f", "-name", "\"reg\""}, io);
    String expected = "path1/path3/reg\npath1/reg\npath2/reg\n";
    assertEquals(expected, io.flush());
  }

  @Test
  public void testExec3() throws Exception {
    // Test in normal cases where the first path is not directory
    find.exec(new String[]{"find", "not-dir", "path2", 
        "-type", "d", "-name", "\"dir\""}, io);
    assertEquals("not-dir is not a valid directory\npath2/dir\n", 
        io.flush());
  }

  @Test(expected = ArityMismatchException.class)
  public void testException1() throws Exception {
    // Exception of invalid number of arguments
    find.exec(new String[]{"find", "-type", "d", "-name", "\"dir\""}, io);
  }

  @Test(expected = InvalidFormatException.class)
  public void testException2() throws Exception {
    // Exception of invalid format of type flag
    find.exec(new String[]{"find", "path1", 
        "-t", "d", "-name", "\"dir\""}, io);
  }

  @Test(expected = InvalidFormatException.class)
  public void testException3() throws Exception {
    // Exception of invalid format of name flag
    find.exec(new String[]{"find", "path1", 
        "-type", "d", "-n", "\"dir\""}, io);
  }

  @Test(expected = InvalidFormatException.class)
  public void testException4() throws Exception {
    // Exception of invalid format for type
    find.exec(new String[]{"find", "path1", "-type", "g", 
        "-name", "\"dir\""}, io);
  }

  @Test(expected = InvalidFormatException.class)
  public void testException5() throws Exception {
    // Exception of invalid format for name (not a string)
    find.exec(new String[]{"find", "path1", "-type", "d", 
        "-name", "dir\""}, io);
  }


  private class MockFileSystem implements FileSystem {
    @Override
    public boolean isDirectory(String path) {

      List<String> chooseList = Arrays
          .asList("dir", "path1/dir", "path2/dir", "path1/path3", 
              "path1/path3/dir");
      if (chooseList.contains(path)) {
        return true;
      }
      return false;
    }

    @Override
    public boolean isRegularFile(String path) {
      List<String> chooseList = Arrays
          .asList("reg", "path1/reg", "path2/reg", "path1/path3/reg");
      if (chooseList.contains(path)) {
        return true;
      }
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
      if (path.equals("not-dir")) {
        throw new InvalidFileException();
      }
      Set<String> result = new HashSet<>();
      if (path.equals("path1")) {
        result.add("path1/dir");
        result.add("path1/reg");
        result.add("path1/path3");
      } else if (path.equals("path2")) {
        result.add("path2/dir");
        result.add("path2/reg");
      } else if (path.equals("path1/path3")) {
        result.add("path1/path3/dir");
        result.add("path1/path3/reg");
      } else {
        return Collections.emptySet();
      }
      return result;
    }

    @Override
    public String getFileContent(String path) throws Exception {
      return null;
    }

    @Override
    public String getWorkingDirectory() {
      return null;
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

    }

    @Override
    public void writeToFile(String path, String content) throws Exception {

    }
  }
}