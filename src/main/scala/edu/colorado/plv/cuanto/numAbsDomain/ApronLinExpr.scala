package edu.colorado.plv.cuanto.numAbsDomain

import apron.{Linexpr0, MpfrScalar, MpqScalar}
import gmp.Mpfr

import collection.JavaConverters._

/**
  * Created by lumber on 4/27/17.
  */
class ApronLinExpr extends AbsExpr {
  private var Expr: Linexpr0 = _

  def expr = Expr

  def this(const: Int, terms: Array[ApronLinTerm]) {
    this
    Expr = new Linexpr0(terms.map(t => t.term), new MpqScalar(const))
  }

  def this(const: Double, terms: Array[ApronLinTerm]) {
    this
    Expr = new Linexpr0(terms.map(t => t.term), new MpfrScalar(const, Mpfr.RNDU))
  }
}
