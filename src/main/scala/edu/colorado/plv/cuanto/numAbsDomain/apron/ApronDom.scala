package edu.colorado.plv.cuanto.numAbsDomain.apron

import apron._
import edu.colorado.plv.cuanto.numAbsDomain._

/**
  * Created by lumber on 4/29/17.
  */
class ApronDom(override val dim: (Int, Int), override val dom: Array[AbsInterval]) extends AbsDom(dim, dom) {
  private val man = new Polka(true)
  private val a0 = new Abstract0(man, dim._1, dim._2, dom.map(int => int.interval.asInstanceOf[Interval]))

  override def toString: String = dom.foldLeft("Domain:\n")((acc, interval) => acc + "  " + interval.toString + "\n")

  private def scalarToDouble(scalar: Scalar): Double = {
    val ret = new MpfrScalar(0.1, ROUNDING).`val`
    scalar.toMpfr(ret, ROUNDING)
    ret.doubleValue(ROUNDING)
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
