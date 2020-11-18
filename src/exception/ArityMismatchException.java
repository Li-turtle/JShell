package exception;

/**
 * The class of exception for invalid number of arguments.
 *
 * @author Sihao Chen sihao.chen@mail.utoronto.ca
 */
public class ArityMismatchException extends Exception {

  /**
   * Extra information user wanted to add for the exception message.
   */
  private String extraInfo;

  /**
   * Init the ArityMismatchException class with no arguments.
   */
  public ArityMismatchException() {
    this.extraInfo = "";
  }

  /**
   * Init the ArityMismatchException class with extra information.
   *
   * @param extraInfo extra information that for the exception message
   */
  public ArityMismatchException(String extraInfo) {
    this.extraInfo = extraInfo;
  }

  /**
   * Get message of exception for ArityMismatchException.
   *
   * @return the message for exception
   */
  @Override
  public String getMessage() {
    if (extraInfo.equals("")) {
      return "cannot match the number of arguments";
    }
    return extraInfo + ": cannot match the number of arguments";
  }
}
