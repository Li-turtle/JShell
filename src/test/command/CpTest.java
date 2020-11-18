package test.command;

import command.Cp;
import exception.FileNotExistException;
import exception.InvalidFormatException;
import fs.Directory;
import fs.FileSystem;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import util.IO;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CpTest {

  private Cp cp;
  private FileSystem fs;
  private IO io;

  /**
   * setUp method of this JUnitTest.
   *
   * 
   */
  @Before
  public void setUp() {
    fs = new MockFS();
    cp = new Cp(fs);
    io = new IO();
  }

  @Test
  public void testGetDoc() {
    // Test whether getDoc() returns correctly the documentation
    String rst = cp.getDoc();
    String expected
        = "NAME\n"
        + "    cp - Copy content at old path to new path\n\n"
        + "SYNOPSIS\n"
        + "    cp OLDPATH NEWPATH\n\n"
        + "DESCRIPTION\n"
        + "    Copy the content at old path to new path.\n"
        + "    If path represents file, copy the content.\n"
        + "    If path represents directory, recursively copy\n"
        + "    content inside directory.\n";
    assertEquals(expected, rst);
  }

  @Test(expected = InvalidFormatException.class)
  public void testInvalidPath() throws Exception {
    String[] args = {
        "cp", "//", "/a"
    };
    cp.exec(args, io);
  }

  @Test(expected = FileNotExistException.class)
  public void testFileNotExist() throws Exception {
    String[] args = {
        "cp", "/mit", "/a"
    };
    cp.exec(args, io);
  }

  @Test
  public void testRegCopySuccess() throws Exception {
    String[] args = {
        "cp", "/c", "/b"
    };
    cp.exec(args, io);
    assertEquals("File Content", fs.getFileContent("/b/c"));
  }

  @Test
  public void testDirCopySuccess() throws Exception {
    String[] args = {
        "cp", "/path", "/b"
    };
    cp.exec(args, io);
    assertTrue(fs.isDirectory("/b/path"));
  }

  private class MockFS implements FileSystem {

    private boolean regCopied = false;
    private boolean dirCopied = false;

    @Override
    public String getFileContent(String path) throws Exception {
      if (path == "/b/c" && regCopied) {
        return "File Content";
      }

      throw new FileNotExistException(path);
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
    public Set<String> getDirectoryContentFullPath(String path) throws Exception {
      return null;
    }

    @Override
    public boolean isRegularFile(String path) {
      if (path == "/a") {
        return false;
      }
      if (regCopied && path == "/b/a") {
        return true;
      }
      if (path == "/c") {
        return true;
      }
      if (path == "/path") {
        return false;
      }
      if (path == "/b") {
        return false;
      }
      return false;
    }

    @Override
    public boolean isDirectory(String path) {
      if (path == "/a") {
        return true;
      }
      if (path == "/b") {
        return true;
      }
      if (path == "/path") {
        return true;
      }
      if (dirCopied && path == "/b/path") {
        return true;
      }
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
      if (oldPath == "/c" && newPath == "/b") {
        regCopied = true;
      }
    }

    @Override
    public void duplicateDirectory(String oldPath, String newPath)
        throws Exception {
      if (oldPath == "/path" && newPath == "/b") {
        dirCopied = true;
      }
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
      if (path == "//") {
        throw new InvalidFormatException("Not a valid path");
      }

      if (path == "/mit") {
        throw new FileNotExistException("/mit");
      }
    }
  }

}