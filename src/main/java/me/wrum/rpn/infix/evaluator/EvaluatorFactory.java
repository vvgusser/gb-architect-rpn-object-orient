package me.wrum.rpn.infix.evaluator;

import me.wrum.rpn.api.evaluator.Evaluator;
import me.wrum.rpn.infix.lexer.LexerFactory;
import me.wrum.rpn.infix.operator.OperatorsSupportFactory;

public final class EvaluatorFactory {
  private EvaluatorFactory() {
    throw new UnsupportedOperationException();
  }

  public static Evaluator create() {
    return new RpnEvaluator(
        LexerFactory.initLexer(),
        OperatorsSupportFactory.instantiate()
    );
  }
}
