package me.wrum.rpn.infix.rules;

import me.wrum.rpn.api.exception.TokenRuleException;
import me.wrum.rpn.api.lexer.Token;
import me.wrum.rpn.api.lexer.TokensRule;

import java.util.List;

import static me.wrum.rpn.api.lexer.Type.CLOSE_BRACE;
import static me.wrum.rpn.api.lexer.Type.NUMBER;

/**
 * The rule checks that there is always a number or another
 * closing parenthesis before the closing parenthesis
 *
 * @author Vyacheslav Gusser proweber1@mail.ru
 */
public final class ValidCloseBraceTokensRule implements TokensRule {
  @Override
  public void assertTokens(String expr, List<Token> tokens) {
    for (int i = 1; i < tokens.size(); i++) {
      var curr = tokens.get(i);
      var prev = tokens.get(i - 1);

      if (curr.is(CLOSE_BRACE) && !prev.is(NUMBER, CLOSE_BRACE)) {
        throw new TokenRuleException(expr, "There can only be a number or another "
            + "closing parenthesis before the closing parenthesis", curr.pos());
      }
    }
  }
}
