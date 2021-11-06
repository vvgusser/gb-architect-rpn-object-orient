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

      boolean isNotLastToken = i < N - 1;
      boolean isCurrOperator = curr.is(OPERATOR);
      boolean isFirstToken = i == 0;

      // If is not last token and next token is number and prev token is operator
      // (if current not first) then merge next and current token and increment
      // pointer
      if (isNotLastToken && isCurrOperator
          && tokens.get(i + 1).is(NUMBER)
          && (isFirstToken || tokens.get(i - 1).is(OPERATOR))) {

        var next = tokens.get(i + 1);

        processed.add(
            new Token(
                curr.value() + next.value(),
                next.pos() - 1,
                NUMBER
            )
        );

        i++;
      } else {
        processed.add(curr);
      }

      i++;
    }

    return processed;
  }
}
