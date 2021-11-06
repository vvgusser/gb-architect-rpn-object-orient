package me.wrum.rpn.infix.rules;

import me.wrum.rpn.api.exception.TokenRuleException;
import me.wrum.rpn.api.lexer.Token;
import me.wrum.rpn.api.lexer.TokensRule;
import me.wrum.rpn.infix.operator.support.OperatorsSupport;

import java.util.List;

import static me.wrum.rpn.api.lexer.Type.OPERATOR;

/**
 * The rule checks that if another operator comes after the operator,
 * it must be unary, and if there is another operator after the unary
 * operator, we throw an error (three or more operators in a row)
 *
 * @author Vyacheslav Gusser proweber1@mail.ru
 */
public final class OperatorPositionRule implements TokensRule {
  private final OperatorsSupport operators;

  public OperatorPositionRule(OperatorsSupport operators) {
    this.operators = operators;
  }

  @Override
  public void assertTokens(String expr, List<Token> tokens) {
    for (int i = 1; i < tokens.size() - 1; i++) {
      var token = tokens.get(i);
      var prev = tokens.get(i - 1);
      var next = tokens.get(i + 1);

      if (token.is(OPERATOR) && prev.is(OPERATOR)) {
        if (!operators.isUnaryOperator(token.value())) {
          throw new TokenRuleException(expr, "After '" + prev.value() + "' can follow only unary operator, open brace or number", i - 1);
        }
        if (next.is(OPERATOR)) {
          throw new TokenRuleException(expr, "Three or more consecutive statements", i);
        }
      } // end check
    }
  }
}
