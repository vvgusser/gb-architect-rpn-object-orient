package me.wrum.rpn;

import me.wrum.rpn.api.evaluator.Evaluator;
import me.wrum.rpn.api.exception.ApplicationException;
import me.wrum.rpn.infix.evaluator.EvaluatorFactory;

import java.text.DecimalFormat;
import java.util.Objects;

/**
 * The entry point to the REPL is an application for evaluating infix expressions
 *
 * @author Vyacheslav Gusser proweber1@mail.ru
 */
final class Main {
  private final Evaluator evaluator;
  private final DecimalFormat formatter;

  /**
   * Creates an application with a mandatory evaluator for evaluating expressions
   *
   * @param evaluator required for evaluate expressions from command line
   */
  public Main(Evaluator evaluator) {
    Objects.requireNonNull(evaluator, "Evaluator must not be null");

    this.evaluator = evaluator;
    this.formatter = new DecimalFormat("#.##");
  }

  void run() {
    var console = System.console();

    if (null == console) {
      throw new RuntimeException("system console not available");
    }

    printHi();

    //noinspection InfiniteLoopStatement
    for (; ; ) {
      var expr = console.readLine(">>> ");

      try {
        var result = evaluator.evaluate(expr);

        console.printf("> %s%n", result.expr());
        console.printf("> %s%n",
            formatter.format(result.result())
        );
      }
      catch (ApplicationException e) {
        console.printf("%s%n", e.getMessage());
      } // end catch block
    }
  }

  private void printHi() {
    System.out.println("""
        Hi!
        Just write infix here and get RPN to answer with evaluated value.
        For example you can write (1 + 2) * 4 + 3 and get back RPN like this
        1 2 + 4 * 3 + and evaluated answer is 15.
        Press Ctrl + C for exit
        Just fun"""
    );
  }

  public static void main(String[] args) {
    new Main(EvaluatorFactory.create()).run();
  }
}
