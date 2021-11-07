package me.wrum.rpn.infix.rules;

import me.wrum.rpn.api.lexer.Token;
import me.wrum.rpn.api.lexer.TokensRule;
import me.wrum.rpn.api.lexer.ValidationContext;
import me.wrum.rpn.infix.operator.support.OperatorsSupport;

import java.util.Objects;

import static me.wrum.rpn.api.lexer.Type.NUMBER;
import static me.wrum.rpn.api.lexer.Type.OPEN_BRACE;
import static me.wrum.rpn.api.lexer.Type.OPERATOR;

/**
 * A rule that checks that the first character of an expression
 * is valid. Valid: numbers, unary operators, open parenthesis.
 *
 * @author Vyacheslav Gusser proweber1@mail.ru
 */
public final class LegalFirstTokenRule implements TokensRule {
  private final OperatorsSupport opSupp;

  public LegalFirstTokenRule(OperatorsSupport opSupp) {
    Objects.requireNonNull(opSupp, "operators support must not be null");

    this.opSupp = opSupp;
  }

  @Override
  public void assertTokens(ValidationContext ctx) {
    var tokens = ctx.tokens();
    var first = tokens.get(0);

    if (isNumberOrOpenBrace(first)) {
      return;
    }

    if (first.is(OPERATOR)) {
      validateOperator(first, ctx);
    } else {
      ctx.invalid("Expression cannot start with a character '" + first.value() + "'", first.pos());
    }
  }

  private void validateOperator(Token tok, ValidationContext ctx) {
    var pos = tok.pos();

    if (!opSupp.isUnaryOperator(tok.value())) {
      ctx.invalid("An expression can only start with a unary operator");
    }

    if (ctx.numberOfTokens() < 2) {
      ctx.invalid("An expression cannot only consist of a unary operator");
    }

    var next = ctx.tokens().get(1);

    if (!isNumberOrOpenBrace(next)) {
      ctx.invalid("There can only be an open parenthesis or a number after the unary operator", pos);
    }
  }

  private boolean isNumberOrOpenBrace(Token token) {
    return token.is(NUMBER, OPEN_BRACE);
  }
}
