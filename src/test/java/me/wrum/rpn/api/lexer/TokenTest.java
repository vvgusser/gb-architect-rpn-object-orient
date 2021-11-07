package me.wrum.rpn.api.lexer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

final class TokenTest {
  private final Token token = new Token("1", 0, Type.NUMBER);

  @Test
  void testIsMethodWithCorrectType() {
    Assertions.assertTrue(token.is(Type.NUMBER));
  }

  @Test
  void testReturnFalseWhenTypeMismatch() {
    Assertions.assertFalse(token.is(Type.OPERATOR));
  }

  @Test
  void testReturnTrueWhenAnyTypeMatches() {
    Assertions.assertTrue(token.is(Type.NUMBER, Type.OPERATOR));
  }

  @Test
  void testReturnFalseWhenAllNonMatch() {
    Assertions.assertFalse(token.is(Type.OPEN_BRACE, Type.OPERATOR));
  }

  @Test
  void testThrowIllegalArgumentWhenTypesNotPass() {
    //noinspection ResultOfMethodCallIgnored
    Assertions.assertThrows(IllegalArgumentException.class, token::is);
  }
}
