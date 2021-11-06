package me.wrum.rpn.api.lexer;

import me.wrum.rpn.api.exception.EmptyExpressionException;
import me.wrum.rpn.api.exception.TokenRuleException;

import java.util.List;

/**
 * Each implementation of this interface must split the initial
 * expression into tokens that can be further processed by the
 * evaluator or validated by rules.
 *
 * @author Vyacheslav Gusser proweber1@mail.ru
 * @apiNote Any lexer should be whitespace insensitive
 */
public interface Lexer {
  /**
   * Splits the original expression into tokens using expression-specific rules
   *
   * @param expression source raw expression, may be empty or {@code null}
   *
   * @return collection of tokens from source expression
   *
   * @throws TokenRuleException       in cases when source expression has errors
   * @throws EmptyExpressionException when source expression is blank or {@code null}
   */
  List<Token> tokenize(String expression);
}
