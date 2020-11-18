package exception;

/**
 * The class of exception for file that already exist.
 *
 * @author Sihao Chen sihao.chen@mail.utoronto.ca
 */
public class FileExistException extends Exception {

  /**
   * The file that already exist.
   */
  private String file;

  /**
   * Extra information user wanted to add for the exception message.
   */
  private String extraInfo;

  /**
   * Init the FileExistException class.
   *
   * @param file the file that already exist
   */
  public FileExistException(String file) {
    this.file = file;
    this.extraInfo = "";
  }

  /**
   * Init the FileExistException class with extra information.
   *
   * @param file      the file that already exist
   * @param extraInfo extra information that for the exception message
   */
  public FileExistException(String file, String extraInfo) {
    this.file = file;
    this.extraInfo = extraInfo;
  }

  /**
   * Get message of FileExistException.
   *
   * @return the message for exception
   */
  @Override
  public String getMessage() {
    return extraInfo + "\'" + file + "\': File exists";
  }
}
