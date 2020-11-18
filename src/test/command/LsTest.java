package test.command;

import static org.junit.Assert.*;

import exception.FileExistException;
import exception.FileNotExistException;
import exception.InvalidFileException;
import fs.Directory;
import fs.FileSystem;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import command.Ls;
import util.IO;


public class LsTest {
  private Ls ls;
  private IO io;

  /**
   * setUp method of this JUnitTest.
   *
   * @throws Exception various Exception
   */
  @Before
  public void setUp() throws Exception {
    FileSystem fs = new MockFileSystem();
    ls = new Ls(fs);
    io = new IO();
  }

  @Test
  public void testGetDoc() {
    // Test whether getDoc() returns correctly the documentation
    String rst = ls.getDoc();
    String expected
        = "NAME\n"
        + "    ls - list the content of the directory or name of file\n"
        + "\n"
        + "SYNOPSIS\n"
        + "    ls [-R][PATH ...]\n"
        + "\n"
        + "DESCRIPTION\n"
        + "    List information about the FILEs "
        + "(the current directory by default)\n";
    assertEquals(expected, rst);
  }

  @Test
  public void testExecNoArgs() throws Exception {
    // Ls with no argument
    ls.exec(new String[]{"ls"}, io);
    assertEquals("a\nb\nc\n", io.flush());
  }

  @Test
  public void testExecMoreThanOneArgs() throws Exception {
    ls.exec(new String[]{"ls", "a", "b"}, io);
    Set<String> output = new HashSet<>(Arrays. asList(io.flush().split("\n")));
    Set<String> expected = new 
        HashSet<>(Arrays. asList("a:", "b:", "a1", "a2", "a3", "b1", "b2", ""));
    assertEquals(expected, output);
  }

  @Test
  public void testExecOneArgsRecursive() throws Exception {
    ls.exec(new String[]{"ls", "-R"}, io);
    Set<String> output = new HashSet<>(Arrays. asList(io.flush().split("\n")));
    Set<String> expected = new HashSet<>(
        Arrays. asList("\ta", "\tb", "\tc", "\t\ta1", "\t\ta2", "\t\ta3", "\t\tb1", "\t\tb2"));
    assertEquals(expected, output);
  }

  @Test
  public void testExecMoreThanOneArgsRecursive() throws Exception {
    ls.exec(new String[]{"ls", "-R", "a"}, io);
    Set<String> output = new HashSet<>(Arrays. asList(io.flush().split("\n")));
    Set<String> expected = new HashSet<>(
        Arrays. asList("\t\ta1", "\t\ta2", "\t\ta3"));
    assertEquals(expected, output);
  }

  @Test(expected = FileNotExistException.class)
  public void testExecException() throws Exception {
    ls.exec(new String[]{"ls", "d"}, io);
  }

  private class MockFileSystem implements FileSystem {

    private Directory root;

    public MockFileSystem() {
      root = new Directory("/");
      root.setParent(root);
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
      return root.toString();
    }

    @Override
    public String getFileContent(String path) throws Exception {
      return null;
    }

    @Override
    public Set<String> getDirectoryContentFullPath(String path) throws Exception {
      Set<String> content;
      if (path.equals("/")) {
        content = new HashSet<>(Arrays. asList("/a", "/b", "/c"));
      } else if (path.equals("a") || path.equals("/a")) {
        content = new HashSet<>(Arrays. asList("/a/a1", "/a/a2", "/a/a3"));
      } else if (path.equals("b") || path.equals("/b")) {
        content = new HashSet<>(Arrays. asList("/b/b1", "/b/b2"));
      } else {
        content = new HashSet<>(Arrays. asList());
      }
      return content;
    }

    @Override
    public Set<String> getDirectoryContentFullPath() {
      return null;
    }

    @Override
    public Set<String> getDirectoryContent(String path) throws Exception {
      if (path.equals("/")) {
        Set<String> content = new HashSet<>(Arrays. asList("a", "b", "c"));
        return content;
      } else if (path.equals("a")) {
        Set<String> content = new HashSet<>(Arrays. asList("a1", "a2", "a3"));
        return content;
      } else if (path.equals("b")) {
        Set<String> content = new HashSet<>(Arrays. asList("b1", "b2"));
        return content;
      } else {
        throw new InvalidFileException("Not a directory");
      }
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
    public boolean isRegularFile(String path) {
      return false;
    }

    @Override
    public boolean isDirectory(String path) {
      if (path.equals("/") || path.equals("a") 
          || path.equals("b") || path.equals("c") 
          || path.equals("/a") || path.equals("/b") 
          || path.equals("/c") || path.equals("/a/a1") 
          || path.equals("/a/a2") || path.equals("/a/a3") 
          || path.equals("/b/b1") || path.equals("/b/b2")) {
        return true;
      }
      return false;
    }
  }
}
