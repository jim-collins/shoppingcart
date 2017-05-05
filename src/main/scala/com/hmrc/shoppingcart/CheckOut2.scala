package com.hmrc.shoppingcart

import com.hmrc.shoppingcart.CheckOut2.Promotion

import scala.collection.mutable.ListBuffer

/**
  * Created by Jim on 5/1/2017.
  */
case class CheckOut2(promotions: List[Promotion]) {

  import CheckOut2._
  def checkout(gds: List[Goods]): BigDecimal = {
    val bsk = addToBasket(gds)
    def sum[T](gds: List[T])( f: T => BigDecimal): BigDecimal = gds.foldLeft(BigDecimal(0))(_ + f(_))

    sum(bsk.offers)( _.price) + sum(bsk.gds)(prices.get(_).getOrElse(BigDecimal(0)))

  }

  def addToBasket(gd: List[Goods]): Basket = {
    def applyPromotion(prom: List[Promotion], b: Basket): Basket = {
      prom match {
        case h :: tail => val (offer, goods) = h(b.gds)
          applyPromotion(tail, Basket(offer :: b.offers, goods))
        case Nil => b
      }
    }
    applyPromotion(promotions, Basket(Nil, gd))
  }

  val appDiscount = (x: Int) => x - (x / 2)
  val threeFor2AppleBananas = (gds: List[Goods]) => {
    val discountGds = Set[Goods](Apple, Orange)
    var n = 0
    val blah = gds.foldLeft(0)((q: Int, g: Goods) => if(discountGds.contains(g)) q + 1 else q)
    blah - (blah / 2)
  }


}

object CheckOut2 {
  type Promotion = Function[List[Goods], (Offer, List[Goods])]
  type GoodsInProm = (Goods, Boolean)

  def dis(gds: List[Goods]): (Offer, List[Goods]) = {
    val (disc, nonDis) = partitionGoods(gds, Set(Apple, Bananna), 3)
    (price2(disc, 3), nonDis)
  }


  def partitionGoods(g: List[Goods], discountedGds: Set[Goods], n: Int): (List[Goods], List[Goods]) ={
    val goodsForOffer: ListBuffer[Goods] = ListBuffer()
    val goodsNotForOffer: ListBuffer[Goods] = ListBuffer()
    def agg(gds: List[Goods]): Unit ={
      val discgds = gds take n
      if (discgds.size == n) {
        goodsForOffer ++= discgds
        agg(gds drop n)
      }
      else
        goodsNotForOffer ++= discgds
    }
    val gdsToDiscount = g.filter(discountedGds.contains(_))
    val remainingGds = g diff gdsToDiscount
    agg(gdsToDiscount)
    (goodsForOffer.toList, remainingGds ++ goodsNotForOffer)
  }

  def price(gds: List[Goods], disc: Int => Int): Offer = {
    val price = minPrice(gds)
    gds  match {
      case h :: tail => Offer(gds, price * disc(gds.size))
      case _ => Offer(Nil, BigDecimal(0))
    }
  }

  def price2(gds: List[Goods], discNum: Int): Offer = {
    println("gds = " + gds)
    val numToDiscard = gds.size / discNum

    println("num dis = " + numToDiscard)
    gds  match {
      case h :: tail => Offer(gds, sum(sortGoods(gds) drop numToDiscard))
      case _ => Offer(Nil, BigDecimal(0))
    }
  }

  def sum(gds: List[Goods]): BigDecimal = {
    gds.foldLeft(BigDecimal(0))((a,b) => a + prices(b))
  }

  def sortGoods(gds: List[Goods]): List[Goods] = gds.sortBy(g => prices(g))

  def minPrice(gds: List[Goods]): BigDecimal = {
    0.20
  }

  val prices = Map[Goods, BigDecimal](Apple -> 0.60, Orange -> 0.25, Bananna -> .20)

}

case class Offer(gds: List[Goods], price: BigDecimal)
case class Basket(offers: List[Offer], gds: List[Goods])
