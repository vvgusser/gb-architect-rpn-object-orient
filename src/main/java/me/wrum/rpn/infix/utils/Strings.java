package me.wrum.rpn.infix.utils;

/**
 * Utility class for strings
 *
 * @author Vyacheslav Gusser proweber1@mail.ru
 */
public final class Strings {
  private Strings() {
    throw new UnsupportedOperationException();
  }

  /**
   * Checks that the string is empty. Empty is considered
   * equal to null or without characters or consisting only
   * of spaces
   *
   * @param str string for check
   *
   * @return {@code true} when string is blank otherwise {@code false}
   */
  public static boolean isBlank(String str) {
    return null == str || str.isBlank();
  }
}
