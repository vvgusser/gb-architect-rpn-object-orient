package me.wrum.rpn.infix.rules;

import me.wrum.rpn.api.lexer.TokensRule;
import me.wrum.rpn.api.lexer.Token;

import java.util.List;

/**
 * This is a service rule that in itself can aggregate many
 * other rules and pass them an expression with tokens for
 * verification. This solution is made to simplify lexers.
 *
 * @author Vyacheslav Gusser proweber1@mail.ru
 */
public final class CompositeRule implements TokensRule {
  private final TokensRule[] rules;

  /**
   * Primary constructor.
   * <p>
   * A constructor that creates a composite rule from a
   * list of other rules. The constructor may throw an
   * exception when a composite rule is created without
   * tweaks
   *
   * @param rules the rules of this rule
   *
   * @throws IllegalArgumentException when rules is empty
   */
  public CompositeRule(TokensRule... rules) {
    if (rules.length < 1) {
      throw new IllegalArgumentException("Rules must not be null or empty");
    }
    this.rules = rules;
  }

  @Override
  public void assertTokens(String originalExpr, List<Token> tokens) {
    for (var rule : rules) {
      rule.assertTokens(originalExpr, tokens);
    }
  }
}
