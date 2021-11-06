package me.wrum.rpn.infix.evaluator;

import me.wrum.rpn.api.evaluator.Evaluator;
import me.wrum.rpn.infix.lexer.LexerFactory;
import me.wrum.rpn.infix.operator.support.OperatorsSupportFactory;

/**
 * Factory for creating instances of evaluators
 *
 * @author Vyacheslav Gusser proweber1@mail.ru
 */
public final class EvaluatorFactory {
  private EvaluatorFactory() {
    throw new UnsupportedOperationException();
  }

  /**
   * Creates a fully configured evaluator for evaluating infix expressions
   *
   * @return evaluator instance
   */
  public static Evaluator create() {
    return new RpnEvaluator(
        LexerFactory.initLexer(),
        OperatorsSupportFactory.instantiate()
    );
  }
}
