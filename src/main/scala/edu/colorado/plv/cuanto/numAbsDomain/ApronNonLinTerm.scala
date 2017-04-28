package edu.colorado.plv.cuanto.numAbsDomain

import apron._
import gmp.Mpfr

/**
  * Created by lumber on 4/27/17.
  */
class ApronNonLinTerm extends AbsTerm {
  private var Term: Texpr0Node = _

  def term: Texpr0Node = Term

  def this(op: Bop, left: Texpr0DimNode, right: Texpr0DimNode) {
    this
    Term = op match {
      case ADD => new Texpr0BinNode(Texpr0BinNode.OP_ADD, left, right)
      case SUB => new Texpr0BinNode(Texpr0BinNode.OP_SUB, left, right)
      case MUL => new Texpr0BinNode(Texpr0BinNode.OP_MUL, left, right)
      case DIV => new Texpr0BinNode(Texpr0BinNode.OP_DIV, left, right)
      case MOD => new Texpr0BinNode(Texpr0BinNode.OP_MOD, left, right)
      case POW => new Texpr0BinNode(Texpr0BinNode.OP_MUL, left, right)
    }
  }

  def this(op: Bop, dim1: Int, dim2: Int) = this(op, new Texpr0DimNode(dim1), new Texpr0DimNode(dim2))

  def this(coeff: Double) {
    this
    Term = new Texpr0CstNode(new MpfrScalar(coeff, Mpfr.RNDU))
  }

  def this(coeff: Int) {
    this
    Term = new Texpr0CstNode(new MpqScalar(coeff))
  }
}
