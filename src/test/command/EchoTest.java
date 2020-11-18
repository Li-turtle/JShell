package test.command;

import static org.junit.Assert.*;
import command.Echo;
import exception.ArityMismatchException;
import exception.InvalidFormatException;
import fs.Directory;
import fs.FileSystem;
import org.junit.Before;
import org.junit.Test;
import util.IO;
import java.util.Set;

public class EchoTest {
  private Echo echo;
  private IO io;
  private FileSystem fs;

  /**
   * setUp method of this JUnitTest.
   *
   * @throws Exception various Exception
   */
  @Before
  public void setUp() throws Exception {
    fs = new MockFileSystem();
    echo = new Echo(fs);
    io = new IO();
  }

  @Test
  public void testGetDoc() {
    // Test whether getDoc() returns correctly the documentation
    String rst = echo.getDoc();
    String expected
        = "NAME\n"
        + "    echo - display a line of text\n\n"
        + "SYNOPSIS\n"
        + "    echo STRING [> OUTFILE]\n"
        + "    echo STRING >> OUTFILE\n\n"
        + "DESCRIPTION\n"
        + "    Echo the STRING to standard output if no more arguments\n"
        + "    given. If '>' is given, then write STRING to OUTFILE. IF '>>'\n"
        + "    is given, then append STRING to OUTFILE. If OUTFILE does not\n"
        + "    exist, create a new file named OUTFILE.\n";
    assertEquals(expected, rst);
  }

  @Test
  public void testExec1() throws Exception {
    // Test in normal case
    echo.exec(new String[]{"echo", "\"hello\""}, io);
    assertEquals("hello\n", io.flush());
  }

  @Test(expected = ArityMismatchException.class)
  public void testExecException1() throws Exception {
    // Test the case for invalid number of arguments
    echo.exec(new String[]{"echo"}, io);
  }

  @Test(expected = InvalidFormatException.class)
  public void testExecException2() throws Exception {
    // Test the case for input is not a string
    echo.exec(new String[]{"echo", "hello\""}, io);
  }

  private class MockFileSystem implements FileSystem {

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