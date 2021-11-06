package me.wrum.rpn.infix.evaluator;

import me.wrum.rpn.api.evaluator.EvaluationResult;
import me.wrum.rpn.api.evaluator.Evaluator;
import me.wrum.rpn.api.lexer.Lexer;
import me.wrum.rpn.api.lexer.Token;
import me.wrum.rpn.infix.operator.OperatorsSupport;
import me.wrum.rpn.infix.utils.Tokens;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static me.wrum.rpn.api.lexer.Type.OPEN_BRACE;
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
    var tokens = lexer.tokenize(expr);

    var rpnTokens = shuntingYard(tokens);

    return new EvaluationResult(
        Tokens.toString(rpnTokens),
        calculateRpn(rpnTokens)
    );
  }

  /**
   * Implementation of the shunting yard algorithm. You can read more
   * <a href="https://en.wikipedia.org/wiki/Shunting-yard_algorithm">here</a>.
   * <p>
   * After processing the original tokens, this method rearranges them so
   * that these tokens can be calculated using the reverse polish notation
   * solver algorithm.
   *
   * @param tokens list of infix tokens
   *
   * @return list of tokens in rpn order
   */
  private List<Token> shuntingYard(List<Token> tokens) {
    var stack = new LinkedList<Token>();
    var result = new ArrayList<Token>();

    for (var token : tokens) {
      switch (token.type()) {
        // Push open brace on stack
        case OPEN_BRACE -> stack.push(token);
        // Poll all tokens from stack until look open brace
        case CLOSE_BRACE -> {
          while (!stack.isEmpty()) {
            var el = stack.pop();

            if (el.is(OPEN_BRACE)) break;

            result.add(el);
          }
        }
        // If current token is operator, look stack and pop
        // operators with precedence higher than of current
        // token, and push to stack current token
        case OPERATOR -> {
          var value = token.value();
          var op = operators.getOperator(value);

          while (!stack.isEmpty()) {
            var tok = stack.getFirst();
            var tokV = tok.value();

            if (!tok.is(OPERATOR) || operators.getOperator(tokV).precedence() < op.precedence()) break;

            result.add(stack.pop());
          }

          stack.push(token);
        }
        // Numbers push directly to stack
        default -> result.add(token);
      }
    }

    // When in stack remains operators, push it all
    // to end of result tokens list
    while (!stack.isEmpty()) {
      result.add(stack.pollFirst());
    }

    return result;
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
