package me.wrum.rpn;

import me.wrum.rpn.infix.evaluator.EvaluatorFactory;

/**
 * Application entrypoint
 *
 * @author Vyacheslav Gusser proweber1@mail.ru
 */
final class Main {
  public static void main(String[] args) {
    new Repl(EvaluatorFactory.create()).run();
  }
}
