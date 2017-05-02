package edu.colorado.plv.cuanto.scoot.ir

import soot.RefType

/**
  * Created by s on 5/1/17.
  */
class NewExpr(n: soot.jimple.NewExpr) extends Expr(n : soot.jimple.NewExpr){
  def getN() = n

}
object NewExpr{
  def unapply(arg: NewExpr): Option[(RefType)] = {
    Some(new RefType(arg.getN().getBaseType))
  }
}
