package me.wrum.rpn.api.evaluator;

/**
 * An interface for evaluators that can evaluate various
 * kinds of expressions. Infix, postfix, prefix, and so on.
 *
 * @author Vyacheslav Gusser proweber1@mail.ru
 */
@FunctionalInterface
public interface Evaluator {
  EvaluationResult evaluate(String expr);
}
