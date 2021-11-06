package me.wrum.rpn.infix.rules;

import me.wrum.rpn.api.lexer.TokensRule;
import me.wrum.rpn.api.exception.TokenRuleException;
import me.wrum.rpn.api.lexer.Token;

import java.util.List;

import static me.wrum.rpn.api.lexer.Type.CLOSE_BRACE;
import static me.wrum.rpn.api.lexer.Type.OPEN_BRACE;

/**
 * A rule that checks that the number of open parentheses matches
 * the number of closing ones. If it is not, throws an error.
 *
 * @author Vyacheslav Gusser proweber1@mail.ru
 */
public final class BracesCountRule implements TokensRule {
  // Implementation:
  //
  // The rule simply checks in a loop that the number of
  // tokens with the OPEN_BRACE type coincides with the number
  // of tokens with the CLOSE_BRACE type, if this is not the
  // case, then it throws out a message that contains information
  // on the number of opening and closing brackets (to be able
  // to find an error). The rule throws an error WITHOUT binding
  // to the character position. In the future, you can improve the
  // algorithm and search in pairs

  @Override
  public void assertTokens(String expr, List<Token> tokens) {
    var open = 0;
    var close = 0;

    for (var token : tokens) {
      if (token.is(OPEN_BRACE)) {
        open++;
      }

      if (token.is(CLOSE_BRACE)) {
        close++;
      }
    }

    if (open != close) {
      throw new TokenRuleException(expr, "The number of opening brackets does "
          + "not match the number of closing brackets [" + open + ":" + close + "]");
    }
  }
}
