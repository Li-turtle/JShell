package fs;

import exception.FileExistException;
import exception.FileNotExistException;
import exception.InvalidFileException;
import exception.InvalidFormatException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import util.ArgParser;
import util.FileNavigator;

/**
 * The class that implements FileSystem. Provide higher level file system management methods to
 * commands
 *
 * @author Ziheng Zhuang ziheng.zhuang@mail.utoronto.ca Yining Wang wyn.wang@mail.utoronto.ca
 * @version 1.7
 * @since 1.7
 */
public class JFS implements FileSystem, Serializable {

  /**
   * The root of the File System.
   */
  private Directory root = null;

  /**
   * The current working directory.
   */
  private Directory workingDirectory = null;

  /**
   * The file navigator used to provide file navigation feature.
   */
  private FileNavigator navigator = null;

  /**
   * Initialize JFS with file navigator injected. Setup working directory and root directory
   *
   * @param navigator The injected file navigator object
   */
  public JFS(FileNavigator navigator) {
    root = new Directory("/");
    root.setParent(root);
    workingDirectory = root;
    this.navigator = navigator;
    this.navigator.setRoot(root);
  }

  /**
   * Return the content of file given path, and throw exception if file does not exist, filename is
   * invalid or path does not represent a regular file.
   *
   * @param path The path of the regular file
   * @return The content of the given path
   * @throws Exception When path is invalid path name or file does not exist or file is not a
   *                   regular file
   */
  @Override
  public String getFileContent(String path) throws Exception {
    navigator.setPath(path, workingDirectory);
    if (!navigator.isRegularFile()) {
      throw new InvalidFileException("Not a regular file");
    }
    return ((RegularFile) navigator.getFile()).read();
  }

  /**
   * Return the root directory of the file system.
   *
   * @return The root of the file system
   */
  @Override
  public Directory getRoot() {
    return root;
  }

  /**
   * Set the working directory of the file system to path, and throw exception if path is invalid or
   * path does not exist or path is not a directory.
   *
   * @param path The path of the directory you want to change to
   * @throws Exception When path does not exist or is not a directory
   */
  @Override
  public void setWorkingDirectory(String path) throws Exception {
    navigator.setPath(path, workingDirectory);
    if (!navigator.isDirectory()) {
      throw new InvalidFileException("Not a directory");
    }
    workingDirectory = (Directory) navigator.getFile();
  }

  /**
   * Return the current working directory in string format.
   *
   * @return The working directory in string format
   */
  @Override
  public String getWorkingDirectory() {
    return workingDirectory.toString();
  }

  /**
   * Return the path names of the files in current working directory.
   *
   * @return The path names of the files (including regular files and directories) in current
   * working directory
   */
  @Override
  public Set<String> getDirectoryContent() {
    return workingDirectory.getSubFileList();
  }

  /**
   * Return the path names of the files in directory path, and throw exception if path is invalid,
   * path does not exist or path does not represent directory.
   *
   * @param path The target path name
   * @return The contents in path (including regular files and directories)
   * @throws Exception When path is invalid, path does not exist, or path does not represent
   *                   directory
   */
  @Override
  public Set<String> getDirectoryContent(String path) throws Exception {
    navigator.setPath(path, workingDirectory);
    if (!navigator.isDirectory()) {
      throw new InvalidFileException("Not a directory");
    }
    return ((Directory) navigator.getFile()).getSubFileList();
  }

  /**
   * Return a set of strings that is prefixed by prefix.
   *
   * @param prefix The prefix
   * @param s      A set of string
   * @return A set of strings that is prefixed by prefix
   */
  private Set<String> addPrefix(String prefix, Set<String> s) {
    Set<String> result = new HashSet<>();
    for (String str : s) {
      result.add(prefix + str);
    }
    return result;
  }

  /**
   * Return the absolute paths of contents in the current working directory (including regular files
   * and directories).
   *
   * @return The absolute paths of contents in current working directory
   */
  @Override
  public Set<String> getDirectoryContentFullPath() {
    return addPrefix(
        workingDirectory.toString() + (workingDirectory.equals(root.toString()) ? "" : "/"),
        getDirectoryContent()
    );
  }

