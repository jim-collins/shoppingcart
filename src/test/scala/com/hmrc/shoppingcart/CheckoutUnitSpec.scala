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
}
