package edu.colorado.plv.cuanto

import gmp.Mpfr

/**
  * Created by lumber on 4/20/17.
  */
package object numAbsDomain {
  trait Bop
  trait Uop

  case object ADD extends Bop {
    override def toString: String = "+"
  }
  case object SUB extends Bop {
    override def toString: String = "-"
  }
  case object MUL extends Bop {
    override def toString: String = "*"
  }
  case object DIV extends Bop {
    override def toString: String = "/"
  }
  case object MOD extends Bop {
    override def toString: String = "%"
  }
  case object POW extends Bop {
    override def toString: String = "^"
  }

  case object LE extends Uop {
    override def toString: String = "<="
  }
  case object LT extends Uop {
    override def toString: String = "<"
  }
  case object GE extends Uop {
    override def toString: String = ">="
  }
  case object GT extends Uop {
    override def toString: String = ">"
  }
  case object NE extends Uop {
    override def toString: String = "!="
  }
  case object EQ extends Uop {
    override def toString: String = "=="
  }

  val ROUNDING = Mpfr.RNDU
}
