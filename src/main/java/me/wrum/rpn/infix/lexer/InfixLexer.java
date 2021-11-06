package me.wrum.rpn.infix.lexer;

import me.wrum.rpn.api.lexer.TokensRule;
import me.wrum.rpn.api.exception.EmptyExpressionException;
import me.wrum.rpn.api.exception.TokenRuleException;
import me.wrum.rpn.api.lexer.Lexer;
import me.wrum.rpn.api.lexer.Token;
import me.wrum.rpn.api.lexer.Type;
import me.wrum.rpn.infix.operator.OperatorsSupport;
import me.wrum.rpn.infix.utils.Strings;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.Character.isDigit;
import static java.lang.Character.isWhitespace;
import static me.wrum.rpn.api.lexer.Type.CLOSE_BRACE;
import static me.wrum.rpn.api.lexer.Type.OPEN_BRACE;
import static me.wrum.rpn.api.lexer.Type.OPERATOR;

/**
 * A simple lexer for infix math expressions.
 *
 * @author Vyacheslav Gusser proweber1@mail.ru
 * @implNote Variables and functions are not supported, but it
 * is easy to extend it to support
 */
public final class InfixLexer implements Lexer {
  private final TokensRule rule;
  private final OperatorsSupport opSupp;

  private final TokensMerger tokensMerger;

  /**
   * Creates a lexer without token validation rules
   *
   * @param opSupp operator support interface
   */
  public InfixLexer(OperatorsSupport opSupp) {
    this(opSupp, null);
  }

  /**
   * Primary constructor.
   * <p>
   * Creates a lexer with the ability to pass a validation
   * rule and a mandatory operator support interface
   *
   * @param opSupp required operation support interface
   * @param rule   optional validation rule
   */
  public InfixLexer(OperatorsSupport opSupp, TokensRule rule) {
    Objects.requireNonNull(opSupp, "operators support must not be null");

    this.opSupp = opSupp;
    this.rule = rule;
    // helper
    this.tokensMerger = new TokensMerger();
  }

  private static void assertExpression(String expr) {
    if (Strings.isBlank(expr)) {
      throw new EmptyExpressionException();
    }
  }

  @Override
  public List<Token> tokenize(String expr) {
    assertExpression(expr);

    var tokens = tokenizeImpl(expr);

    if (rule != null) {
      rule.assertTokens(expr, tokens);
    }

    return tokensMerger.merge(tokens);
  }

  /**
   * Actual implementation of tokenization of infix expression.
   * <p>
   * The classic implementation of the infix expression tokenization
   * algorithm. A buffer is used for numeric tokens and operators, as
   * well as parentheses, are considered separators. If we encounter
   * a separator and the numeric buffer is not empty, we drop it into
   * the token list as a numeric token and clear the buffer, and so on
   * until we have processed the entire expression. If at the end of the
   * iteration over the expression we still have something in the numeric
   * buffer, we also dump it into tokens as a number.
   *
   * @param expr actual infix expression
   *
   * @return list of tokens from expression
   */
  private List<Token> tokenizeImpl(String expr) {
    var tokens = new ArrayList<Token>();
    var numBuf = new StringBuilder();

    for (var i = 0; i < expr.length(); i++) {
      var ch = expr.charAt(i);

      if (isDigit(ch) || ch == '.') {
        numBuf.append(ch);
        continue;
      }

      if (!numBuf.isEmpty()) {
        tokens.add(createNumberTokenAndFlushBuf(numBuf, i));
      }

      if (isWhitespace(ch)) {
        continue;
      }

      var token = retrieveToken(ch, i);

      if (null == token) {
        throw new TokenRuleException(expr, "Illegal character'" + ch + "'", i);
      }

      tokens.add(token);
    }

    if (!numBuf.isEmpty()) {
      tokens.add(createNumberToken(numBuf, expr.length()));
    }

    return tokens;
  }

  /**
   * Creates a token of type NUMBER and clears the numeric buffer
   *
   * @param buf     numbers buffer
   * @param currPos current expression pointer
   *
   * @return instance of number token
   */
  private Token createNumberTokenAndFlushBuf(StringBuilder buf, int currPos) {
    var token = createNumberToken(buf, currPos);
    buf.setLength(0);
    return token;
  }

  /**
   * Creates a token of type NUMBER from a numeric buffer. The
   * buffer is not cleaned or touched in any way
   *
   * @param buf     numbers buffer
   * @param currPos current expression pointer
   *
   * @return instance of number token
   */
  private Token createNumberToken(StringBuilder buf, int currPos) {
    return new Token(buf.toString(), currPos - buf.length(), Type.NUMBER);
  }

  /**
   * Creates a token from a non-numeric character.
   * <p>
   * May return null if the token could not be recognized
   *
   * @param ch  character from original expression
   * @param pos character position in expression
   *
   * @return a token instance with a recognized type, or
   * {@code null} if the token could not be recognized
   */
  private Token retrieveToken(char ch, int pos) {
    switch (ch) {
      case '(' -> {
        return new Token("(", pos, OPEN_BRACE);
      }
      case ')' -> {
        return new Token(")", pos, CLOSE_BRACE);
      }
      default -> {
        if (opSupp.hasOperatorForSymbol(ch)) {
          return new Token("" + ch, pos, OPERATOR);
        }
      } // end default block
    }

    return null;
  }
}
