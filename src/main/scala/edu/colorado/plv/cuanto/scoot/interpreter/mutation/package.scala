package edu.colorado.plv.cuanto.scoot.interpreter
package mutation

package object mutation {

  // private def some[A](a: A): Option[A] = Some(a)

  // /** Step an environment forward over a single statement */
  // def step(stmt: AssignStmt)(env: Env): Option[Env] = {
  //   val varNameO: Option[Local] = stmt.getLeftOp() match {
  //     case l: Local => Some(l)
  //     case _ => None
  //   }
  //   val newValueO: Option[Result] = denote(stmt.getRightOp(), env)
  //   for {
  //     varName <- varNameO
  //     newValue <- newValueO
  //   } yield env + (varName.getName() -> newValue)
  // }

  // /** Interpret the integer value of a variable mutated over a sequence
  //   * of assignment statements */
  // def denote(ss: Traversable[AssignStmt], v: Local): Option[Result] =
  //   denote(ss).flatMap(_ get v.getName())

  // def denote(ss: Traversable[AssignStmt]): Option[Env] =
  //   ss.foldLeft(some(emptyEnv))((env,stmt) => env.flatMap(step(stmt)))
}
