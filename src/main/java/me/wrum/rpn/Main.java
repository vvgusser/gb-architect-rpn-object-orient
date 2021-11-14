package me.wrum.rpn;

import me.wrum.rpn.infix.evaluator.EvaluatorFactory;

import java.text.DecimalFormat;

/**
 * Application entrypoint
 *
 * @author Vyacheslav Gusser proweber1@mail.ru
 */
final class Main {
  public static void main(String[] args) {
    var decimalFormat = new DecimalFormat("#.##");
    var evaluator = EvaluatorFactory.create();

    new Repl().onNextExpression((expr, out) -> {
      var result = evaluator.evaluate(expr);

      out.print(result.expr());
      out.print(decimalFormat.format(result.result()));
    });
  }
}
