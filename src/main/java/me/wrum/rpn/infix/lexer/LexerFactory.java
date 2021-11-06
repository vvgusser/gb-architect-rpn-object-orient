package me.wrum.rpn.infix.lexer;

import me.wrum.rpn.api.lexer.Lexer;
import me.wrum.rpn.infix.operator.OperatorsSupportFactory;
import me.wrum.rpn.infix.rules.BracesCountRule;
import me.wrum.rpn.infix.rules.CompositeRule;
import me.wrum.rpn.infix.rules.ConsecutiveNumbersRule;
import me.wrum.rpn.infix.rules.LegalFirstTokenRule;
import me.wrum.rpn.infix.rules.LegalLastTokenRule;
import me.wrum.rpn.infix.rules.LegalNumbersRule;
import me.wrum.rpn.infix.rules.OpenBracesRule;
import me.wrum.rpn.infix.rules.OperatorPositionRule;
import me.wrum.rpn.infix.rules.ValidCloseBraceTokensRule;

public final class LexerFactory {
  private LexerFactory() {
    throw new UnsupportedOperationException();
  }

  public static Lexer initLexer() {
    var opSupp = OperatorsSupportFactory.instantiate();

    var compositeRule = new CompositeRule(
        new BracesCountRule(),
        new LegalFirstTokenRule(opSupp),
        new LegalLastTokenRule(),
        new ValidCloseBraceTokensRule(),
        new ConsecutiveNumbersRule(),
        new LegalNumbersRule(),
        new OpenBracesRule(),
        new OperatorPositionRule(opSupp)
    );

    return new InfixLexer(opSupp, compositeRule);
  }
}
