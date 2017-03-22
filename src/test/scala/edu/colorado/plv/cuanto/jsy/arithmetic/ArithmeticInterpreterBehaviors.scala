package edu.colorado.plv.cuanto.jsy
package arithmetic

import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.prop.PropertyChecks

import scala.util.Try

/**
  * @author Bor-Yuh Evan Chang
  */
trait ArithmeticInterpreterBehaviors { self: FlatSpec with Matchers with PropertyChecks =>
  import edu.colorado.plv.cuanto.testing.implicits.tryEquality
  import implicits.stringToExpr

  val denoteTests = Table(
    "expression" -> "denotation",
    "2" -> 2,
    "1 + 1" -> 2,
    "1 - 1" -> 0,
    "1 * 1" -> 1,
    "1 / 1" -> 1,
    "1 + 2 + 3" -> 6,
    "1 + 2 * 3" -> 7
  )

  def interpreter(denote: Expr => Try[Double]) =
    forAll(denoteTests) { (e, n) =>
      it should s"interpret $e to $n" in {
        denote(e) shouldEqual n
      }
    }
}
