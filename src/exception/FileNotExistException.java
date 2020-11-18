package exception;

/**
 * The class of exception for file not exist.
 *
 * @author Sihao Chen sihao.chen@mail.utoronto.ca
 */
public class FileNotExistException extends Exception {

  /**
   * The file that does not exist.
   */
  private String file;

  /**
   * Extra information user wanted to add for the exception message.
   */
  private String extraInfo;

  /**
   * Init FileNotExistException class.
   *
   * @param file the file that does not exist
   */
  public FileNotExistException(String file) {
    this.file = file;
    this.extraInfo = "";
  }

  /**
   * Init the FileNotExistException class with extra information.
   *
   * @param file      the file that already exist
   * @param extraInfo extra information that for the exception message
   */
  public FileNotExistException(String file, String extraInfo) {
    this.file = file;
    this.extraInfo = extraInfo;
  }

  /**
   * Get Message of FileNotExistException.
   *
   * @return the message for exception
   */
  @Override
  public String getMessage() {
    return extraInfo + "\'" + file + "\': No such file or directory";
  }
}
