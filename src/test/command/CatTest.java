package test.command;

import org.junit.Test;
import command.Cat;
import exception.*;
import fs.Directory;
import fs.FileSystem;
import static org.junit.Assert.assertEquals;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.Before;
import util.IO;

public class CatTest {

  private Cat cat;
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
    cat = new Cat(fs);
    io = new IO();
  }

  @Test
  public void testGetDoc() {
    // Test whether getDoc() returns correctly the documentation
    String rst = cat.getDoc();
    String expected
        = "NAME\n"
        + "    cat - Display the contents of FILE or FILEs\n\n"
        + "SYNOPSIS\n"
        + "    cat FILE1 [FILE2 ...] \n\n"
        + "DESCRIPTION\n"
        + "    Display the contents of FILE or FILEs concatenated in the\n"
        + "    shell. There are three line breaks separating the contents of\n"
        + "    FILEs from the others. If FILE is invalid, display an error.\n";
    assertEquals(expected, rst);
  }

  @Test
  public void testOneArg() throws Exception {
    // Test whether Cat runs successfully with one regular file
    cat.exec(new String[]{"cat", "regularFile1"}, io);
    assertEquals("hello world", io.flush());
  }
  
  @Test
  public void testTwoArg() throws Exception {
    // Test whether Cat runs successfully with two regular files
    cat.exec(new String[]{"cat", "regularFile1", "regularFile2"}, io);
    Set<String> output = new HashSet<>(Arrays. asList(io.flush().split("\n\n\n")));
    Set<String> expected = new HashSet<>(
        Arrays. asList("hello world", "good luck"));
    assertEquals(expected, output);
  }
  
  @Test
  public void testThreeArg() throws Exception {
    // Test whether Cat runs successfully with three regular files
    cat.exec(new String[]{"cat", "regularFile1", "regularFile2", "regularFile3"}, io);
    Set<String> output = new HashSet<>(Arrays. asList(io.flush().split("\n\n\n")));
    Set<String> expected = new HashSet<>(
        Arrays. asList("hello world", "good luck", "have fun"));
    assertEquals(expected, output);
  }

  @Test(expected = ArityMismatchException.class)
  public void testExecException1() throws Exception {
    // Exception of invalid number of arguments 
    // When no argument is passed
    cat.exec(new String[]{"cat"}, io);
  }

  @Test
  public void testExecException2() throws Exception {
    // Exception of passing File that is a directory
    cat.exec(new String[]{"cat", "dir1"}, io);
    assertEquals("dir1: Not a regular file\n", io.flush());
  }

  @Test
  public void testExecException3() throws Exception {
    // Exception of passing a non-exist file
    cat.exec(new String[]{"cat", "notExistFile1"}, io);
    assertEquals("notExistFile1: File Not Exist\n", io.flush());
  }
  
  
  
  public class MockFileSystem implements FileSystem {
    @Override
    public void writeToFile(String path, String content) throws Exception {}

    @Override
    public void setWorkingDirectory(String path) throws Exception {}

    @Override
    public void removeFile(String path) throws Exception {}

    @Override
    public void duplicateRegularFile(String oldPath, String newPath) throws Exception {}

    @Override
    public void duplicateDirectory(String oldPath, String newPath) throws Exception {}

    @Override
    public void createDirectory(String path) throws Exception {}

    @Override
    public void appendToFile(String path, String content) throws Exception {}

    @Override
    public String getWorkingDirectory() {
      return null;
    }

    @Override
    public String getFileContent(String path) throws Exception {
      if (path == "regularFile1") {
        return "hello world";
      }
      if (path == "regularFile2") {
        return "good luck";
      }
      if (path == "regularFile3") {
        return "have fun";
      }
      if (path == "dir1") {
        throw new InvalidFileException("Not a regular file");
      } else {
        throw new FileNotExistException("File not exists");
      }
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
    public void verifyPath(String path) throws Exception { }

    @Override
    public boolean isRegularFile(String path) {
      return path == "regularFile1" || path == "regularFile1" || path == "regularFile1";
    }

    @Override
    public boolean isDirectory(String path) {
      return path == "dir1";
    }
  }
}
