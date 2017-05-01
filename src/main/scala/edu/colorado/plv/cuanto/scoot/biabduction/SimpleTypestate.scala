package edu.colorado.plv.cuanto.scoot.biabduction

import edu.colorado.plv.cuanto.scoot.ir.{Body, Stmt}
import soot.{SootClass, SootField, SootMethod}

import scala.collection.JavaConverters._

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
  type MethodIdentifier = (String,String) //TODO: change to something better
  case class MethodTriple(methodIdentifier: MethodIdentifier, p : (AbstractDomain, STSSettings, STSContext) => AbstractDomain)


  def processMethod(method: SootMethod): MethodTriple = {
    val body = new Body(method.getActiveBody)

    val initial = (a: AbstractDomain, s: STSSettings, c: STSContext) => a
    if(body.stmts.length > 0){
      body.stmts(0)
      ???
    }else{
      MethodTriple((method.getClass.getName, method.getSignature), initial)
    }

  }

  //The following two classes will eventually
  case class STSContext() //Calling context plus methods called so far //TODO: how to deal with recursion?

  case class STSSettings() //Policies for choosing when to widen

}
