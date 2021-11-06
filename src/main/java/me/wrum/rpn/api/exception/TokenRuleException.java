package me.wrum.rpn.api.exception;

/**
 * An exception that can be thrown by validation rules or a lexer.
 * This exception is user friendly and will highlight an invalid
 * token in the original expression. If an exception is thrown without
 * a positional parameter, then the final message will be without
 * specifying the location of the error.
 * <p>
 * <p>
 * Example with binding position in source expression
 * <pre>
 * >>> 2 * ()
 * 2 * ()
 *   ^
 * error: Empty parentheses are not allowed
 * </pre>
 * <p>
 * Example without reference to position in the original expression
 * <pre>
 * >>> 2 * (9 - 3))
 * error: The number of opening brackets does not match the number of closing brackets [1:2]
 * </pre>
 *
 * @author Vyacheslav Gusser proweber1@mail.ru
 */
public final class TokenRuleException extends ApplicationException {
  private final String expr;
  private final String message;
  private final int position;

  public TokenRuleException(String expr, String message) {
    this(expr, message, -1);
  }

  public TokenRuleException(String expr, String message, int position) {
    this.expr = expr;
    this.message = message;
    this.position = position;
  }

  @Override
  public String getMessage() {
    return buildPositionedString() + "error: " + message;
  }

  private String buildPositionedString() {
    return position >= 0 ? expr + "\n" + " ".repeat(position) + "^\n" : "";
  }
}
