package com.hmrc.shoppingcart

/**
 * Created by Jim on 10/08/2015.
 */
trait Checkout {
  def checkout(good: List[Goods]): BigDecimal
}
