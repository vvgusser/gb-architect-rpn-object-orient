package me.wrum.rpn.infix.operator;

/**
 * A common binary operation for multiplying two operands.
 *
 * @author Vyacheslav Gusser proweber1@mail.ru
 */
public final class Multiply extends AbstractOperator {
  @Override
  public char symbol() {
    return '*';
  }

  @Override
  double evaluate(double a, double b) {
    return a * b;
  }

  @Override
  public int precedence() {
    return 3;
  }
}
