package edu.colorado.plv.cuanto.numAbsDomain.apron

import apron._
import edu.colorado.plv.cuanto.numAbsDomain._

/**
  * Created by lumber on 4/29/17.
  */
class ApronDom extends AbsDom {
  private var a0: Abstract0 = _

  def this(dim: (Int, Int), dom: Array[AbsInterval], man: Manager) {
    this
    a0 = new Abstract0(man, dim._1, dim._2, dom.map(int => int.interval.asInstanceOf[Interval]))
  }

  def this(a0p: Abstract0) {
    this
    new Abstract0(a0.getCreationManager, a0p)
  }

  override def toString: String = dom.foldLeft("Domain:\n")((acc, interval) => acc + "  " + interval.toString + "\n")

  private def scalarToDouble(scalar: Scalar): Double = {
    val ret = new MpfrScalar(0.1, ROUNDING).`val`
    scalar.toMpfr(ret, ROUNDING)
    ret.doubleValue(ROUNDING)
  }

  def getBound(expr: AbsExpr): AbsInterval = {
    val b = expr.expr match {
      case nonLinExpr: Texpr0Intern => a0.getBound(a0.getCreationManager, nonLinExpr)
      case linExpr: Linexpr0 => a0.getBound(a0.getCreationManager, linExpr)
      case _ => ???
    }
    new ApronInterval(scalarToDouble(b.inf), scalarToDouble(b.sup))
  }

  def getBound(dim: Int): AbsInterval = {
    val b = a0.getBound(a0.getCreationManager, dim)
    new ApronInterval(scalarToDouble(b.inf), scalarToDouble(b.sup))
  }

  def satisfy(cons: AbsCons): Boolean = {
    cons.cons match {
      case nonLinCons: Tcons0 => a0.satisfy(a0.getCreationManager, nonLinCons)
      case linCons: Lincons0 => a0.satisfy(a0.getCreationManager, linCons)
    }
  }

  def join(apronDom: ApronDom): Boolean = {
    if (a0.getCreationManager == apronDom.a0.getCreationManager) {
      a0.join(a0.getCreationManager, apronDom.a0)
      true
    } else
      false
  }

  def joinCopy(apronDom: ApronDom): ApronDom = {
    if (a0.getCreationManager == apronDom.a0.getCreationManager)
      new ApronDom(a0.joinCopy(a0.getCreationManager, apronDom.a0))
    else
      new ApronDom(a0)
  }
}

object ApronDom {
  val Polka = new Polka(true)
  val Oct = new Octagon
  val Box = new Box
}
