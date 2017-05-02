package edu.colorado.plv.cuanto.scoot.ir

/**
  * Created by s on 5/2/17.
  */
class Type(n : soot.Type) {

}

class RefLikeType(n : soot.RefLikeType) extends Type(n: soot.RefLikeType) {

}

class RefType(n : soot.RefType) extends RefLikeType(n : soot.RefLikeType){
  def getN() = n

}

object RefType{
  def unapply(arg: RefType): Option[String] = {
    Some(arg.getN().getClassName)
  }
}
