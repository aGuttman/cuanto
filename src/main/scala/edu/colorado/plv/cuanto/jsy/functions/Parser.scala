package edu.colorado.plv.cuanto.jsy
package functions

import edu.colorado.plv.cuanto.jsy.common.{JsyParserLike, OpParserLike}

/**
  * @author Bor-Yuh Evan Chang
  */
trait ParserLike extends OpParserLike with JsyParserLike {

  abstract override def optyp: Parser[Typ] =
    ("(" ~> repsep(pairoptsep(ident ^^ Var, ":", typ), ",") <~ ")") ~ ("=>" ~> typ) ^^ {
      case params ~ t => TFun(params, t)
    } |
    super.optyp

}

/**
  * @author Bor-Yuh Evan Chang
  */
object Parser {

}


