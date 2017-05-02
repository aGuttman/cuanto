package edu.colorado.plv.cuanto.scoot.ir

import java.util

import soot.ValueBox

/**
  * Created by Jared on 4/17/2017.
  */
class Expr(dt: soot.jimple.Expr) extends Value(dt: soot.Value){
  def getDt() = dt
}
  //empty interface

object Expr{
  def unapply(arg: Expr): Some[util.List[ValueBox]] = {
    Some(arg.getDt().getUseBoxes)
  }
}

