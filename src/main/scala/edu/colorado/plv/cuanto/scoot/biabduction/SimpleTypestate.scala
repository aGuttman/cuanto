package edu.colorado.plv.cuanto.scoot.biabduction

import soot.{SootClass, SootField, SootMethod}

/**
  * Created by Shawn Meier on 4/25/17.
  */
object SimpleTypestate {
  trait AbstractHeapDomain{

  }
//  trait AbstractValueDomain{
//
//  }
  case class AbstractAddress(int: Int)
  abstract class Reference //field or variable in program
  case class Local(method: SootMethod, name: String) extends Reference
  case class Field(clazz: SootClass, addr: AbstractAddress, field: SootField) extends Reference
  case class StaticField(clazz: SootClass, field: SootField) extends Reference


  abstract class Value
  case class Top() extends Value
  case class SingleObject() extends Value
  case class Bottom() extends Value



  class ConcretePointsToDomain()

  case class AbstractDomain(heapDomain: ConcretePointsToDomain)
  type MethodIdentifier = SootMethod //TODO: change to something better
  case class MethodTriple(methodIdentifier: MethodIdentifier, p : AbstractDomain => AbstractDomain)
  def processMethod(method: MethodIdentifier): MethodTriple = ???

}
