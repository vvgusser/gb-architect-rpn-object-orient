package me.wrum.rpn.infix.evaluator;

import me.wrum.rpn.api.evaluator.EvaluationResult;
import me.wrum.rpn.api.evaluator.Evaluator;
import me.wrum.rpn.api.lexer.Lexer;
import me.wrum.rpn.api.lexer.Token;
import me.wrum.rpn.infix.operator.support.OperatorsSupport;
import me.wrum.rpn.infix.utils.Tokens;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static me.wrum.rpn.api.lexer.Type.OPERATOR;

/**
 * Evaluator implementation based on calculating reverse polish notation.
 * The shunting yard algorithm is used to convert the infix expression to
 * reverse polish notation.
 *
 * @author Vyacheslav Gusser proweber1@mail.ru
 */
public final class RpnEvaluator implements Evaluator {
  private final Lexer lexer;
  private final OperatorsSupport operators;

  public RpnEvaluator(Lexer lexer, OperatorsSupport operators) {
    Objects.requireNonNull(lexer, "Lexer must not be null");
    Objects.requireNonNull(operators, "Operators support must not be null");

    this.lexer = lexer;
    this.operators = operators;
  }

  @Override
  public EvaluationResult evaluate(String expr) {
    var rpnTokens = lexer.tokenize(expr);

    return new EvaluationResult(
        Tokens.toString(rpnTokens),
        calculateRpn(rpnTokens)
    );
  }

  /**
   * Classic implementation of evaluating an expression in
   * reverse Polish notation using a stack
   *
   * @param tokens tokens in rpn order
   *
   * @return evaluation result
   */
  private double calculateRpn(List<Token> tokens) {
    var stack = new LinkedList<Double>();

    for (var token : tokens) {
      var value = token.value();

      if (!token.is(OPERATOR)) {
        stack.push(Double.parseDouble(value));

        continue;
      }

      var op1 = stack.pop();

      var operands = stack.isEmpty()
          ? new double[]{ op1 }
          : new double[]{ stack.pop(), op1 };

      stack.push(
          operators.getOperator(value)
              .evaluate(operands)
      );
    }

    return stack.getFirst();
  }
}
