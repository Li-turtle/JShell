package fs;

import java.io.Serializable;

/**
 * The class that acts as a generic file in JShell.
 *
 * @author Ziheng Zhuang ziheng.zhuang@mail.utoronto.ca
 * @version 1.3
 * @since 1.0
 */
public class File implements Cloneable, Serializable {

  /**
   * The parent directory of the current file.
   */
  private Directory parent;

  /**
   * The name of the file.
   */
  private String name;

  /**
   * Init File with name.
   *
   * @param name The name of the file
   */
  public File(String name) {
    this.name = name;
  }

  /**
   * Init File with name & parent directory.
   *
   * @param name   The name of the file
   * @param parent The parent directory
   */
  public File(String name, Directory parent) {
    this.name = name;
    this.parent = parent;
  }

  /**
   * Set the parent directory for file.
   *
   * @param parent The parent directory
   */
  public void setParent(Directory parent) {
    this.parent = parent;
  }

  /**
   * Return the parent directory.
   *
   * @return The parent directory
   */
  public Directory getParent() {
    return parent;
  }

  /**
   * Return the name of the file.
   *
   * @return the name of the file
   */
  public String getName() {
    return name;
  }

  /**
   * Set name of the file as name.
   *
   * @param name New name of the file
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Return the full path of file.
   *
   * @return The full path of the file object
   */
  @Override
  public String toString() {
    if (parent == this) {
      return "/";
    }
    String parStr = parent.toString();
    return parStr + (parStr.endsWith("/") ? "" : "/") + getName();
  }

  @Override
  protected File clone() throws CloneNotSupportedException {
    return (File) super.clone();
  }

  public File getCopy() throws CloneNotSupportedException {
    return clone();
  }
}
