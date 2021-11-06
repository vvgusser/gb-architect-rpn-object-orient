package me.wrum.rpn.api.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

final class TokenRuleExceptionTest {
  @Test
  void testMessageFormattingWithPosition() {
    var ex = new TokenRuleException("9 + 2", "test message", 2);

    var expected = """
        9 + 2
          ^
        error: test message""";

    Assertions.assertEquals(expected, ex.getMessage());
  }

  @Test
  void testMessageFormattingWithoutPosition() {
    var ex = new TokenRuleException("2 + 2", "test error");

    Assertions.assertEquals("error: test error", ex.getMessage());
  }
}
