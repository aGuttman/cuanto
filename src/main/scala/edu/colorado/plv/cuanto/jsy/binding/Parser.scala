package edu.colorado.plv.cuanto.jsy
package binding

import edu.colorado.plv.cuanto.jsy.common.{JsyParserLike, OpParserLike, UnitOpParser}

/** Parse variables and bindings.
  *
  * @author Bor-Yuh Evan Chang
  */
trait ParserLike extends OpParserLike with JsyParserLike {
  import ParserLike._

  /** Reduction operator from a sequence of statements to an expression.
    *
    * Accumulates the "continuation" expression, which may be `None`.
    *
    * It is expected that this method may be overriden with additional
    * cases.
    *
    * @see [[statements]] for the parser that uses this operator
    *       as a helper function.
    */
  def reduceToExpr: PartialFunction[(Stmt,Option[Expr]), Option[Expr]] = {
    case (Skip, eopt) => eopt
    case (E(e), None) => Some(e)
    case (E(e1), Some(e2)) => Some(Seq(e1, e2))
    case (Decl(d), None) => Some(d(Unit))
    case (Decl(d), Some(e2)) => Some(d(e2))
  }

  /** Parse a sequence of statements.
    *
    * @see [[stmt]] for parsing a single statement
    */
  override def statements: Parser[Expr] = {
    rep(stmt) ^^ { stmts =>
      stmts.foldRight(None: Option[Expr]) { (stmt, eopt) =>
        reduceToExpr(stmt, eopt)
      }.getOrElse(Unit)
    }
  }

  /** Parse a single statement.
    *
    * @see [[decl]] for parsing a declaration
    */
  def stmt: Parser[Stmt] =
    ";" ^^^ Skip |
    decl |
    (expr | block) ^^ E

  /** Parse a declaration. */
  def decl: Parser[Decl] =
    mode ~ withpos(ident) ~ withpos("=" ~> expr) ^^ {
      case mode ~ ((posx, x)) ~ ((pos1, e1)) => Decl(e2 => Bind(mode, Var(x) setPos posx, e1, e2) setPos pos1)
    }

  /** Parse a mode annotation. */
  def mode: Parser[Mode] =
    positioned(("const" | "let") ^^^ MConst)

  abstract override def opAtom: Parser[Expr] =
    positioned {
      "undefined" ^^^ Unit
    } |
    block |
    super.opAtom

  lazy val seqBop: OpPrecedence = List(List("," -> Seq))

  /** Parameter: define the non-terminal for the sub-expressions of sequencing expressions. */
  def seqsub: Parser[Expr]

  /** ''seq'' ::= ''seqsub,,1,,'' `,` ''seqsub,,2,,'' */
  def seq: Parser[Expr] = binaryLeft(seqBop, seqsub)

  override def start: Parser[Expr] = statements
  override def expr: Parser[Expr] = seq
}

object ParserLike {
  /** Statements.
    *
    * Statements only exist in the concrete syntax, so they are eliminated during parsing.
    */
  trait Stmt

  /** Skip: unit statement */
  case object Skip extends Stmt

  /** An expression as a statement. */
  case class E(e: Expr) extends Stmt

  /** Declarations.
    *
    * A declaration is a parser that takes a continuation Expr to yield an Expr.
    */
  case class Decl(d: Expr => Expr) extends Stmt
}

/** The parser for just bindings. */
object Parser extends UnitOpParser with ParserLike {
  override lazy val bop: OpPrecedence = Nil
  override def seqsub: Parser[Expr] = binary
}
