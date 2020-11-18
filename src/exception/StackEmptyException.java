package exception;

/**
 * The Exception that indicates empty stack.
 *
 * @author Ziheng Zhuang ziheng.zhuang@mail.utoronto.ca
 * @version 1.2
 * @since 1.2
 */
public class StackEmptyException extends Exception {

  /**
   * Get Message of StackEmptyException.
   *
   * @return the message for exception
   */
  @Override
  public String getMessage() {
    return "Stack empty: cannot pop elements";
  }
}
