package me.wrum.rpn.infix.operator;

/**
 * A unary-binary operation for adding two numbers. In the case
 * of a calculation with two operands, the second is added to the
 * first. In case of unary evaluation, the first operand is
 * simply returned.
 *
 * @author Vyacheslav Gusser proweber1@mail.ru
 */
public final class Plus extends AbstractUnaryOperator {
  @Override
  public char symbol() {
    return '+';
  }

  @Override
  protected double evaluate(double a, double b) {
    return a + b;
  }

  @Override
  protected double evaluate(double a) {
    return +a;
  }

  @Override
  public int precedence() {
    return 2;
  }
}
