package edu.colorado.plv.cuanto.scoot.ir

import soot.RefType

/**
  * Created by s on 5/1/17.
  */
class NewExpr(n: soot.jimple.NewExpr) extends Expr(soot.jimple.NewExpr){

}
object NewExpr{
  def unapply(arg: NewExpr): Option[(soot.RefType, List[Value])] = ???
}
