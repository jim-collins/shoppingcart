package com.hmrc.shoppingcart

/**
 * Created by Jim on 10/08/2015.
 */
sealed trait Goods

case object Apple extends Goods

case object Orange extends Goods

case object Pear extends Goods
case object Bananna extends Goods
