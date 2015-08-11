package com.hmrc.shoppingcart

import org.specs2.mutable._

/**
 * Created by Jim on 10/08/2015.
 */
class CheckoutUnitSpec extends Specification {
  "A checkout" should {
    """correctly total a mixed basket of apples and oranges""" in {
      val checkout = new Checkout{}
      checkout.checkout(List(Apple,Apple,Orange,Apple)) must  beEqualTo(2.05)
    }
    """not charge for an empty basket""" in {
      val checkout = new Checkout{}
      checkout.checkout(List()) must beEqualTo(0)
    }
    """charge correct amount for 1 apple""" in {
      val checkout = new Checkout{}
      checkout.checkout(List(Apple)) must beEqualTo(0.60)
    }
    """charge correct amount for 1 orange""" in {
      val checkout = new Checkout{}
      checkout.checkout(List(Orange)) must beEqualTo(0.25)
    }
  }
  "A checkout with discounts" should {
    val appleDiscount = (x: Int) => x - (x / 2)
    val orangeDiscount = (x: Int) => x - (x / 3)
    val discounts = Map[Goods, Function1[Int, Int]](Apple -> appleDiscount, Orange -> orangeDiscount)
    """Only charge for 1 in 2 apples""" in {
      val checkout = new Checkout(discounts)
      checkout.checkout(List(Apple,Apple,Apple)) must  beEqualTo(1.2)
      checkout.checkout(List(Apple,Apple,Apple,Apple)) must  beEqualTo(1.2)
    }
    """Give 3 oranges for the price of 2""" in {
      val checkout = new Checkout(discounts)
      checkout.checkout(List(Orange,Orange,Orange)) must  beEqualTo(0.5)
      checkout.checkout(List(Orange,Orange,Orange,Orange)) must  beEqualTo(0.75)
      checkout.checkout(List(Orange,Orange,Orange,Orange,Orange)) must  beEqualTo(1.0)
      checkout.checkout(List(Orange,Orange,Orange,Orange,Orange,Orange)) must  beEqualTo(1.0)
    }
  }
}
