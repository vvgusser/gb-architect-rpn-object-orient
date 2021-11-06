package me.wrum.rpn.infix.evaluator;

import me.wrum.rpn.api.evaluator.Evaluator;
import me.wrum.rpn.api.exception.ApplicationException;
import me.wrum.rpn.infix.lexer.LexerFactory;
import me.wrum.rpn.infix.operator.OperatorsSupportFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

public final class RpnEvaluatorTest {
  private final Evaluator testable = new RpnEvaluator(
      LexerFactory.initLexer(),
      OperatorsSupportFactory.instantiate()
  );

  @ParameterizedTest
  @MethodSource("legalExpressions")
  void testLegalCases(String infix, String rpn, double expected) {
    var result = testable.evaluate(infix);

    Assertions.assertEquals(result.result(), expected);
    Assertions.assertEquals(result.expr(), rpn);
  }

  @SuppressWarnings("unused")
  public static Stream<Arguments> legalExpressions() {
    return Stream.of(
        Arguments.of("(1 + 2) * 4 + 3", "1 2 + 4 * 3 +", 15),
        Arguments.of("9 + 8", "9 8 +", 17),
        Arguments.of("-(8 / 2) * 4", "8 2 / 4 * -", -16),
        Arguments.of("98 / 32 + 16 * (78 - 78)", "98 32 / 16 78 78 - * +", 3.0625),
        Arguments.of("0 * (0)", "0 0 *", 0),
        Arguments.of("32 / 89 * 8", "32 89 / 8 *", 2.8764044943820224),
        Arguments.of("0.5 + 1.5", "0.5 1.5 +", 2),
        Arguments.of("9 * (8 / (4 / 2))", "9 8 4 2 / / *", 36),
        Arguments.of("256 + 322 / 4 * (17 - 8)", "256 322 4 / 17 8 - * +", 980.5),
        Arguments.of("-1", "-1", -1),
        Arguments.of("+5", "+5", 5),
        Arguments.of("-6 + 5", "-6 5 +", -1),
        Arguments.of("0*(0+(98 - 97 - 1))", "0 0 98 97 - 1 - + *", 0),
        Arguments.of("-9 + -9", "-9 -9 +", -18)
    );
  }

  @ParameterizedTest
  @NullSource
  @ValueSource(strings = {
      "--0",
      "--",
      "*98",
      "/32",
      "0*()",
      "0*(9 - 2 -)",
      "125. + 125.",
      "398 * 0 -",
      "122 / 0",
      "122 / (98 - 98)",
      "916 000 + 843",
      "",
      " ",
      "98 */ 17",
      "100 *+- 9",
      "113 +++ 9",
      "112 -+- 9",
      "122 ++ -9",
      "-17+9-  ",
      "6 ; 6",
  })
  void testIllegalCases(String expr) {
    Assertions.assertThrows(ApplicationException.class,
        () -> testable.evaluate(expr)
    );
  }
}
