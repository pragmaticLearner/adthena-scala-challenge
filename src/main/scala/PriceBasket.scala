package main.scala

import scala.math.BigDecimal.RoundingMode

object PriceBasket {
  val prices: Map[String, Double] = Map(
    "Soup" -> 0.65,
    "Bread" -> 0.80,
    "Milk" -> 1.30,
    "Apples" -> 1.00
  )

  def calculateTotalPrice(items: List[String]): Unit = {
    val itemCounts = items.map(_.capitalize).groupBy(identity).view.mapValues(_.size).toMap

    var costTotal: BigDecimal = itemCounts.collect {
      case (item, count) if prices.contains(item) => prices(item) * count
    }.sum
    println(s"Subtotal: £${"%.2f".format(costTotal)}")

    // Get apple discount
    var appleDiscount = BigDecimal(0.0)
    if (itemCounts.contains("Apples")) {
      itemCounts.get("Apples") match {
        case Some(value) =>
          appleDiscount = BigDecimal(value * 0.1).setScale(1, RoundingMode.HALF_UP)
        case None =>
      }
    }
    costTotal = costTotal - appleDiscount

    // Get bread discount
    val soupCount = itemCounts.getOrElse("Soup", 0)
    val breadCount = itemCounts.getOrElse("Bread", 0)

    costTotal = costTotal - (breadCount * prices.getOrElse("Bread", 0.0))

    val discountedCount = (soupCount / 2) min breadCount
    val remainingCount = (breadCount - discountedCount) max 0

    val totalBreadCost = remainingCount.toDouble * prices.getOrElse("Bread", 0.0) + discountedCount.toDouble * 0.5
    costTotal = costTotal + totalBreadCost - (0.1 * discountedCount)

    if (appleDiscount != 0.0) {
      println(s"Apples 10% off: £${"%.2f".format(appleDiscount)}")
    }
    if (discountedCount != 0.0) {
      println(s"Bread 50% off: £${"%.2f".format(breadCount * prices.getOrElse("Bread", 0.0) - totalBreadCost + (0.1 * discountedCount))}")
    }
    if (appleDiscount == 0.0 && discountedCount == 0.0) {
      println("(No offers available)")
    }

    println(s"Total price: £${"%.2f".format(costTotal)}")

  }

  def main(args: Array[String]): Unit = {
    if (args.isEmpty) {
      println("No items to calculate in the basket.")
    } else {
      calculateTotalPrice(args.toList)
    }
  }
}
