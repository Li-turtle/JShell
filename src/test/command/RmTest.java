package test.command;

import command.Rm;
import exception.*;
import fs.Directory;
import fs.FileSystem;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import util.IO;

public class RmTest {

  private Rm rm;
  private MockFileSystem fs;
  private IO io;

  /**
   * setUp method of this JUnitTest.
   * 
   * @throws Exception various Exception
   */
  @Before
  public void setUp() throws Exception {
    fs = new MockFileSystem();
    rm = new Rm(fs);
    io = new IO();
  }

  @Test
  public void testGetDoc() {
    // Test whether getDoc() returns correctly the documentation
    String rst = rm.getDoc();
    String expected
        = "NAME\n"
        + "    rm -  removes the DIR from the file system\n\n"
        + "SYNOPSIS\n"
        + "    rm DIR \n\n"
        + "DESCRIPTION\n"
        + "    removes the DIR from the file system\n"
        + "    throws an error if there is no such DIR.\n";
    assertEquals(expected, rst);
  }

  @Test
  public void testExec() throws Exception {
    // Test whether Rm runs successfully
    fs.createDirectory("file1");
    fs.createDirectory("file2");
    rm.exec(new String[]{"rm", "file1"}, io);
    rm.exec(new String[]{"rm", "file2"}, io);
    Set<String> test = new HashSet<>();
    assertEquals(test, fs.getDirectoryContentFullPath());
  }

  @Test(expected = ArityMismatchException.class)
  public void testExecException1() throws Exception {
    // Exception of invalid number of arguments
    rm.exec(new String[]{"rm", "file1", "file2"}, io);
  }

  @Test(expected = InvalidFileException.class)
  public void testExecException2() throws Exception {
    // Exception of removing File that is not a directory
    fs.writeToFile("regularFile1", io.flush());
    rm.exec(new String[]{"rm", "regularFile1"}, io);
  }

  @Test(expected = FileNotExistException.class)
  public void testExecException3() throws Exception {
    // Exception of removing non-exist directory
    rm.exec(new String[]{"rm", "file1"}, io);
  }

  public class MockFileSystem implements FileSystem {
    
    public Set<String> regularFileList;
    public Set<String> dirList;
    
    MockFileSystem(){
      this.regularFileList = new HashSet<>();
      this.dirList = new HashSet<>();
      
    }

    @Override
    public void writeToFile(String path, String content) throws Exception {
      regularFileList.add(path);
    }

    @Override
    public void setWorkingDirectory(String path) throws Exception {

    }

    @Override
    public void removeFile(String path) throws Exception {
      if (regularFileList.contains(path)) {
        throw new InvalidFileException("Not a directory");
      }
      if (! dirList.contains(path)) {
        throw new FileNotExistException("File not exists");
      }
      dirList.remove(path);
    }

    @Override
    public void duplicateRegularFile(String oldPath, String newPath) throws Exception {

    }

    @Override
    public void duplicateDirectory(String oldPath, String newPath) throws Exception {

    }

    @Override
    public void createDirectory(String path) throws Exception {
      dirList.add(path);
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
      return dirList;
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
      return regularFileList.contains(path);
    }

    @Override
    public boolean isDirectory(String path) {
      return dirList.contains(path);
    }
  }

}
