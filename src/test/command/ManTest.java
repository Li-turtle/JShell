package test.command;

import static org.junit.Assert.assertEquals;

import command.Command;
import command.Man;
import exception.InvalidFormatException;
import fs.Directory;
import fs.FileSystem;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import util.IO;

public class ManTest {
  private Man man;
  private FileSystem fs;
  private IO io;

  /**
   * setUp method of this JUnitTest.
   *
   * @throws Exception various Exception
   */
  @Before
  public void setUp() {
    fs = new MockFS();
    io = new IO();
    Map<String, Command> dict = new HashMap<>();
    dict.put("mockcmd", new MockCmd());
    man = new Man(dict);
  }

  @Test
  public void testGetDoc() throws Exception {
    String[] args = {"man", "mockcmd"};
    man.exec(args, io);
    assertEquals("This is a doc\n", io.flush());
  }

  @Test(expected = InvalidFormatException.class)
  public void testCommandNotFound() throws Exception {
    String[] args = {"man", "stg"};
    man.exec(args, io);
  }

  private class MockFS implements FileSystem {

    @Override
    public String getFileContent(String path) throws Exception {
      return null;
    }

    @Override
    public Directory getRoot() {
      return null;
    }

    @Override
    public void setWorkingDirectory(String path) throws Exception {

    }

    @Override
    public String getWorkingDirectory() {
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
    public Set<String> getDirectoryContentFullPath(String path)
        throws Exception {
      return null;
    }

    @Override
    public boolean isRegularFile(String path) {
      return false;
    }

    @Override
    public boolean isDirectory(String path) {
      return false;
    }

    @Override
    public void createDirectory(String path) throws Exception {

    }

    @Override
    public void writeToFile(String path, String content) throws Exception {

    }

    @Override
    public void appendToFile(String path, String content) throws Exception {

    }

    @Override
    public void duplicateRegularFile(String oldPath, String newPath)
        throws Exception {

    }

    @Override
    public void duplicateDirectory(String oldPath, String newPath)
        throws Exception {

    }

    @Override
    public void removeFile(String path) throws Exception {

    }

    @Override
    public boolean removable(String path) throws Exception {
      return false;
    }

    @Override
    public void verifyPath(String path) throws Exception {

    }
  }
  
  private class MockCmd implements Command {

    @Override
    public String getDoc() {
      return "This is a doc";
    }

    @Override
    public void exec(String[] args, IO io) throws Exception {

    }
  }
}