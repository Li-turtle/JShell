package test.command;

import static org.junit.Assert.*;

import command.Curl;
import exception.ArityMismatchException;
import exception.InvalidFileException;
import exception.InvalidFormatException;
import fs.Directory;
import fs.FileSystem;
import fs.RegularFile;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import util.IO;


public class CurlTest {
  private Curl curl;
  private FileSystem fs;
  private IO io;
  private String result = "";

  /**
   * setUp method of this JUnitTest.
   *
   * @throws Exception various Exception
   */
  @Before
  public void setUp() throws Exception {
    fs = new MockFileSystem();
    curl = new Curl(fs);
    io = new IO();
  }

  @Test
  public void testGetDoc() {
    // Test whether getDoc() returns correctly the documentation
    String rst = curl.getDoc();
    String expected
        = "NAME\n"
        + "    curl - transfer a URL\n\n"
        + "SYNOPSIS\n"
        + "    curl URL\n\n"
        + "DESCRIPTION\n"
        + "    Retrieve the file at that URL and add it to the current\n"
        + "     working directory.\n";
    assertEquals(expected, rst);
  }

  @Test
  public void testExecValidURL() throws Exception {
    URL website = new URL("https://www.utsc.utoronto.ca/~nick/b36/lecture.html");
    BufferedReader input = new BufferedReader(
        new InputStreamReader(website.openStream()));
    String inputLine;
    while ((inputLine = input.readLine()) != null) {
      result += inputLine + "\n";
    }

    curl.exec(new String[]{"curl",
        "https://www.utsc.utoronto.ca/~nick/b36/lecture.html"}, io);
    assertEquals(result, fs.getFileContent("lecture"));
  }


  @Test(expected = InvalidFormatException.class)
  public void testExecInvalidURL() throws Exception {
    curl.exec(new String[]{"curl", "InvalidURL"}, io);
  }

  @Test(expected = ArityMismatchException.class)
  public void testExecFileArityMismatchException() throws Exception {
    curl.exec(new String[]{"curl"}, io);
  }


  private class MockFileSystem implements FileSystem {
    RegularFile regularFile;

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
      if (path.equals("lecture")) {
        if (regularFile == null) {
          regularFile = new RegularFile("lecture");
        }
        regularFile.append(content);
      }
    }

    @Override
    public String getWorkingDirectory() {
      return null;
    }

    @Override
    public String getFileContent(String path) throws Exception {
      if (path.equals("lecture") && path != null) {
        return regularFile.read();
      } else {
        throw new InvalidFileException("Not a regular file");
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
