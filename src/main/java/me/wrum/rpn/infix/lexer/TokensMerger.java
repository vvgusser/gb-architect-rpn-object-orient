package me.wrum.rpn.infix.lexer;

import me.wrum.rpn.api.lexer.Token;

import java.util.ArrayList;
import java.util.List;

import static me.wrum.rpn.api.lexer.Type.NUMBER;
import static me.wrum.rpn.api.lexer.Type.OPERATOR;

/**
 * Helper class that helps to compress some tokens. In our
 * case, it connects the number and the adjacent unary operator
 * when we have several operators near.
 *
 * @author Vyacheslav Gusser proweber1@mail.ru
 */
final class TokensMerger {
  List<Token> merge(List<Token> tokens) {
    var processed = new ArrayList<Token>();

    var i = 0;
    var N = tokens.size();

    while (i < N) {
      var curr = tokens.get(i);

      if (i < N - 1 && curr.is(OPERATOR) && tokens.get(i + 1).is(NUMBER) && (i == 0 || tokens.get(i - 1).is(OPERATOR))) {
        processed.add(merge(curr, tokens.get(++i)));
      } else {
        processed.add(curr);
      }

      i++;
    }

    return processed;
  }

  /**
   * This merge two tokens into one and set position of new token by
   * subtracting <b>1</b> from second token position
   *
   * @param a first token (operator)
   * @param b second token (number)
   *
   * @return instance of merged token
   */
  private Token merge(Token a, Token b) {
    return new Token(a.value() + b.value(), b.pos() - 1, NUMBER);
  }
}
