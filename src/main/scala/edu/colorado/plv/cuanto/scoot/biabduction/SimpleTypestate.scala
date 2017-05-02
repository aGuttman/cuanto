package edu.colorado.plv.cuanto.scoot.biabduction

import edu.colorado.plv.cuanto.jsy.Expr
import edu.colorado.plv.cuanto.scoot.ir.{AssignStmt, Body, Stmt}
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
  case class TopValue() extends Value
  case class SingleObjectValue() extends Value //TODO: fields
  case class NullValue()
  case class BottomValue() extends Value





  sealed abstract class AbstractDomain
  case class ConcretePointsToDomain(pts: Map[AbstractAddress, Value]) extends AbstractDomain
  case class TTop() extends AbstractDomain
  type MethodIdentifier = (String,String)
  type MethodAbstractDomainTransformer = Map[AbstractDomain, AbstractDomain]

  //Graph of these represents the transformation applied by a method
  case class TransformerGraphNode(m: MethodAbstractDomainTransformer, next: Set[TransformerGraphNode])

  //Result of processing a method
  case class MethodTriple(methodIdentifier: MethodIdentifier, p : MethodAbstractDomainTransformer)


  def processMethod(method: SootMethod): MethodTriple = {
    val body = new Body(method.getActiveBody)

    if(body.stmts.length > 0){
      val stmtStart: Set[Stmt] = Set(body.stmts(0))
      val commandsMap = processCommands(body, Map[Stmt, TransformerGraphNode]())
      ???
    }else{
      //Return Top to Top for empty method, refinement pass later
      MethodTriple((method.getClass.getName, method.getSignature), Map((TTop(),TTop())))
    }

  }

  //Graph of transforming methods


  def processCommands(body: Body, upTo: Map[Stmt,
                        TransformerGraphNode]): Map[Stmt, TransformerGraphNode] = {
    body.stmts.map(a => a match{
      case AssignStmt(lhs, rhs) => ???
//      case
      case _ => ???
    })
    ???
  }
  def processExpr(expr: Expr): Map[AbstractDomain, Value] = expr match{
    case
    case _ => ???
  }

}
