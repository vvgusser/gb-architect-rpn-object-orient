package me.wrum.rpn.infix.rules;

import me.wrum.rpn.api.exception.TokenRuleException;
import me.wrum.rpn.api.lexer.Token;
import me.wrum.rpn.api.lexer.TokensRule;
import me.wrum.rpn.infix.operator.support.OperatorsSupport;

import java.util.List;
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
  public void assertTokens(String expr, List<Token> tokens) {
    new Helper(tokens, expr).validate();
  }

  private boolean isNumberOrOpenBrace(Token token) {
    return token.is(NUMBER, OPEN_BRACE);
  }

  /**
   * Helper that helps to extract the general state of the validator
   * into the state of the class. To make validation methods cleaner.
   *
   * @author Vyacheslav Gusser proweber1@mail.ru
   */
  private final class Helper {
    private final List<Token> tokens;

    private final int pos;
    private final Token token;

    private final String expr;

    Helper(List<Token> tokens, String expr) {
      this.tokens = tokens;
      this.expr = expr;

      var first = tokens.get(0);

      this.token = first;
      this.pos = first.pos();
    }

    /**
     * The method executes the main validation rule, which is
     * written in the documentation of the outer class.
     */
    void validate() {
      if (isNumberOrOpenBrace(token)) {
        return;
      }

      if (token.is(OPERATOR)) {
        validateOperator();
      } else {
        throw new TokenRuleException(expr, "Expression cannot start with a character '" + token + "'", pos);
      }
    }

    /**
     * This method runs when the first token of the expression is an operator.
     * Here it is checked that this is a valid operator. And admissible is
     * unary. It is also checked that after the unary operator there is an
     * open parenthesis or a number.
     */
    void validateOperator() {
      if (!opSupp.isUnaryOperator(token.value())) {
        throw new TokenRuleException(expr, "An expression can only start with a unary operator", pos);
      }

      if (tokens.size() < 2) {
        throw new TokenRuleException(expr, "An expression cannot only consist of a unary operator");
      }

      var next = tokens.get(1);

      if (!isNumberOrOpenBrace(next)) {
        throw new TokenRuleException(expr, "There can only be an open parenthesis or a number after the unary operator", pos);
      }
    }
  } // end helper class
}
