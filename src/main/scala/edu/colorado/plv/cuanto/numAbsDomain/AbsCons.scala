package edu.colorado.plv.cuanto.numAbsDomain

/**
  * Created by lumber on 4/20/17.
  */
abstract class AbsCons(val expr: AbsExpr, val op: Cop) {
  def cons: Any
}
