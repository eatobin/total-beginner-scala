package total

import org.scalatest._

class BorrowerSpec extends FlatSpec with Matchers {

  val br1: Borrower = Borrower.makeBorrower("Borrower1", 1)

  "A Borrower" should "create itself properly" in {
    Borrower.getName(br1) should be("Borrower1")
    Borrower.getMaxBooks(br1) should be(1)
  }

  it should "set a new name and maxBooks" in {
    Borrower.setName("Borrower1", Borrower("Jack", 1)) should be(br1)
    Borrower.setMaxBooks(1, Borrower("Borrower1", 11)) should be(br1)
  }

  it should "return a string \"Borrower1 (1 books)\"" in {
    Borrower.borrowerToString(br1) should be("Borrower1 (1 books)")
  }

}
