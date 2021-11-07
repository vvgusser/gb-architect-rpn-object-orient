package me.wrum.rpn.api.lexer;

import me.wrum.rpn.api.exception.TokenRuleException;
import me.wrum.rpn.infix.utils.Strings;

import java.util.List;

/**
 * Token validation context, stores some validation payload and can
 * throw validation errors
 *
 * @author Vyacheslav Gusser proweber1@mail.ru
 */
public record ValidationContext(String expr, List<Token> tokens) {
  public ValidationContext {
    if (Strings.isBlank(expr)) {
      throw new IllegalArgumentException("Expression must not be blank");
    }
    if (null == tokens || tokens.isEmpty()) {
      throw new IllegalArgumentException("Tokens must not be blank");
    }
  }

  /**
   * Return number of tokens that has this context
   *
   * @return positive number of tokens
   */
  public int numberOfTokens() {
    return tokens.size();
  }

  /**
   * Throws validation exception with given message without link to
   * source expression position
   *
   * @param message error message
   */
  public void invalid(String message) {
    throw new TokenRuleException(expr, message);
  }

  /**
   * Throws validation exception with given message and linking to
   * source expression token position
   *
   * @param message error message
   * @param pos     source expression token position
   */
  public void invalid(String message, int pos) {
    throw new TokenRuleException(expr, message, pos);
  }
}
