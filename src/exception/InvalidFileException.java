package exception;

/**
 * The class of exception for files that has invalid type.
 *
 * @author Sihao Chen sihao.chen@mail.utoronto.ca
 */
public class InvalidFileException extends Exception {
  /**
   * Extra information user wanted to add for the exception message.
   */
  private String errorInfo;

  /**
   * Init the class of InvalidFileException.
   */
  public InvalidFileException() {
    this.errorInfo = "Invalid type of file";
  }

  /**
   * Init the class of InvalidFileException with additional message.
   * @param errorInfo the additional info added for exception message
   */
  public InvalidFileException(String errorInfo) {
    this.errorInfo = errorInfo;
  }

  /**
   * Get Message of InvalidFileException.
   *
   * @return the message for exception
   */
  @Override
  public String getMessage() {
    return errorInfo;
  }
}
