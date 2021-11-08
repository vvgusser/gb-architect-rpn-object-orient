package me.wrum.rpn.api.lexer;

import java.util.stream.Stream;

/**
 * Representing a token parsed by a lexer
 *
 * @author Vyacheslav Gusser proweber1@mail.ru
 * @see Lexer
 */
public record Token(String value, int pos, Type type) {

  /**
   * The method checks that the current token has a type
   * that matches at least one of the ones passed to the method
   *
   * @param types for check
   *
   * @return {@code true} when has match with given types
   * {@code false} otherwise
   */
  public boolean is(Type... types) {
    if (types.length == 0) {
      throw new IllegalArgumentException("Types must not be blank");
    }

    return Stream.of(types).anyMatch(t -> t == this.type);
  }
}
