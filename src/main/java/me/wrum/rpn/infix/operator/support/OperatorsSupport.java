package me.wrum.rpn.infix.operator.support;

import me.wrum.rpn.api.operator.Operator;
import me.wrum.rpn.infix.utils.Strings;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

/**
 * A component that plays the role of a registry of operations that are
 * supported by the application. Provides a convenient interface for
 * checking the existence of an operator, getting it, and other useful methods.
 *
 * @author Vyacheslav Gusser proweber1@mail.ru
 */
public final class OperatorsSupport {
  private final Map<Character, Operator> operators;

  private OperatorsSupport(Map<Character, Operator> operators) {
    this.operators = operators;
  }

  /**
   * Creates a new instance of the class from the collection of
   * operators that it should host.
   *
   * @param operators supported operators
   *
   * @return instance of {@link OperatorsSupport}
   *
   * @throws IllegalArgumentException when operators collection is empty or null
   */
  public static OperatorsSupport create(Collection<Operator> operators) {
    if (null == operators || operators.isEmpty()) {
      throw new IllegalArgumentException("Operators must not be null and not be empty");
    }

    var map = operators.stream()
        .collect(
            toMap(
                Operator::symbol,
                Function.identity()
            )
        );

    return new OperatorsSupport(map);
  }

  /**
   * Checks that the application has a handler for the operation passed in arguments
   *
   * @param sym operator symbol
   *
   * @return {@code true} when operator exists and {@code false} otherwise
   */
  public boolean hasOperatorForSymbol(char sym) {
    return operators.containsKey(sym);
  }

  /**
   * Returns the operator by code from the method arguments. May throw an exception
   * if the operator is not found, to prevent the exception from being thrown or to
   * avoid handling it, call first {@link #hasOperatorForSymbol(char)}
   *
   * @param code operator symbol
   *
   * @return instance of operator object
   */
  public Operator getOperator(String code) {
    var operator = operators.get(
        getOperatorCode(code)
    );

    return Optional.ofNullable(operator)
        .orElseThrow();
  }

  /**
   * Checks that the operator with a character from the given string
   * is unary. If the operator for the passed string is not registered,
   * an exception is thrown. Also, an exception is thrown if the passed
   * string is empty or null, as well as if its length is not equal to
   * one character.
   *
   * @param str operator symbol
   *
   * @return {@code true} when operator is unary {@code false} otherwise
   */
  public boolean isUnaryOperator(String str) {
    return getOperator(str).unary();
  }

  private char getOperatorCode(String str) {
    if (Strings.isBlank(str)) {
      throw new IllegalArgumentException("operator code string must not be blank");
    }
    if (str.length() != 1) {
      throw new IllegalArgumentException("operator code string must have length = 1");
    }
    return str.charAt(0);
  }
}
