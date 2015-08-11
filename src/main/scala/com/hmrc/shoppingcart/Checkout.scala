package com.hmrc.shoppingcart

/**
 * Created by Jim on 10/08/2015.
 */
case class Checkout(discounts: Map[Goods, Function1[Int, Int]] = Map[Goods, Function1[Int, Int]]()) {
  def checkout(bskt: List[Goods]): BigDecimal = {
    bskt.groupBy(identity).collect{case x => (x._1, x._2.length)}.collect{ case x => getPrice(x._1, x._2) }.foldLeft(BigDecimal(0)){ (a,b) => a + b.getOrElse(BigDecimal(0))}
  }

  val prices = Map[Goods, BigDecimal](Apple -> 0.60, Orange -> 0.25)

  val defaultDiscount = (x: Int) => x

  def getPrice(item: Goods, qty: Int): Option[BigDecimal] =
    prices.get(item).map(_ * discounts.getOrElse(item, defaultDiscount)(qty))
}
