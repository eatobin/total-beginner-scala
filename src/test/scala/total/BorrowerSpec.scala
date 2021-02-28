package total

import org.scalatest.flatspec.AnyFlatSpec
import total.Borrower._

class BorrowerSpec extends AnyFlatSpec {

  private val jsonStringBr: String = "{\"name\":\"Borrower1\",\"maxBooks\":1}"
  private val br1: Borrower = borrowerJsonStringToBorrower(jsonStringBr).getOrElse(Borrower("", 0))

  "A Borrower" should "create itself properly" in {
    assert(getName(br1) == "Borrower1")
    assert(br1.name == "Borrower1")
    assert(getMaxBooks(br1) == 1)
    assert(br1.maxBooks == 1)
  }

  it should "set a new name and maxBooks" in {
    assert(setName("Borrower1", Borrower("Jack", 1)) == br1)
    assert(setMaxBooks(1, Borrower("Borrower1", 11)) == br1)
  }

  it should "return a string \"Borrower1 (1 books)\"" in {
    assert(borrowerToString(br1) == "Borrower1 (1 books)")
  }

  it should "turn a Borrower into a JSON string" in {
    assert(borrowerToJsonString(br1) == jsonStringBr)
  }

}
