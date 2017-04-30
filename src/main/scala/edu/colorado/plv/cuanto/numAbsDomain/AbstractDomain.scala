package edu.colorado.plv.cuanto.numAbsDomain

import edu.colorado.plv.cuanto.abstracting.{Abstractable, Abstraction}
import edu.colorado.plv.cuanto.jsy.numerical.{Denotational, Evalable}

/**
  * Created by lumber on 4/29/17.
  */
abstract class AbstractDomain[D] extends Abstraction with Evalable[D] with Abstractable[D] {
    type V = Either[Double,Boolean]

    def not(v: V): V = v match { case Right(b) => Right(!b) ; case _ => throw new UnsupportedOperationException }
    def and(l: V, r: V): V = (l,r) match { case (Right(l), Right(r)) => Right(l && r) ; case _ => throw new UnsupportedOperationException }
    def or(l: V, r: V): V = (l,r) match { case (Right(l), Right(r)) => Right(l || r) ; case _ => throw new UnsupportedOperationException }
    def ite(cond: V, l: V, r: V): V = cond match { case Right(cond) => if(cond) l else r ; case _ => throw new UnsupportedOperationException }

    def divide(l: V, r: V): V = (l,r) match { case (Left(l),Left(r)) => Left( l / r ) ; case _ => throw new UnsupportedOperationException }
    def minus (l: V, r: V): V = (l,r) match { case (Left(l),Left(r)) => Left( l - r ) ; case _ => throw new UnsupportedOperationException }
    def plus  (l: V, r: V): V = (l,r) match { case (Left(l),Left(r)) => Left( l + r ) ; case _ => throw new UnsupportedOperationException }
    def times (l: V, r: V): V = (l,r) match { case (Left(l),Left(r)) => Left( l * r ) ; case _ => throw new UnsupportedOperationException }
    def negate(v : V): V = v match { case Left(v) => Left( - v) ; case _ => throw new UnsupportedOperationException }

    def equ(l: V, r: V): V = (l,r) match { case (Left(l),Left(r)) => Right( l == r ) ; case _ => throw new UnsupportedOperationException }
    def neq(l: V, r: V): V = (l,r) match { case (Left(l),Left(r)) => Right( l != r ) ; case _ => throw new UnsupportedOperationException }
    def ge (l: V, r: V): V = (l,r) match { case (Left(l),Left(r)) => Right( l >= r ) ; case _ => throw new UnsupportedOperationException }
    def gt (l: V, r: V): V = (l,r) match { case (Left(l),Left(r)) => Right( l >  r ) ; case _ => throw new UnsupportedOperationException }
    def le (l: V, r: V): V = (l,r) match { case (Left(l),Left(r)) => Right( l <= r ) ; case _ => throw new UnsupportedOperationException }
    def lt (l: V, r: V): V = (l,r) match { case (Left(l),Left(r)) => Right( l <  r ) ; case _ => throw new UnsupportedOperationException }


    def truthiness(v: V): Set[Boolean] = Set( v fold ( {d => d != 0}, {b => b} ) )

    override def represent(v:Any): V = v match {
      case d:Double  => Left(d)
      case b:Boolean => Right(b)
      case _ => throw new UnsupportedOperationException("Only doubles and booleans are representable in the numerical sublanguage")
    }

    def join(e1: V, e2: V): V = {
      e1
    }
}
