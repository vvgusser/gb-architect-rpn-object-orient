package me.wrum.rpn.api.lexer;

import me.wrum.rpn.api.exception.TokenRuleException;

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
   * @param ctx context of validation
   *
   * @throws TokenRuleException When tokens fail validation
   */
  void assertTokens(ValidationContext ctx);
}
