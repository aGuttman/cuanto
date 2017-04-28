package edu.colorado.plv.cuanto.numAbsDomain

import apron.Texpr0Intern

/**
  * Created by lumber on 4/27/17.
  */
class ApronNonLinExpr extends AbsExpr {
  private var Expr: Texpr0Intern = _

  def expr = Expr

  def this(expr: ApronLinExpr) {
    this
    Expr = new Texpr0Intern(expr.expr)
  }

  def this(term: ApronNonLinTerm) {
    this
    Expr = new Texpr0Intern(term.term)
  }
}
