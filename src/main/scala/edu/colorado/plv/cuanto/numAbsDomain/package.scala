package edu.colorado.plv.cuanto

/**
  * Created by lumber on 4/20/17.
  */
package object numAbsDomain {
  trait Bop
  trait Uop

  case object ADD  extends Bop
  case object SUB extends Bop
  case object MUL extends Bop
  case object DIV   extends Bop
  case object MOD   extends Bop
  case object POW   extends Bop

  case object LE extends Uop
  case object LT extends Uop
  case object GE extends Uop
  case object GT extends Uop
  case object NE extends Uop
  case object EQ extends Uop
}
