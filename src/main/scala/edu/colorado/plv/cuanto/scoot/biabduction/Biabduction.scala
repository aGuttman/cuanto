package edu.colorado.plv.cuanto.scoot.biabduction

import soot.SootMethod

/**
  * Created by Shawn Meier on 4/25/17.
  */
object Biabduction {
  trait AbstractHeapDomain{

  }
  trait AbstractValueDomain{

  }
  case class AbstractDomain(heapDomain: AbstractHeapDomain)
  type MethodIdentifier = SootMethod //TODO: change to something better
  case class MethodTriple(methodIdentifier: MethodIdentifier, p : AbstractDomain => AbstractDomain)
  def processMethod(method: MethodIdentifier): MethodTriple = ???
}
