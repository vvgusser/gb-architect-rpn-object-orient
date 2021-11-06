package me.wrum.rpn.infix.operator;

/**
 * Unary - binary difference operator. In the case of two operands,
 * the operator subtracts the second from the first; in the case of
 * unary computation, it inverts the sign of the number.
 * <p>
 * For example 5 becomes -5, and -5 becomes 5
 *
 * @author Vyachelsav Gusser proweber1@mail.ru
 */
public final class Minus extends AbstractUnaryOperator {
  @Override
  public char symbol() {
    return '-';
  }

  @Override
  protected double evaluate(double a, double b) {
    return a - b;
  }

  @Override
  protected double evaluate(double a) {
    return -a;
  }

  @Override
  public int precedence() {
    return 2;
  }
}
