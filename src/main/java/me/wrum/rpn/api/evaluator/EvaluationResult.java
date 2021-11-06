package me.wrum.rpn.api.evaluator;

/**
 * The result of evaluating an expression
 *
 * @author Vyacheslav Gusser proweber1@mail.ru
 * @see Evaluator#evaluate(String)
 */
public record EvaluationResult(String expr, double result) {
}
