package me.wrum.rpn.infix.rules;

import me.wrum.rpn.api.lexer.Token;
import me.wrum.rpn.api.lexer.TokensRule;
import me.wrum.rpn.api.lexer.Type;
import me.wrum.rpn.api.lexer.ValidationContext;

/**
 * The rule checks that the expression contains well-formed numbers.
 * Strips off octal numbers and checks that the fractional numbers
 * are in the correct format.
 *
 * @author Vyacheslav Gusser proweber1@mail.ru
 */
public final class LegalNumbersRule implements TokensRule {
  // Implementation:
  //
  // First, we only select numbers from all tokens. Then
  // we check that the numbers are not octal, if this check
  // passes, then we check that the number matches the
  // regular expression

  /**
   * Regular expression to test for a number. Matches for the following values
   * <pre>
   *   0.5
   *   100
   *   238.25
   * </pre>
   * And cuts off the next
   * <pre>
   *   123.
   *   ...
   * </pre>
   */
  private static final String VALID_NUMBER_REGEX = "[0-9]+(.[0-9]+)?";

  @Override
  public void assertTokens(ValidationContext ctx) {
    var numberTokensStream = ctx.tokens().stream().filter(this::isNumberToken);

    numberTokensStream.forEach(el -> {
      var value = el.value();
      var pos = el.pos();

      if (!value.matches(VALID_NUMBER_REGEX)) {
        ctx.invalid("Invalid number, only integers and fractional numbers are allowed, there must be numbers after the period in the fractional",
            pos);
      }
    });
  }

  private boolean isNumberToken(Token token) {
    return token.is(Type.NUMBER);
  }
}
