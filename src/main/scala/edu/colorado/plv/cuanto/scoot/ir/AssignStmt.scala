package edu.colorado.plv.cuanto.scoot.ir

/**
  * Created by Jared on 4/17/2017.
  */
class AssignStmt(dt: soot.jimple.AssignStmt) extends DefinitionStmt(dt: soot.jimple.DefinitionStmt)

object AssignStmt{
  def unapply(arg: AssignStmt): Some[(Value, Value)] = {
    Some(arg.leftOp,arg.rightOp)
  }
}
  //AssignStmt has setLeftOp and setRightOp, which are unnecessary for the moment