  /**
   * Return the full paths of contents in path, and throw error if path is invalid, path does not
   * exist or path does not represent directory.
   *
   * @param path The path of target directory
   * @return The full path of contents in path, including regular files and directories
   * @throws Exception When path is invalid, path does not exist, or path does not represent
   *                   directory
   */
  @Override
  public Set<String> getDirectoryContentFullPath(String path)
      throws Exception {
    return addPrefix(path + (path.equals(root.toString()) ? "" : "/"),
        getDirectoryContent(path));
  }

  /**
   * Check if path represents a regular file.
   *
   * @param path The path
   * @return True of path represents a regular file
   */
  @Override
  public boolean isRegularFile(String path) {
    navigator.setPath(path, workingDirectory);
    return navigator.isRegularFile();
  }

  /**
   * Check if path represents a directory.
   *
   * @param path The path to check
   * @return True if path represents a directory
   */
  @Override
  public boolean isDirectory(String path) {
    navigator.setPath(path, workingDirectory);
    return navigator.isDirectory();
  }

  /**
   * Get the parent path of the current path.
   *
   * @param path The current path
   * @return The parent path of current path
   */
  private String getParent(String path) {
    if (path.endsWith("/") && path != "/") {
      path = path.substring(0, path.length() - 1);
    }
    if (!path.contains("/")) {
      return ".";
    }
    return path.lastIndexOf("/") == 0 
        ? "/" : path.substring(0, path.lastIndexOf("/"));
  }

  /**
   * Return the relative path of the current path.
   *
   * @param path The current path
   * @return The relative path of current path
   */
  private String getCur(String path) {
    if (path.endsWith("/")) {
      path = path.substring(0, path.length() - 1);
    }
    if (!path.contains("/")) {
      return path;
    }
    return path.substring(path.lastIndexOf("/") + 1);
  }

  /**
   * Create a path, and throw error when parent of path does not exist, path is a invalid name, or
   * path already exist.
   *
   * @param path The path to create
   * @throws Exception When parent of path does not exist, path is a invalid name, or path already
   *                   exist
   */
  @Override
  public void createDirectory(String path) throws Exception {
    navigator.setPath(path, workingDirectory);
    if (navigator.isFile()) {
      throw new FileExistException(path);
    }
    navigator.setPath(getParent(path), workingDirectory);
    Directory directory = navigator.getDirectory();
    String curName = getCur(path);
    if (!ArgParser.isValidFilename(curName)) {
      throw new InvalidFormatException(path);
    }
    File newFile = new Directory(curName);
    directory.addFile(curName, newFile);
    newFile.setParent(directory);
  }

  /**
   * Get the regular file that path represents, create a new regular file if does not exist. Throw
   * error when file cannot be created when not exist, path is invalid, or path is a directory
   *
   * @param path The path
   * @return the regular file that path represents
   * @throws Exception When file cannot be created when not exist, path is invalid, or path is a
   *                   directory
   */
  private RegularFile getRegularFile(String path) throws Exception {
    navigator.setPath(path, workingDirectory);
    if (!navigator.isValidFilename() || !navigator.isValidRegularFilename()) {
      throw new InvalidFormatException(path);
    }
    if (navigator.isFile() && !navigator.isRegularFile()) {
      throw new InvalidFileException("Not a regular file");
    }
    if (navigator.isRegularFile()) {
      return navigator.getRegularFile();
    }
    navigator.setPath(getParent(path), workingDirectory);
    Directory directory = navigator.getDirectory();
    String curName = getCur(path);
    if (!ArgParser.isValidFilename(curName)) {
      throw new InvalidFormatException(path);
    }
    File newFile = new RegularFile(curName, directory);
    directory.addFile(curName, newFile);
    return (RegularFile) newFile;
  }

  /**
   * Write content to regular file with path path. If path does not exist, create one. Throw error
   * when file does not exist and cannot be created, or path is a directory
   *
   * @param path    The path of regular file
   * @param content The content that writes to file
   * @throws Exception When files does not exist and cannot be created, or path is a directory
   */
  @Override
  public void writeToFile(String path, String content) throws Exception {
    RegularFile regularFile = getRegularFile(path);
    regularFile.write(content);
  }

  /**
   * Append content to regular file with path path. If path does not exist, create one. Throw error
   * when file does not exist and cannot be created, or path is a directory
   *
   * @param path    The path of regular file
   * @param content The content that writes to file
   * @throws Exception When files does not exist and cannot be created, or path is a directory
   */
  @Override
  public void appendToFile(String path, String content) throws Exception {
    RegularFile regularFile = getRegularFile(path);
    regularFile.append(content);
  }

