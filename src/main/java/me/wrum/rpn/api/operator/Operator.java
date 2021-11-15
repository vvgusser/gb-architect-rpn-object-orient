package me.wrum.rpn.api.operator;

import java.util.Collection;

/**
 * Operator that can perform calculations on operands
 *
 * @author Vyacheslav Gusser proweber1@mail.ru
 */
public interface Operator {

  /**
   * Operator notation in string expressions. The char type
   * is used specifically so that operators are always single-character,
   * for example: + ^ / and so on.
   *
   * @return operator marker
   */
  char symbol();

  /**
   * Unary flag, unary operators can work with one operand
   *
   * @return {@code true} when operand is unary, otherwise {@code false}
   */
  default boolean unary() {
    return false;
  }

  /**
   * Operator precedence, must always return a positive number
   * between 0 and n
   *
   * @return positive precedence value
   */
  int precedence();

  /**
   * The method checks that the current operator has precedence over
   * the one passed to the method.
   * <p>
   * The default implementation just compares priorities.
   *
   * @param op another operator
   *
   * @return {@code true} when current operator has higher precedence
   * then other, otherwise {@code false}
   */
  default boolean higherThan(Operator op) {
    return precedence() >= op.precedence();
  }

  /**
   * Computation of the result by an operator, an array is accepted in order
   * to support many operand operators.
   * <p>
   * The method can throw unchecked exceptions that can occur during operator evaluation
   *
   * @param operands an array of operator operands, never {@code null}
   *
   * @return Operator calculation result
   */
  double evaluate(Collection<Double> operands);
}
