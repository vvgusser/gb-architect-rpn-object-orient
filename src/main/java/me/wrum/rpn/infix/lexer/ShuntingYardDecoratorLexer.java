package me.wrum.rpn.infix.lexer;

import me.wrum.rpn.api.lexer.Lexer;
import me.wrum.rpn.api.lexer.Token;
import me.wrum.rpn.api.operator.Operator;
import me.wrum.rpn.infix.operator.support.OperatorsSupport;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static me.wrum.rpn.api.lexer.Type.OPEN_BRACE;
import static me.wrum.rpn.api.lexer.Type.OPERATOR;

/**
 * A lexer decorator that converts infix tokens to postfix tokens using an algorithm
 * <a href="https://en.wikipedia.org/wiki/Shunting-yard_algorithm">shunting yard</a>
 *
 * @author Vyacheslav Gusser proweber1@mail.ru
 */
public final class ShuntingYardDecoratorLexer implements Lexer {
  private final Lexer wrapped;
  private final OperatorsSupport operators;

  public ShuntingYardDecoratorLexer(Lexer wrapped, OperatorsSupport operators) {
    Objects.requireNonNull(wrapped, "Wrapped lexer must not be null");
    Objects.requireNonNull(operators, "Operators support must not be null");

    this.wrapped = wrapped;
    this.operators = operators;
  }

  @Override
  public List<Token> tokenize(String expression) {
    var tokens = wrapped.tokenize(expression);

    var stack = new LinkedList<Token>();
    var result = new ArrayList<Token>();

    for (var token : tokens) {
      switch (token.type()) {
        // Push open brace on stack
        case OPEN_BRACE -> stack.push(token);
        // Poll all tokens from stack until look open brace
        case CLOSE_BRACE -> {
          while (!stack.isEmpty() && !stack.getFirst().is(OPEN_BRACE)) {
            result.add(stack.pop());
          }

          stack.pop();
        }
        // If current token is operator, look stack and pop
        // operators with precedence higher than of current
        // token, and push to stack current token
        case OPERATOR -> {
          var value = token.value();
          var op = operators.getOperator(value);

          while (!stack.isEmpty() && isHigherPrecedence(stack.getFirst(), op)) {
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
      result.add(stack.pop());
    }

    return result;
  }

  /**
   * Check that given token is operator and operator precedence higher
   * than operator from second argument
   *
   * @param a  token for check
   * @param op operator for compare
   *
   * @return {@code true} when given token is operator with higher
   * precedence than second operator otherwise {@code false}
   */
  private boolean isHigherPrecedence(Token a, Operator op) {
    return a.is(OPERATOR) && operators.getOperator(a.value()).higherThan(op);
  }
}
