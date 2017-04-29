package edu.colorado.plv.cuanto.numAbsDomain

import apron.Interval

/**
  * Created by lumber on 4/29/17.
  */
class ApronInterval(lb: Double, ub: Double) extends AbsInterval(lb, ub) {
  def interval = new Interval(lb, ub)
}
