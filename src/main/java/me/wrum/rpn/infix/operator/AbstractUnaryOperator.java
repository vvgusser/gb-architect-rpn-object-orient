package me.wrum.rpn.infix.operator;

import me.wrum.rpn.api.operator.Operator;

import java.util.Collection;

/**
 * An abstract type of unary operators. Implements a basic computation
 * that invokes a unary or binary interface depending on the number
 * of operands.
 *
 * @author Vyacheslav Gusser proweber1@mail.ru
 */
abstract class AbstractUnaryOperator implements Operator {
  private static void assertOperandsArr(Collection<Double> arr) {
    if (arr.size() > 2 || arr.size() < 1) {
      throw new IllegalArgumentException("operands count must be between 1 and 2");
    }
  }

  @Override
  public final boolean unary() {
    return true;
  }

  @Override
  public final double evaluate(Collection<Double> operands) {
    assertOperandsArr(operands);

    var iter = operands.iterator();

    return operands.size() == 2
        ? evaluate(iter.next(), iter.next())
        : evaluate(iter.next());
  }

  /**
   * Evaluation over two operands
   *
   * @param a first operand
   * @param b second operand
   *
   * @return computation result
   */
  protected abstract double evaluate(double a, double b);

  /**
   * Unary interface, works with only one operand
   *
   * @param a operand for process
   *
   * @return computation result
   */
  protected abstract double evaluate(double a);
}
