package exception;

/**
 * The Exception that indicates invalid argument format.
 *
 * @author Ziheng Zhuang ziheng.zhuang@mail.utoronto.ca
 * @version 1.2
 * @since 1.1
 */
public class InvalidFormatException extends Exception {

  /**
   * The error log of the InvalidFormatException.
   */
  private String errLog;

  /**
   * Init InvalidFormatException with error log msg.
   * @param msg The error log
   */
  public InvalidFormatException(String msg) {
    errLog = msg;
  }

  /**
   * Get Message of InvalidFormatException.
   *
   * @return the message for exception
   */
  @Override
  public String getMessage() {
    return "\'" + errLog + "\'" + ": Incorrect format";
  }
}
