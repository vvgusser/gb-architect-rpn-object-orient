package me.wrum.rpn.api.lexer;

/**
 * Types of tokens that a lexer can return
 *
 * @see Lexer
 * @see Token
 * @author Vyacheslav Gusser proweber1@mail.ru
 */
public enum Type {
  NUMBER,
  OPERATOR,
  OPEN_BRACE,
  CLOSE_BRACE
}
