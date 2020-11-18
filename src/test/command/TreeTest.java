package test.command;

import command.Tree;
import exception.ArityMismatchException;
import fs.Directory;
import fs.FileSystem;
import org.junit.Before;
import org.junit.Test;
import util.IO;

import java.util.*;

import static org.junit.Assert.*;

public class TreeTest {
  private FileSystem fs;
  private IO io;
  private Tree tree;
  
  /**
   * setUp method of this JUnitTest.
   *
   * @throws Exception various Exception
   */
  @Before
  public void setUp() {
    fs = new MockFileSystem();
    io = new IO();
    tree = new Tree(fs);
  }

  @Test
  public void testGetDoc() {
    // Test whether getDoc() returns correctly the documentation
    String rst = tree.getDoc();
    String expected
        = "NAME\n"
        + "    tree - tree display of file hierarchy\n\n"
        + "SYNOPSIS\n"
        + "    tree\n\n"
        + "DESCRIPTION\n"
        + "    Display the entire file system as a tree.\n";
    assertEquals(expected, rst);
  }

  @Test
  public void testExec1() throws Exception {
    // tree at the beginning (with only root)
    tree.exec(new String[]{"tree"}, io);
    assertEquals("/\n", io.flush());
  }

  @Test
  public void testExec2() throws Exception {
    // tree while file1 and dir1 created
    ((MockFileSystem) fs).changeCondition(1);
    tree.exec(new String[]{"tree"}, io);
    String expected = "/\n\treg1\n\tdir1\n";
    assertEquals(expected, io.flush());
  }

  @Test
  public void testExec3() throws Exception {
    // tree while dir2 and reg2 created in dir1
    ((MockFileSystem) fs).changeCondition(2);
    tree.exec(new String[]{"tree"}, io);
    String expected = "/\n\treg1\n\tdir1\n\t\tdir2\n\t\treg2\n";
    assertEquals(expected, io.flush());
  }

  @Test(expected = ArityMismatchException.class)
  public void testException() throws Exception {
    // Exception of invalid number of arguments
    tree.exec(new String[]{"tree", "new"}, io);
  }

  private class MockFileSystem implements FileSystem {
    private int condition = 0;
    // condition 0 is at beginning (only root)
    // condition 1 is root has dir1 and reg1 in it
    // condition 2 is dir1 has dir2 and reg2 in it

    public void changeCondition(int condition) {
      this.condition = condition;
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
      return null;
    }

    @Override
    public String getFileContent(String path) throws Exception {
      return null;
    }

    @Override
    public Set<String> getDirectoryContentFullPath(String path) throws Exception {
      if (path.equals("/") && (condition == 1 || condition == 2)) {
        Set<String> rst = new HashSet<>();
        rst.add("reg1");
        rst.add("dir1");
        return rst;
      }
      if (path.equals("dir1") && condition == 2) {
        Set<String> rst = new HashSet<>();
        rst.add("reg2");
        rst.add("dir2");
        return rst;
      }
      return Collections.emptySet();
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
      List<String> regList = Arrays
          .asList("reg1", "reg2");
      if (regList.contains(path)) {
        return true;
      }
      return false;
    }

    @Override
    public boolean isDirectory(String path) {
      List<String> dirList = Arrays
          .asList("/", "dir1", "dir2");
      if (dirList.contains(path)) {
        return true;
      }
      return false;
    }
  }
}