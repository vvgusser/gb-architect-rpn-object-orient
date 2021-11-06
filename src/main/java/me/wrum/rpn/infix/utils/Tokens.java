package me.wrum.rpn.infix.utils;

import me.wrum.rpn.api.lexer.Token;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Class with utilities for tokens
 *
 * @author Vyacheslav Gusser proweber1@mail.ru
 */
public final class Tokens {
  private Tokens() {
    throw new UnsupportedOperationException();
  }

  /**
   * Converts a collection of tokens to a string where
   * each token is separated by spaces
   *
   * @param tokens for build string
   *
   * @return tokens string separated by space
   */
  public static String toString(Collection<Token> tokens) {
    return tokens.stream()
        .map(Token::value)
        .collect(Collectors.joining(" "));
  }
}
