package edu.colorado.plv.cuanto.numAbsDomain

import apron.{Interval, Linterm0, MpfrScalar, MpqScalar}
import gmp.Mpfr

/**
  * Created by lumber on 4/27/17.
  */
class ApronLinTerm extends AbsTerm {
  private var Term: Linterm0 = _

  def term: Linterm0 = Term

  def this(coeff: Double, dim: Int) {
    this
    Term = new Linterm0(dim, new MpfrScalar(coeff, Mpfr.RNDU))
  }

  def this(coeff: Int, dim: Int) {
    this
    Term = new Linterm0(dim, new MpqScalar(coeff))
  }

  def this(lb: Int, ub: Int, dim: Int) {
    this
    Term = new Linterm0(dim, new ApronInterval(lb, ub).interval)
  }
}
