package test.fs;

import exception.InvalidFileException;
import exception.InvalidFormatException;
import fs.Directory;
import fs.File;
import fs.JFS;
import fs.RegularFile;
import java.util.HashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.omg.CORBA.DynAnyPackage.Invalid;
import util.FileNavigator;

public class JFSTest {
  private JFS jfs;

  /**
   * setUp method of this JUnitTest.
   * 
   * @throws Exception various Exception
   */
  @Before
  public void setUp() {
    FileNavigator fn = new MockFN();
    jfs = new JFS(fn);
    fn.setRoot(jfs.getRoot());
  }

  @Test(expected = InvalidFileException.class)
  public void getFileContentTest() throws Exception {
    jfs.getFileContent("/");
  }

  @Test
  public void getFileContentSucTest() throws Exception {
    assertEquals("content", jfs.getFileContent("/file"));
  }

  @Test
  public void getRootTest() {
    assertEquals("/", jfs.getRoot().toString());
  }

  @Test
  public void getSetWorkingDirectoryTest() throws Exception {
    jfs.setWorkingDirectory("/");
    assertEquals("/", jfs.getWorkingDirectory());
  }

  @Test
  public void getDirectoryContentTest() throws Exception {
    Set<String> set = new HashSet<>();
    set.add("path");
    set.add("file");
    assertEquals(set, jfs.getDirectoryContent("/"));
  }

  @Test
  public void getDirectoryFullPathTest() throws Exception {
    Set<String> set = new HashSet<>();
    set.add("path");
    set.add("file");
    assertEquals(set, jfs.getDirectoryContent("/"));
  }

  @Test
  public void isRegularFileTest() {
    assertTrue(jfs.isRegularFile("/file"));
    assertFalse(jfs.isRegularFile("/"));
  }

  @Test
  public void isDirectoryTest() {
    assertTrue(jfs.isDirectory("/"));
    assertFalse(jfs.isDirectory("/file"));
  }

  @Test
  public void createDirectoryTest() throws Exception {
    jfs.createDirectory("/newpath");
    assertTrue(jfs.isDirectory("/newpath"));
  }

  @Test
  public void writeToFileTest() throws Exception {
    jfs.writeToFile("/file", "content");
    assertTrue(jfs.isRegularFile("/file"));
  }

  @Test
  public void appendToFileTest() throws Exception {
    jfs.appendToFile("/file", "content");
    assertTrue(jfs.isRegularFile("/file"));
  }

  @Test
  public void removableTest() throws Exception {
    assertTrue(jfs.removable("/file"));
  }

  @Test(expected = InvalidFormatException.class)
  public void verifyPathTest() throws Exception {
    jfs.verifyPath("//");
  }

  @Test
  public void removeFileTest() {
    try {
      jfs.removeFile("/file");
    } catch (Exception e) {
      e.getMessage();

    }

    assertFalse(jfs.isDirectory("/file"));
  }

  @Test
  public void duplicateDirTest() throws Exception {
    jfs.duplicateDirectory("/", "/");
    assertTrue(jfs.isDirectory("/"));
  }

  @Test
  public void duplicateRegTest() throws Exception {
    jfs.duplicateRegularFile("/file", "/");
    assertTrue(jfs.isRegularFile("/file"));
  }

  private class MockFN implements FileNavigator {
    private String path;
    private Directory directory;
    private Directory root;

    /**
     * Set the path and working directory for file navigation.
     *
     * @param path             The path to search
     * @param workingDirectory The current working directory
     */
    @Override
    public void setPath(String path, Directory workingDirectory) {
      this.path = path;
      directory = workingDirectory;
    }

    /**
     * Return true if the target of the path is a file.
     *
     * @return True if there is a file corresponding to path
     */
    @Override
    public boolean isFile() {
      return false;
    }

    /**
     * Check if the target of the path is a RegularFile.
     *
     * @return True if the file corresponding to path is RegularFile
     * @throws Exception if no file found
     */
    @Override
    public boolean isRegularFile() {
      if (path == "/") {
        return false;
      }
      if (path == "/file") {
        return true;
      }
      if (path == "/path") {
        return false;
      }
      return true;
    }

    /**
     * Check if the target of the path is a Directory.
     *
     * @return True if the file corresponding to path is Directory
     * @throws Exception if no file found
     */
    @Override
    public boolean isDirectory() {
      if (path == "/") {
        return true;
      }
      if (path == "/path") {
        return true;
      }
      if (path == "/newpath") {
        return true;
      }
      return false;
    }

    /**
     * Check if given filename is a valid filename.
     *
     * @return True if filename is valid filename
     */
    @Override
    public boolean isValidFilename() {
      if (path == "//") {
        return false;
      }
      return true;
    }

    /**
     * Check if given filename is a valid regular filename.
     *
     * @return True if filename is a valid regular filename
     */
    @Override
    public boolean isValidRegularFilename() {
      return true;
    }

    /**
     * Return target file specified by path.
     *
     * @return Target file
     * @throws Exception if no file found
     */
    @Override
    public File getFile() throws Exception {
      if (path == "/file") {
        RegularFile reg = new RegularFile("file");
        reg.setParent(root);
        reg.write("content");
        return reg;
      }
      if (path == "/") {
        Directory root = new Directory("/");
        File file = new RegularFile("file");
        file.setParent(root);
        File path = new Directory("path");
        path.setParent((Directory) path);
        root.addFile("path", path);
        root.addFile("file", file);
        root.setParent(root);
        return root;
      }
      return null;
    }

    /**
     * Return the directory that path represents, and throw error when path is not
     * valid, does not exist or is not directory.
     *
     * @return The directory given path and working directory
     * @throws Exception When path is not valid, does not exist or is not
     *                   directory under current context
     */
    @Override
    public Directory getDirectory() throws Exception {
      if (path == "/") {
        Directory root = new Directory("/");
        File file = new RegularFile("file");
        file.setParent(root);
        File path = new Directory("path");
        path.setParent((Directory) path);
        root.addFile("path", path);
        root.addFile("file", file);
        root.setParent(root);
        return root;
      }
      if (path == "/path") {
        Directory path = new Directory("path");
        path.setParent(root);
      }
      return null;
    }

    /**
     * Return the regular file that path represents, and throw error when path is
     * not valid, does not exist or is not regular file.
     *
     * @return The regular file given path and working directory
     * @throws Exception When path is not valid, does not exist or is not regular
     *                   file under current context
     */
    @Override
    public RegularFile getRegularFile() throws Exception {
      if (path == "/file") {
        return new RegularFile("file");
      }

      return null;
    }

    /**
     * Set the root directory.
     *
     * @param root The root directory
     */
    @Override
    public void setRoot(Directory root) {
      root = root;
    }
  }
}
