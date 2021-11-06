package me.wrum.rpn.api.exception;

/**
 * An exception should be thrown when an empty expression is passed to the lexer
 *
 * @author Vyacheslav Gusser proweber1@mail.ru
 * @see me.wrum.rpn.api.lexer.Lexer#tokenize(String)
 */
public final class EmptyExpressionException extends ApplicationException {
  public EmptyExpressionException() {
    super("Empty expressions are not allowed");
  }
}
