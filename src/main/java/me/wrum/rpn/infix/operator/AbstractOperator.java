package me.wrum.rpn.infix.operator;

import me.wrum.rpn.api.operator.Operator;

/**
 * An abstract operator type that implements a basic computation
 * function and delegates it to a simpler interface.
 * <p>
 * This type reflects not uranium operators, those operators where
 * there will necessarily be two operands
 *
 * @author Vyacheslav Gusser proweber1@mail.ru
 */
abstract class AbstractOperator implements Operator {
  @Override
  public final boolean unary() {
    return false;
  }

  @Override
  public final double evaluate(double[] operands) {
    if (operands.length != 2) {
      throw new IllegalArgumentException("Required two operands for operation '" + symbol() + "'");
    }
    return evaluate(operands[0], operands[1]);
  }

  /**
   * Computation of a binary operator. During the calculation, it
   * is allowed to throw exceptions
   *
   * @param a first operand
   * @param b second operand
   *
   * @return computation result
   */
  abstract double evaluate(double a, double b);
}
