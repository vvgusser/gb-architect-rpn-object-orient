package me.wrum.rpn.infix.rules;

import me.wrum.rpn.api.lexer.TokensRule;
import me.wrum.rpn.api.exception.TokenRuleException;
import me.wrum.rpn.api.lexer.Token;

import java.util.List;

import static me.wrum.rpn.api.lexer.Type.CLOSE_BRACE;
import static me.wrum.rpn.api.lexer.Type.OPEN_BRACE;
import static me.wrum.rpn.api.lexer.Type.OPERATOR;

/**
 * @author Vyacheslav Gusser proweber1@mail.ru
 */
public final class OpenBracesRule implements TokensRule {
  @Override
  public void assertTokens(String expr, List<Token> tokens) {
    for (var i = 0; i < tokens.size(); i++) {
      var token = tokens.get(i);

      if (!token.is(OPEN_BRACE)) {
        continue;
      }

      if (i < tokens.size() - 1 && tokens.get(i + 1).is(CLOSE_BRACE)) {
        throw new TokenRuleException(expr, "Empty parentheses are not allowed", i);
      }

      if (i > 0 && !tokens.get(i - 1).is(OPERATOR)) {
        throw new TokenRuleException(expr, "The parenthesis must be preceded by "
            + "an operator, unless the expression begins with a parenthesis", i);
      }
    } // end for-loop
  }
}
