package edu.colorado.plv.cuanto.numAbsDomain

import apron._

/**
  * Created by lumber on 4/27/17.
  */
class ApronLinCons(override val expr: ApronLinExpr, override val op: Uop) extends AbsCons(expr, op) {
  def cons: Lincons0 = Cons

  def neg(expr: Linexpr0): Linexpr0 = {
    val ret = new Linexpr0(expr)
    ret.getLinterms.foreach(term => term.coeff.neg())
    ret.getCst.neg()
    ret
  }

  private val Cons: Lincons0 = op match {
    case LE => new Lincons0(Tcons0.SUPEQ, neg(expr.expr))
    case LT => new Lincons0(Tcons0.SUP, neg(expr.expr))
    case GE => new Lincons0(Tcons0.SUPEQ, expr.expr)
    case GT => new Lincons0(Tcons0.SUP, expr.expr)
    case NE => new Lincons0(Tcons0.DISEQ, expr.expr)
    case EQ => new Lincons0(Tcons0.EQ, expr.expr)
  }
}
