package me.wrum.rpn.infix.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

final class StringsTest {
  @ParameterizedTest
  @NullSource
  @ValueSource(strings = {"", " ", "  "})
  void testBlankStrings(String str) {
    Assertions.assertTrue(Strings.isBlank(str));
  }

  @ParameterizedTest
  @ValueSource(strings = {" Hi", "hi", " hi ", "h h h ", "hello"})
  void testNotBlankStrings(String str) {
    Assertions.assertFalse(Strings.isBlank(str));
  }
}
