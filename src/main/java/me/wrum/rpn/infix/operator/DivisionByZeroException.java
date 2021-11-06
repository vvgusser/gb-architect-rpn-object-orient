package me.wrum.rpn.infix.operator;

import me.wrum.rpn.api.exception.ApplicationException;

public final class DivisionByZeroException extends ApplicationException {
  public DivisionByZeroException() {
    super("Division by zero");
  }
}
