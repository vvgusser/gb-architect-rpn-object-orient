package me.wrum.rpn.infix.lexer;

import me.wrum.rpn.api.lexer.Lexer;
import me.wrum.rpn.infix.operator.support.OperatorsSupportFactory;
import me.wrum.rpn.infix.rules.BracesCountRule;
import me.wrum.rpn.infix.rules.CompositeRule;
import me.wrum.rpn.infix.rules.ConsecutiveNumbersRule;
import me.wrum.rpn.infix.rules.LegalFirstTokenRule;
import me.wrum.rpn.infix.rules.LegalLastTokenRule;
import me.wrum.rpn.infix.rules.LegalNumbersRule;
import me.wrum.rpn.infix.rules.OpenBracesRule;
import me.wrum.rpn.infix.rules.OperatorPositionRule;
import me.wrum.rpn.infix.rules.ValidCloseBraceTokensRule;

/**
 * Factory for creating configured instances of lexers
 *
 * @author Vyacheslav Gusser proweber1@mail.ru
 */
public final class LexerFactory {
  private LexerFactory() {
    throw new UnsupportedOperationException();
  }

  /**
   * Creates a lexer object ready for tokenization of infix
   * expressions and their validation
   *
   * @return configured lexer instance
   */
  public static Lexer initLexer() {
    var opSupp = OperatorsSupportFactory.instantiate();

    var compositeRule = new CompositeRule(
        new BracesCountRule(),
        new LegalFirstTokenRule(opSupp),
        new LegalLastTokenRule(),
        new OpenBracesRule(),
        new ValidCloseBraceTokensRule(),
        new ConsecutiveNumbersRule(),
        new LegalNumbersRule(),
        new OperatorPositionRule(opSupp)
    );

    return new InfixLexer(opSupp, compositeRule);
  }
}
