package edu.colorado.plv.cuanto

import soot.{Body, Local, SootMethod}
import soot.dexpler.typing.UntypedConstant
import soot.jimple._
import soot.shimple.ShimpleExpr
import soot.shimple.toolkits.scalar.SEvaluator.MetaConstant

import scala.collection.JavaConverters._

/**
  * Created by lumber on 4/13/17.
  */
package object jimpleinterpreter {
  /**
    * Return the value of the constant variable
    * @param constant a constant variable
    * @return the value of the constant variable
    */
  @deprecated()
  def getConstantVal(constant: Constant): Int = {
    constant match {
      case cons: ClassConstant => ???
      case cons: MetaConstant => ???
      case cons: MethodHandle => ???
      case cons: NullConstant => ???
      case cons: NumericConstant =>
        cons match {
          case _cons: ArithmeticConstant =>
            _cons match {
              case __cons: IntConstant => __cons.value
              case __cons: LongConstant => __cons.value.toInt
            }
          case _cons: RealConstant =>
            _cons match {
              case __cons: DoubleConstant => __cons.value.toInt
              case __cons: FloatConstant => __cons.value.toInt
            }
        }
      case cons: StringConstant => ???
      case cons: UntypedConstant => ???
    }
  }

  /**
    * Return the next statement to be sequentially interpreted
    * @param stmt current statement
    * @param body the method body that contains current statement
    * @return the next statement to be sequentially interpreted
    */
  // TODO: is there a better way to do this?
  @deprecated()
  private def getNextStmt(stmt: Stmt, body: Body): Stmt = {
    // cannot use foldRight here... (otherwise it would be in the wrong direction of traversal)
    body.getUnits.iterator().asScala.foldLeft(false) {
      (acc, nextStmt) => if (acc) return nextStmt.asInstanceOf[Stmt]; nextStmt == stmt
    }
    null

    /*val it = body.getUnits.iterator()
    while (it.hasNext) {
      val nextStmt = it.next()
      if (nextStmt == stmt) {
        if (it.hasNext) {
          it.next() match {
            case s: Stmt => return s
            case _ => assert(false, "Type error: Next Stmt")
          }
        } else {
          return null
        }
      }
    }
    null*/
  }

  /**
    * Concretely interpret a method invocation
    * @param method a method to be interpreted
    * @param memory current memory
    * @param argList the list of actual arguments to be passed to the method that is going to be interpreted
    * @return the return value of the method that is finished for interpreting (could be None for a return type of void)
    */
  @deprecated()
  def concreteInterpret(method: SootMethod, memory: MutableMemory, argList: List[Int]): Option[Int] = {
    var ret: Option[Int] = None // return value of the method being interpreted
    val body = method.retrieveActiveBody()
    var pc = body.getUnits.getFirst match {
      case s: Stmt => s
      case _ => assert(false, "Type error: First statement of the method body"); null
    }
    while (pc != null) {
      val (newPC, retVal) = _concreteInterpret(pc, body, memory, argList)
      pc = newPC
      ret = retVal
    }
    ret
  }

  /**
    * Concretely interpret a jimple statement
    * @param statement the jimple statement to be interpreted
    * @param body the method body that contains the statement to be interpreted
    * @param memory current memory
    * @param argList if the jimple statement contains a method invocation, then this is the list of actual arguments to be passed to the method that is going to be invoked. Otherwise, this parameter is not used.
    * @return the next statement to be interpreted and the return value of the method invocation (could be None for a return type of void)
    */
  @deprecated()
  private def _concreteInterpret(statement: Stmt, body: Body, memory: MutableMemory, argList: List[Int]): (Stmt, Option[Int]) = {
    val DEBUG = false
    if (DEBUG) println("[" + body.getMethod.getName + "] " + statement)

    var ret: Option[Int] = None // return value of a return statement

    val pc = statement match {
      case stmt: DefinitionStmt =>
        stmt match {
          case _stmt: AssignStmt =>
            _stmt.getLeftOp match {
              case left: Local => memory.setVal(left, memory.getVal(_stmt.getRightOp))
              case left@_ => println(left.getClass); assert(false, "Left op of AssignStmt is not Local")
            }
          case _stmt: IdentityStmt =>
            // Initialize argument(s)
            _stmt.getLeftOp match {
              case left: Local => memory.setVal(left, argList(left.getName.substring(1).toInt))
              case left@_ => println(left.getClass); assert(false, "Left op of AssignStmt is not Local")
            }
        }
        getNextStmt(stmt, body)
      case stmt: GotoStmt =>
        stmt.getTarget match {
          case s: Stmt => s
          case _ => assert(false, "Type error: Target of GotoStmt"); null
        }
      case stmt: IfStmt =>
        val cond = memory.getVal(stmt.getCondition)
        if (cond == 1) {
          stmt.getTarget
        } else if (cond == 0) {
          getNextStmt(stmt, body)
        } else {
          assert(false, "Evaluation error in if condition"); null
        }
      case stmt: ReturnStmt => ret = Some(memory.getVal(stmt.getOp)); null
      case stmt: ReturnVoidStmt => null
      case stmt: InvokeStmt => // Ignore
        invoke(stmt.getInvokeExpr, memory)
        getNextStmt(stmt, body)
      case stmt: SwitchStmt => // Ignore
        getNextStmt(stmt, body)
      case stmt: MonitorStmt => // Ignore
        getNextStmt(stmt, body)
      case stmt: ThrowStmt => // Ignore
        getNextStmt(stmt, body)
      case stmt: BreakpointStmt => // Ignore
        getNextStmt(stmt, body)
      case stmt: NopStmt => // Ignore
        getNextStmt(stmt, body)
      case stmt: RetStmt => // Ignore
        getNextStmt(stmt, body)
    }
    (pc, ret)
  }

  /**
    * Interpret an invoke expression
    * @param expr the invoke expression
    * @param memory current memory
    * @return the return value of the method invocation (could be None for a return type of void)
    */
  @deprecated()
  private def invoke(expr: InvokeExpr, memory: MutableMemory): Option[Int] = {
    val argList: List[Int] = expr.getArgs.asScala.map { arg => memory.getVal(arg) }.toList

    memory.createNewStack()
    val ret = concreteInterpret(expr.getMethod, memory, argList)
    memory.popCurrentStack()
    ret
  }

  /**
    * Evaluate the expression
    * @param expr the expression to be evaluated
    * @param memory current memory
    * @return the evaluation of the expression
    */
  @deprecated()
  def eval(expr: Expr, memory: MutableMemory): Int = {
    expr match {
      // case _expr: AnyNewExpr => ???
      case _expr: BinopExpr =>
        _expr match {
          case __expr: AddExpr =>
            memory.getVal(__expr.getOp1) + memory.getVal(__expr.getOp2)
          case __expr: AndExpr => ???
          case __expr: CmpExpr => ???
          case __expr: CmpgExpr => ???
          case __expr: CmplExpr => ???
          case __expr: ConditionExpr =>
            val left = memory.getVal(__expr.getOp1)
            val right = memory.getVal(__expr.getOp2)
            __expr match {
              case ___expr: EqExpr => if (left == right) 1 else 0
              case ___expr: GeExpr => if (left >= right) 1 else 0
              case ___expr: GtExpr => if (left > right) 1 else 0
              case ___expr: LeExpr => if (left <= right) 1 else 0
              case ___expr: LtExpr => if (left < right) 1 else 0
              case ___expr: NeExpr => if (left != right) 1 else 0
            }
          case __expr: DivExpr =>
            memory.getVal(__expr.getOp1) / memory.getVal(__expr.getOp2)
          case __expr: MulExpr =>
            memory.getVal(__expr.getOp1) * memory.getVal(__expr.getOp2)
          case __expr: OrExpr => ???
          case __expr: RemExpr => ???
          case __expr: ShlExpr => ???
          case __expr: SubExpr =>
            memory.getVal(__expr.getOp1) - memory.getVal(__expr.getOp2)
          case __expr: UshrExpr => ???
          case __expr: XorExpr => ???
        }
      case _expr: CastExpr => ???
      case _expr: InstanceOfExpr => ???
      case _expr: InvokeExpr => invoke(_expr, memory) match {
        case Some(x) => x
        case None => ???
      }
      case _expr: NewArrayExpr => ???
      case _expr: NewExpr => ???
      case _expr: NewMultiArrayExpr => ???
      case _expr: ShimpleExpr => ???
      case _expr: UnopExpr => ???
    }
  }
}
