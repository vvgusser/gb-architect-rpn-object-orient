package me.wrum.rpn.infix.operator;

import java.util.List;

/**
 * Factory for creating objects of {@link OperatorsSupport}
 *
 * @author Vyacheslav Gusser proweber1@mail.ru
 */
public final class OperatorsSupportFactory {
  private OperatorsSupportFactory() {
    throw new UnsupportedOperationException();
  }

  /**
   * Creates object {@link OperatorsSupport} with all operators available in the system
   *
   * @return instance of {@link OperatorsSupport}
   */
  public static OperatorsSupport instantiate() {
    var operators = List.of(
        new Divide(),
        new Multiply(),
        new Minus(),
        new Plus()
    );

    return OperatorsSupport.create(operators);
  }
}
