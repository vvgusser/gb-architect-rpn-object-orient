package me.wrum.rpn;

import me.wrum.rpn.api.evaluator.Evaluator;
import me.wrum.rpn.api.exception.ApplicationException;

import java.io.Console;
import java.text.DecimalFormat;
import java.util.Objects;

/**
 * REPL (read-eval-print-loop) interface for evaluate infix expressions
 *
 * @author Vyachesav Gusser proweber1@mail.ru
 */
final class Repl {
  private final Evaluator evaluator;
  private final DecimalFormat formatter;

  /**
   * Primary constructor.
   * <p>
   * Create repl with required evaluator for evaluate expressions
   *
   * @param evaluator required evaluator
   */
  public Repl(Evaluator evaluator) {
    Objects.requireNonNull(evaluator, "Evaluator must not be null");

    this.evaluator = evaluator;
    this.formatter = new DecimalFormat("#.##");
  }

  void run() {
    var console = getConsole();

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
      }
    }
  }

  /**
   * This return system console and throw exception if console isn't available
   *
   * @return console instance
   */
  private Console getConsole() {
    var result = System.console();

    if (null == result) {
      throw new RuntimeException("System console not available");
    }

    return result;
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
}
