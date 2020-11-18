package test.fs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import exception.FileNotExistException;
import fs.Directory;
import org.junit.Before;
import org.junit.Test;

public class DirectoryTest {

  private Directory directory;

  /**
   * setUp method of this JUnitTest.
   * 
   * @throws Exception various Exception
   */
  @Before
  public void setUp() {
    directory = new Directory("Dir");
    directory.setParent(directory);
  }

  @Test
  public void setParentTest() {
    Directory newDir = new Directory("NewDir");
    directory.setParent(newDir);
    assertEquals(directory.getParent(), newDir);
  }

  @Test
  public void hasFileTest() {
    directory.addFile("NewFile", new Directory("NewFile"));
    assertTrue(directory.hasFile("NewFile"));
  }

  @Test
  public void addFileTest() {
    directory.addFile("NewFile", new Directory("NewFile"));
    assertTrue(directory.hasFile("NewFile"));
  }

  @Test
  public void removeFileTest() throws FileNotExistException {
    directory.addFile("NewFile", new Directory("NewFile"));
    directory.removeFile("NewFile");
    assertFalse(directory.hasFile("NewFile"));
  }

  @Test
  public void renameFileTest() throws FileNotExistException {
    directory.addFile("NewFile", new Directory("NewFile"));
    directory.renameFile("NewFile", "File");
    assertTrue(directory.hasFile("File"));
  }

  @Test
  public void getFileTest() throws FileNotExistException {
    Directory newDir = new Directory("File");
    directory.addFile("File", newDir);
    assertEquals(newDir, directory.getFile("File"));
  }

  @Test
  public void getFileList() {
    Directory F1 = new Directory("F1");
    Directory F2 = new Directory("F2");
    directory.addFile("F1", F1);
    directory.addFile("F2", F2);
    assertTrue(directory.getFileList().contains("F1"));
    assertTrue(directory.getFileList().contains("F2"));
  }

  @Test
  public void getSubFileList() {
    Directory G1 = new Directory("G1");
    Directory G2 = new Directory("G2");
    directory.addFile("G1", G1);
    directory.addFile("G2", G2);
    assertTrue(directory.getSubFileList().contains("G1"));
    assertTrue(directory.getSubFileList().contains("G2"));
  }

  @Test
  public void toStringTest() {
    assertEquals("/", directory.toString());
  }

  @Test
  public void getCopyTest() throws CloneNotSupportedException {
    Directory clone = directory.getCopy();
    clone.setParent(clone);
    assertNotEquals(clone.hashCode(), directory.hashCode());
    assertEquals(clone.toString(), directory.toString());
  }

  @Test
  public void containsTest() {
    Directory newDir = new Directory("NewDir");
    directory.addFile("NewDir", newDir);
    assertTrue(directory.contains(newDir));
  }
}
