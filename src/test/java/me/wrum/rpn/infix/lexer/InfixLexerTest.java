package me.wrum.rpn.infix.lexer;

import me.wrum.rpn.api.exception.EmptyExpressionException;
import me.wrum.rpn.api.exception.TokenRuleException;
import me.wrum.rpn.api.lexer.Lexer;
import me.wrum.rpn.api.lexer.Token;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static me.wrum.rpn.api.lexer.Type.CLOSE_BRACE;
import static me.wrum.rpn.api.lexer.Type.NUMBER;
import static me.wrum.rpn.api.lexer.Type.OPEN_BRACE;
import static me.wrum.rpn.api.lexer.Type.OPERATOR;

final class InfixLexerTest {
  private final Lexer testable = LexerFactory.initLexer();

  @Test
  void testSimpleCase() {
    var tokens = testable.tokenize("1+2");

    var expectedTokens = List.of(
        new Token("1", 0, NUMBER),
        new Token("+", 1, OPERATOR),
        new Token("2", 2, NUMBER)
    );

    Assertions.assertEquals(expectedTokens, tokens);
  }

  @Test
  void testMultiSymbolNumbersCase() {
    var tokens = testable.tokenize("29+32/16");

    var expectedTokens = List.of(
        new Token("29", 0, NUMBER),
        new Token("+", 2, OPERATOR),
        new Token("32", 3, NUMBER),
        new Token("/", 5, OPERATOR),
        new Token("16", 6, NUMBER)
    );

    Assertions.assertEquals(expectedTokens, tokens);
  }

  @Test
  void testTokenizationWithBraces() {
    var tokens = testable.tokenize("(2*3)+3");

    var expectedTokens = List.of(
        new Token("(", 0, OPEN_BRACE),
        new Token("2", 1, NUMBER),
        new Token("*", 2, OPERATOR),
        new Token("3", 3, NUMBER),
        new Token(")", 4, CLOSE_BRACE),
        new Token("+", 5, OPERATOR),
        new Token("3", 6, NUMBER)
    );

    Assertions.assertEquals(expectedTokens, tokens);
  }

  @ParameterizedTest
  @NullSource
  @ValueSource(strings = { "", " ", "  " })
  void testNotAcceptEmptyExpressions(String expr) {
    Assertions.assertThrows(EmptyExpressionException.class,
        () -> testable.tokenize(expr)
    );
  }

  @Test
  void testIgnoreWhiteSpaces() {
    var tokens = testable.tokenize("2 + 2");

    var expected = List.of(
        new Token("2", 0, NUMBER),
        new Token("+", 2, OPERATOR),
        new Token("2", 4, NUMBER)
    );

    Assertions.assertEquals(expected, tokens);
  }

  @ParameterizedTest
  @ValueSource(strings = { "9 ; 2", "9abc", "9}9", "12_32", "12 : 32" })
  void mustThrowExceptionWhenIllegalCharacters(String expr) {
    Assertions.assertThrows(TokenRuleException.class,
        () -> testable.tokenize(expr)
    );
  }

  @Test
  void testPlusTokensMerging() {
    var expected = List.of(
        new Token("2", 0, NUMBER),
        new Token("+", 2, OPERATOR),
        new Token("+2", 4, NUMBER)
    );

    Assertions.assertEquals(expected,
        testable.tokenize("2 ++ 2"));
  }

  @Test
  void testMinusTokensMerging() {
    var expected = List.of(
        new Token("2", 0, NUMBER),
        new Token("+", 1, OPERATOR),
        new Token("-2", 3, NUMBER)
    );

    Assertions.assertEquals(expected,
        testable.tokenize("2+ -2"));
  }

  @Test
  void testComplexExpressionMerging() {
    var expected = List.of(
        new Token("-", 0, OPERATOR),
        new Token("(", 2, OPEN_BRACE),
        new Token("8", 3, NUMBER),
        new Token("/", 5, OPERATOR),
        new Token("2", 7, NUMBER),
        new Token(")", 8, CLOSE_BRACE),
        new Token("*", 10, OPERATOR),
        new Token("16", 12, NUMBER),
        new Token("-", 15, OPERATOR),
        new Token("-3", 17, NUMBER),
        new Token("+", 20, OPERATOR),
        new Token("3", 22, NUMBER),
        new Token("+", 24, OPERATOR),
        new Token("+2", 26, NUMBER)
    );

    Assertions.assertEquals(expected,
        testable.tokenize("- (8 / 2) * 16 - -3 + 3 + +2"));
  }
}
