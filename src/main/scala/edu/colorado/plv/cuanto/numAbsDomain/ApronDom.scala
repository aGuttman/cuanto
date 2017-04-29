package edu.colorado.plv.cuanto.numAbsDomain

import apron._
import gmp.Mpfr

/**
  * Created by lumber on 4/29/17.
  */
class ApronDom(override val dim: (Int, Int), override val dom: Array[AbsInterval]) extends AbsDom(dim, dom) {
  val man = new Octagon
  val a0 = new Abstract0(man, dim._1, dim._2, dom.map(int => int.interval.asInstanceOf[Interval]))

  private def scalarToDouble(scalar: Scalar): Double = {
    val ret = new MpfrScalar(0.1, Mpfr.RNDU).`val`
    scalar.toMpfr(ret, Mpfr.RNDU)
    ret.doubleValue(Mpfr.RNDU)
  }

  def getBound(expr: AbsExpr): AbsInterval = {
    val b = expr.expr match {
      case nonLinExpr: Texpr0Intern => a0.getBound(man, nonLinExpr)
      case linExpr: Linexpr0 => a0.getBound(man, linExpr)
      case _ => ???
    }
    new ApronInterval(scalarToDouble(b.inf), scalarToDouble(b.sup))
  }

  def getBound(dim: Int): AbsInterval = {
    val b = a0.getBound(man, dim)
    new ApronInterval(scalarToDouble(b.inf), scalarToDouble(b.sup))
  }

  def satisfy(cons: AbsCons): Boolean = {
    cons.cons match {
      case nonLinCons: Tcons0 => a0.satisfy(man, nonLinCons)
      case linCons: Lincons0 => a0.satisfy(man, linCons)
    }
  }
}
