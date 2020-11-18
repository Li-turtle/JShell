package test.command;

import command.Mv;
import exception.ArityMismatchException;
import exception.FileNotExistException;
import exception.InvalidFileException;
import fs.Directory;
import fs.FileSystem;
import org.junit.Before;
import org.junit.Test;
import util.IO;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import static org.junit.Assert.*;


public class MvTest {

  private Mv mv;
  private FileSystem fs;
  private IO io;

  /**
   * setUp method of this JUnitTest.
   *
   * @throws Exception various Exception
   */
  @Before
  public void setUp() throws Exception {
    fs = new MockFileSystem();
    mv = new Mv(fs);
    io = new IO();
  }

  @Test
  public void testGetDoc() {
    // Test whether getDoc() returns correctly the documentation
    String rst = mv.getDoc();
    String expected
        = "NAME\n"
        + "    mv - move (rename) files\n\n"
        + "SYNOPSIS\n"
        + "    mv OLDPATH NEWPATH\n\n"
        + "DESCRIPTION\n"
        + "    Rename OLDPATH to NEWPATH if OLDPATH and NEWPATH\n"
        + "    have same type or NEWPATH not created, or move OLDPATH\n"
        + "    to NEWPATH if NEWPATH is regular file and NEWPATH\n"
        + "    is directory.\n";
    assertEquals(expected, rst);
  }

  @Test
  public void testExec1() throws Exception {
    // Move directory to directory
    mv.exec(new String[]{"mv", "dir1", "dir2"}, io);
    assertEquals("move", ((MockFileSystem) fs).getFileRecord());
  }

  @Test
  public void testExec2() throws Exception {
    // Move directory to new path
    mv.exec(new String[]{"mv", "dir1", "newPath"}, io);
    assertEquals("create", ((MockFileSystem) fs).getFileRecord());
  }

  @Test
  public void testExec3() throws Exception {
    // Move regular file to directory
    mv.exec(new String[]{"mv", "reg1", "dir1"}, io);
    assertEquals("move", ((MockFileSystem) fs).getFileRecord());
  }

  @Test
  public void testExec4() throws Exception {
    // Move regular file to regular file
    mv.exec(new String[]{"mv", "reg1", "reg2"}, io);
    assertEquals("overwrite", ((MockFileSystem) fs).getFileRecord());
  }

  @Test
  public void testExec5() throws Exception {
    // Move regular file no new path
    mv.exec(new String[]{"mv", "reg1", "newPath"}, io);
    assertEquals("create", ((MockFileSystem) fs).getFileRecord());
  }

  @Test(expected = ArityMismatchException.class)
  public void testExecException1() throws Exception {
    // Exception of invalid number of arguments
    mv.exec(new String[]{"mv", "dir1"}, io);
  }

  @Test(expected = InvalidFileException.class)
  public void testExecException2() throws Exception {
    // Exception of removing non-removable directory
    mv.exec(new String[]{"mv", "non-removable", "dir2"}, io);
  }

  @Test(expected = FileNotExistException.class)
  public void testExecException3() throws Exception {
    // Exception of source file does not exist
    mv.exec(new String[]{"mv", "non-exist", "dir2"}, io);
  }

  @Test(expected = InvalidFileException.class)
  public void testExecException4() throws Exception {
    // Exception of mv directory onto regular file
    mv.exec(new String[]{"mv", "dir1", "reg1"}, io);
  }

  @Test(expected = FileNotExistException.class)
  public void testExecException5() throws Exception {
    // Exception of target path is invalid (source is directory)
    mv.exec(new String[]{"mv", "dir1", "invalid"}, io);
  }

  @Test(expected = FileNotExistException.class)
  public void testExecException6() throws Exception {
    // Exception of target path is invalid (source is regular file)
    mv.exec(new String[]{"mv", "reg1", "invalid"}, io);
  }

  @Test(expected = InvalidFileException.class)
  public void testExecException7() throws Exception {
    // Exception of target path is a child of the source
    mv.exec(new String[]{"mv", "dir1", "pathChild"}, io);
  }

  private class MockFileSystem implements FileSystem {
    private String fileRecord = "";

    public String getFileRecord() {
      return fileRecord;
    }

    @Override
    public boolean isDirectory(String path) {
      List<String> dirList = Arrays
          .asList("dir1", "dir2", "pathChild");
      return dirList.contains(path);
    }

    @Override
    public boolean isRegularFile(String path) {
      List<String> regList = Arrays
          .asList("reg1", "reg2");
      return regList.contains(path);
    }

    @Override
    public boolean removable(String path) throws Exception {
      List<String> notRemovableList = Arrays
          .asList("non-removable");
      if (notRemovableList.contains(path)) {
        throw new InvalidFileException();
      }
      return true;
    }

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
      if (newPath.equals("invalid")) {
        throw new FileNotExistException(newPath);
      } else if (isRegularFile(newPath)) {
        throw new InvalidFileException();
      } else if (isDirectory(newPath)) {
        if (newPath.equals("pathChild")) {
          throw new InvalidFileException();
        }
        fileRecord = "move";
      } else {
        fileRecord = "create";
      }
    }

    @Override
    public void duplicateRegularFile(String oldPath, String newPath) throws Exception {
      if (newPath.equals("invalid")) {
        throw new FileNotExistException(newPath);
      } else if (isDirectory(newPath)) {
        fileRecord = "move";
      } else if (isRegularFile(newPath)) {
        fileRecord = "overwrite";
      } else {
        fileRecord = "create";
      }
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