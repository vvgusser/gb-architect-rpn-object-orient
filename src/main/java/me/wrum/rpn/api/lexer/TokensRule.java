package me.wrum.rpn.api.lexer;

import me.wrum.rpn.api.exception.TokenRuleException;
import me.wrum.rpn.api.lexer.Token;

import java.util.List;

/**
 * This interface defines validation rules that can be applied
 * to tokens after tokenization by a lexer.
 *
 * @author Vyacheslav Gusser proweber1@mail.ru
 */
@FunctionalInterface
public interface TokensRule {

  /**
   * The method should do token validation from the arguments
   *
   * @param originalExpr The original expression processed by the lexer
   *                     It is never {@code null} and never empty.
   * @param tokens       List of tokens provided by lexer
   *
   * @throws TokenRuleException When tokens fail validation
   */
  void assertTokens(String originalExpr, List<Token> tokens);
}
