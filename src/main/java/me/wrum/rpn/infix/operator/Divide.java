package me.wrum.rpn.infix.operator;

import me.wrum.rpn.infix.operator.exception.DivisionByZeroException;

/**
 * Division operator for two operands. It is NOT a unary operator,
 * it can take at least two operands.
 *
 * @author Vyacheslav Gusser proweber1@mail.ru
 */
public final class Divide extends AbstractOperator {
  @Override
  public char symbol() {
    return '/';
  }

  /**
   * Computation of a division operation, with division by zero
   * rules. When divided by zero, this method throws an exception.
   *
   * @param a first operand
   * @param b second operand
   *
   * @return computation result
   *
   * @throws DivisionByZeroException when second operand is zero
   */
  @Override
  double evaluate(double a, double b) {
    if (b == 0) {
      throw new DivisionByZeroException();
    }
    return a / b;
  }

  @Override
  public int precedence() {
    return 3;
  }
}
