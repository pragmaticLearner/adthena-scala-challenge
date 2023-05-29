import org.scalatest.*
import org.scalatest.funsuite.AnyFunSuite
import main.scala.PriceBasket

import java.io.ByteArrayOutputStream


class PriceBasketTest extends AnyFunSuite {
  val priceBasket = PriceBasket
  val emptyList: Array[String] = Array()

  test("Empty command line args should return 'No items to calculate in the basket.'") {
    val args: Array[String] = Array.empty[String]
    val expectedOutput = "No items to calculate in the basket."
    val outputStream = new java.io.ByteArrayOutputStream()
    Console.withOut(outputStream) {
      PriceBasket.main(args)
    }
    val actualOutput = outputStream.toString.stripLineEnd
    assert(actualOutput === expectedOutput)
  }

  test("Test given basic command. Should return 3 print statements") {
    val args: Array[String] = Array("Soup", "Bread", "Milk")
    val outputStream = new java.io.ByteArrayOutputStream()
    Console.withOut(outputStream) {
      PriceBasket.main(args)
    }
    val actualOutput = outputStream.toString.stripLineEnd
    assert(actualOutput.linesIterator.size === 3)
  }

  test("Test complex command for deals. Should return 4 print statements") {
    val args: Array[String] = Array("Soup", "Bread", "Bread", "Apples", "Soup")
    val outputStream = new java.io.ByteArrayOutputStream()
    Console.withOut(outputStream) {
      PriceBasket.main(args)
    }
    val actualOutput = outputStream.toString.stripLineEnd
    assert(actualOutput.linesIterator.size === 4)
  }

  test("No deals to be calculated - basket does not contain apples nor has a ratio of soup:bread as 2:1") {
    val args: Array[String] = Array("Soup", "Bread", "Milk", "Bread")
    val outputStream = new java.io.ByteArrayOutputStream()
    Console.withOut(outputStream) {
      PriceBasket.main(args)
    }
    val actualOutput = outputStream.toString.stripLineEnd
    assert(actualOutput.linesIterator.size === 3)
  }
}
