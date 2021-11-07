package me.wrum.rpn.infix.utils;

import me.wrum.rpn.api.lexer.Token;
import me.wrum.rpn.api.lexer.Type;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

final class TokensTest {
  @ParameterizedTest
  @MethodSource("input")
  void testToString(List<Token> tokens, String expected) {
    Assertions.assertEquals(expected, Tokens.toString(tokens));
  }

  @SuppressWarnings("unused")
  static Stream<Arguments> input() {
    return Stream.of(
        Arguments.of(
            List.of(
                new Token("1", 0, Type.NUMBER),
                new Token("+", 1, Type.OPERATOR),
                new Token("1", 2, Type.NUMBER)
            ),
            "1 + 1"
        ),
        Arguments.of(
            List.of(
                new Token("(", 0, Type.OPEN_BRACE),
                new Token("2", 1, Type.NUMBER),
                new Token("*", 2, Type.OPERATOR),
                new Token("3", 3, Type.NUMBER),
                new Token(")", 4, Type.CLOSE_BRACE),
                new Token("+", 5, Type.OPERATOR),
                new Token("2", 6, Type.NUMBER)
            ),
            "( 2 * 3 ) + 2"
        ),
        Arguments.of(
            List.of(
                new Token("9", 0, Type.NUMBER),
                new Token("9", 1, Type.NUMBER),
                new Token("+", 2, Type.OPERATOR)
            ),
            "9 9 +"
        )
    );
  }
}
