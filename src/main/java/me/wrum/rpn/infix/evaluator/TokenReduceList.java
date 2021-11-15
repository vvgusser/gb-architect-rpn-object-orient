package me.wrum.rpn.infix.evaluator;

import me.wrum.rpn.api.lexer.Token;
import me.wrum.rpn.api.lexer.Type;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Simple functional wrapper on tokens collection
 *
 * @param <T> type of tokens
 *
 * @author Vyacheslav Gusser proweber1@mail.ru
 */
final class TokenReduceList<T extends Token> {
  private final List<T> tokens;

  TokenReduceList(List<T> tokens) {
    this.tokens = List.copyOf(tokens);
  }

  /**
   * Reduce nested list by some reducer functions. This reducer call
   * one reducer for number types and another for operator types. And
   * after reducing return evaluation result. This is just for example
   * of functional manner programming.
   *
   * @param nums      reducer for number tokens
   * @param opReducer reducer for operator tokens
   * @param <N>       type of numbers
   *
   * @return reduce result
   */
  <N> N reduce(Function<Token, N> nums, BiFunction<List<N>, Token, N> opReducer) {
    var st = new LinkedList<N>();

    for (var tok : tokens) {
      if (tok.is(Type.NUMBER)) {
        st.push(nums.apply(tok));
      } else {
        var ops = new ArrayList<N>();

        // pop two or lesser operands
        for (var i = 0; i < 2 && !st.isEmpty(); i++) {
          ops.add(st.pop());
        }

        var res = opReducer.apply(ops, tok);

        st.push(res);
      }
    }

    return st.pop();
  }
}
