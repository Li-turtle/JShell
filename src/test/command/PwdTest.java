package test.command;

import static org.junit.Assert.assertEquals;

import command.Pwd;
import exception.ArityMismatchException;
import fs.Directory;
import fs.FileSystem;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import util.IO;

public class PwdTest {

  private Pwd pwd;
  private IO io;

  @Before
  public void setUp() {
    pwd = new Pwd(new MockFSPwd());
    io = new IO();
  }

  @Test
  public void getPwdTest() throws Exception {
    String[] args = {"pwd"};
    pwd.exec(args, io);
    assertEquals("/path/file\n", io.flush());
  }

  @Test(expected = ArityMismatchException.class)
  public void incorrectArgTest() throws Exception {
    String[] args = {"pwd", "extra"};
    pwd.exec(args, io);
  }

  private class MockFSPwd implements FileSystem {

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
      return "/path/file";
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
}