  /**
   * Make a new copy of regular file at old path to new path. Throw exception when old path is
   * invalid, new path is invalid, old path does not exist, old path is not regular file or new file
   * cannot be created
   *
   * @param oldPath The path of the regular file to be duplicated
   * @param newPath The new path of the regular file
   * @throws Exception When old path is invalid, new path is invalid, old path does not exist, old
   *                   path is not regular file or new file cannot be created
   */
  @Override
  public void duplicateRegularFile(String oldPath, String newPath) throws Exception {
    navigator.setPath(oldPath, workingDirectory);
    RegularFile file = navigator.getRegularFile();
    navigator.setPath(newPath, workingDirectory);
    if (navigator.isDirectory()) {
      navigator.getDirectory().addFile(file.getName(), file.getCopy());
      return;
    }
    RegularFile newFile = getRegularFile(newPath);
    newFile.write(file.read());
  }

  /**
   * Create new path and copy content of oldDir to newPath
   *
   * @param oldDir The old directory
   * @param newPath The new Path
   * @throws Exception When cannot create file
   */
  private void dupDirOnCreate(Directory oldDir, String newPath) throws Exception {
    createDirectory(newPath);
    navigator.setPath(newPath, workingDirectory);
    Directory newDir = navigator.getDirectory();
    if (oldDir.contains(newDir) || oldDir == newDir) {
      throw new InvalidFileException("Cannot be duplicated");
    }
    String newName = newDir.getName();
    Directory parentDir = newDir.getParent();
    parentDir.addFile(newName, oldDir.getCopy());
    parentDir.getFile(newName).setName(newName);
    newDir = (Directory) parentDir.getFile(newName);
    newDir.setParent(parentDir);
    newDir.addFile(".", newDir);
  }

  /**
   * Make a new copy of directory at old path to new path. Throw exception when old path is invalid,
   * new path is invalid, old path does not exist, old path is not directory or new path cannot be
   * created
   *
   * @param oldPath The path of the directory to be duplicated
   * @param newPath The new path of the directory
   * @throws Exception When old path is invalid, new path is invalid, old path does not exist, old
   *                   path is not directory or new path cannot be created
   */
  @Override
  public void duplicateDirectory(String oldPath, String newPath) throws Exception {
    navigator.setPath(oldPath, workingDirectory);
    Directory oldDir = navigator.getDirectory();

    navigator.setPath(newPath, workingDirectory);

    if (navigator.isDirectory()) {
      // If directory exist
      Directory newDir = navigator.getDirectory();

      if (oldDir.contains(newDir) || oldDir == newDir) {
        throw new InvalidFileException("Cannot be duplicated");
      }

      newDir.addFile(oldDir.getName(), oldDir.getCopy());
      Directory innerDir = (Directory) newDir.getFile(oldDir.getName());
      innerDir.setParent(newDir);
      innerDir.addFile(".", innerDir);
    } else {
      dupDirOnCreate(oldDir, newPath);
    }
  }

  /**
   * Remove the file that path represents. Throw exception when path is invalid, path does not
   * exist
   *
   * @param path The path of the file
   * @throws Exception When path is invalid, path does not exist
   */
  @Override
  public void removeFile(String path) throws Exception {
    navigator.setPath(path, workingDirectory);
    File file = navigator.getFile();
    if (removable(path)) {
      file.getParent().removeFile(file.getName());
    }
  }

  /**
   * Check if path can be remove.
   *
   * @param path The path to check
   * @return True if path can be removed
   */
  @Override
  public boolean removable(String path) throws Exception {
    navigator.setPath(path, workingDirectory);
    if (navigator.getFile() == root) {
      throw new InvalidFileException("Cannot remove root directory");
    }
    if (navigator.isRegularFile()) {
      return true;
    }
    if (navigator.getFile() == workingDirectory 
        || ((Directory) navigator.getFile()).contains(workingDirectory)) {
      throw new InvalidFileException("Cannot remove working directory");
    }
    return true;
  }

  /**
   * Check if path is valid.
   *
   * @param path The path to check
   * @throws Exception When path is invalid or path does not exist
   */
  @Override
  public void verifyPath(String path) throws Exception {
    navigator.setPath(path, workingDirectory);
    if (!navigator.isValidFilename()) {
      throw new InvalidFormatException("Not a valid path name");
    }
    if (!navigator.isFile()) {
      throw new FileNotExistException(path);
    }
  }
}
