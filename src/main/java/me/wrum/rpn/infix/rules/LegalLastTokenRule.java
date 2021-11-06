package me.wrum.rpn.infix.rules;

import me.wrum.rpn.api.lexer.TokensRule;
import me.wrum.rpn.api.exception.TokenRuleException;
import me.wrum.rpn.api.lexer.Token;

import java.util.List;

import static me.wrum.rpn.api.lexer.Type.OPERATOR;

/**
 * Checks that the expression does NOT end with a statement. This
 * may mean that the expression is simply incorrectly constructed
 * or a postfix expression was passed that is not supported by
 * this package.
 *
 * @author Vyachelsav Gusser proweber1@mail.ru
 */
public final class LegalLastTokenRule implements TokensRule {
  @Override
  public void assertTokens(String expr, List<Token> tokens) {
    var last = tokens.get(tokens.size() - 1);

    if (last.is(OPERATOR)) {
      throw new TokenRuleException(expr, "Expression cannot end with operator", last.pos());
    }
  }
}
