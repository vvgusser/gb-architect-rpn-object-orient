package me.wrum.rpn;

import me.wrum.rpn.api.exception.ApplicationException;

import java.io.Console;
import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * REPL (read-eval-print-loop) interface for evaluate infix expressions
 *
 * @author Vyachesav Gusser proweber1@mail.ru
 */
final class Repl {
  @FunctionalInterface
  interface Printer {
    void print(Object o);
  }

  void onNextExpression(BiConsumer<String, Printer> exprConsumer) {
    Objects.requireNonNull(exprConsumer, "expression consumer must not be null");

    var console = getConsole();

    printHi();

    //noinspection InfiniteLoopStatement
    for (;;) {
      var expr = console.readLine(">>> ");

      try {
        exprConsumer.accept(
            expr,
            o -> console.printf("> %s%n", o)
        );
      } catch (ApplicationException e) {
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
