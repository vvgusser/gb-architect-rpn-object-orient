package me.wrum.rpn.infix.rules;

import me.wrum.rpn.api.lexer.TokensRule;
import me.wrum.rpn.api.lexer.ValidationContext;

import static me.wrum.rpn.api.lexer.Type.NUMBER;

/**
 * This rule checks that the tokens do not contain two numbers
 * one after the other. This means that the original expression
 * contains numbers separated by spaces.
 *
 * @author Vyacheslav Gusser proweber1@mail.ru
 */
public class ConsecutiveNumbersRule implements TokensRule {
  @Override
  public void assertTokens(ValidationContext ctx) {
    var tokens = ctx.tokens();
    var N = ctx.numberOfTokens();

    for (int i = 1; i < N; i++) {
      var curr = tokens.get(i);
      var prev = tokens.get(i - 1);

      if (curr.is(NUMBER) && prev.is(NUMBER)) {
        ctx.invalid("Numbers cannot be separated by spaces", curr.pos());
      }
    } // end for loop
  }
}
