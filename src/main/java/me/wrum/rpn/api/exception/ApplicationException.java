package me.wrum.rpn.api.exception;

/**
 * Base exception for all others to be handled by the main loop of the application
 *
 * @author Vyacheslav Gusser proweber1@mail.ru
 */
public abstract class ApplicationException extends RuntimeException {
  public ApplicationException() {
  }

  public ApplicationException(String message) {
    super(message);
  }

  public ApplicationException(String message, Throwable cause) {
    super(message, cause);
  }
}
