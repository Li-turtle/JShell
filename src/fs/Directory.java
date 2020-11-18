package fs;

import exception.FileNotExistException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The class that acts as a mock directory in JShell.
 *
 * @author Ziheng Zhuang ziheng.zhuang@mail.utoronto.ca
 * @version 1.7
 * @since 1.0
 */
public class Directory extends File implements Serializable {

  /**
   * The map that maps filenames to file objects.
   */
  private Map<String, File> fileList;

  /**
   * Initialize Directory with name & parent directory.
   *
   * @param name   The name of the directory
   * @param parent The parent directory
   */
  public Directory(String name, Directory parent) {
    super(name, parent);
    fileList = new HashMap<>();
    fileList.put(".", this);
    fileList.put("..", parent);
  }

  /**
   * Initialize Directory with name.
   *
   * @param name The name of the directory
   */
  public Directory(String name) {
    super(name);
    fileList = new HashMap<>();
    fileList.put(".", this);
  }

  /**
   * Set the parent directory for current directory.
   *
   * @param parent Parent directory
   */
  public void setParent(Directory parent) {
    super.setParent(parent);
    fileList.put("..", parent);
  }

  /**
   * Check if has file with name.
   *
   * @param name The name of the file
   * @return True if directory has a file with name
   */
  public boolean hasFile(String name) {
    return fileList.containsKey(name);
  }

  /**
   * Add file with name to directory.
   *
   * @param name The name of the file
   * @param file The file
   */
  public void addFile(String name, File file) {
    fileList.put(name, file);
  }

  /**
   * Remove file with name in the current directory.
   *
   * @param name The name of the file
   */
  public void removeFile(String name) throws FileNotExistException {
    if (!hasFile(name)) {
      throw new FileNotExistException(name);
    }
    fileList.remove(name);
  }

  /**
   * Rename file from oldName to newName.
   *
   * @param oldName Old name of the file
   * @param newName New name of the file
   */
  public void renameFile(String oldName,
      String newName) throws FileNotExistException {
    if (!hasFile(oldName)) {
      throw new FileNotExistException(oldName);
    }
    File file = fileList.get(oldName);
    // Set new name for target file
    file.setName(newName);

    // Set new name for target file under current directory
    fileList.put(newName, file);
    fileList.remove(oldName);
  }

  /**
   * Return the file with name.
   *
   * @param name The name of the file
   * @return The file with name
   */
  public File getFile(String name) throws FileNotExistException {
    if (!hasFile(name)) {
      throw new FileNotExistException(name);
    }
    return fileList.get(name);
  }

  /**
   * Return parent directory.
   *
   * @return Parent directory
   */
  public Directory getParentDirectory() {
    return (Directory) fileList.get("..");
  }

  /**
   * Return content of the directory.
   *
   * @return The content of the directory
   */
  public Set<String> getFileList() {
    return new HashSet<>(fileList.keySet());
  }

  /**
   * Get the content of the directory without . and ..
   *
   * @return The content of the directory without . and ..
   */
  public Set<String> getSubFileList() {
    Set<String> content = getFileList();
    content.remove(".");
    content.remove("..");
    return content;
  }

  /**
   * Return the full path of file.
   *
   * @return The full path of the file object
   */
  @Override
  public String toString() {
    return super.toString();
  }

  @Override
  protected Directory clone() throws CloneNotSupportedException {
    //    System.out.println(this.toString());
    Directory newObj = (Directory) super.clone();
    newObj.fileList = new HashMap<>();
    for (Map.Entry<String, File> item : fileList.entrySet()) {
      if (item.getKey() == "." || item.getKey() == "..") {
        continue;
      }
      newObj.fileList.put(item.getKey(), item.getValue().clone());
      newObj.fileList.get(item.getKey()).setParent(newObj);
    }
    return newObj;
  }

  @Override
  public Directory getCopy() throws CloneNotSupportedException {
    return clone();
  }

  /**
   * Check if there exits directory.
   *
   * @param dir Directory
   * @return true if contains the directory
   */
  public boolean contains(Directory dir) {
    for (Map.Entry<String, File> item : fileList.entrySet()) {
      if (item.getKey() == "." || item.getKey() == "..") {
        continue;
      }
      if (item.getValue() == dir) {
        return true;
      }
      if (item instanceof Directory && ((Directory) item).contains(dir)) {
        return true;
      }
    }
    return false;
  }
}
