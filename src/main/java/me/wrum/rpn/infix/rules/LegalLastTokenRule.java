package me.wrum.rpn.infix.rules;

import me.wrum.rpn.api.lexer.TokensRule;
import me.wrum.rpn.api.lexer.ValidationContext;

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
  public void assertTokens(ValidationContext ctx) {
    var tokens = ctx.tokens();
    var last = tokens.get(tokens.size() - 1);

    if (last.is(OPERATOR)) {
      ctx.invalid("Expression cannot end with operator", last.pos());
    }
  }
}
