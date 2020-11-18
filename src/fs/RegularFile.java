package fs;

import java.io.Serializable;

/**
 * The class that acts as a mock regular file (holds content) in JShell.
 *
 * @author Ziheng Zhuang ziheng.zhuang@mail.utoronto.ca
 * @version 1.7
 * @since 1.0
 */
public class RegularFile extends File implements Serializable {

  /**
   * The content of the regular file.
   */
  private String content = "";

  /**
   * Init RegularFile with name.
   *
   * @param name The name of RegularFile
   */
  public RegularFile(String name) {
    super(name);
  }

  /**
   * Init RegularFile with name & parent directory.
   *
   * @param name   The name of RegularFile
   * @param parent The parent directory
   */
  public RegularFile(String name, Directory parent) {
    super(name, parent);
  }

  /**
   * Overwrite contents to RegularFile.
   *
   * @param content The content
   */
  public void write(String content) {
    this.content = content;
  }

  /**
   * Append contents to RegularFile.
   *
   * @param content The appending content
   */
  public void append(String content) {
    this.content += content;
  }

  /**
   * Get the content of the RegularFile.
   *
   * @return The content of the RegularFile
   */
  public String read() {
    return content;
  }

  @Override
  protected RegularFile clone() throws CloneNotSupportedException {
    return (RegularFile) super.clone();
  }

  @Override
  public RegularFile getCopy() throws CloneNotSupportedException {
    return clone();
  }
}
