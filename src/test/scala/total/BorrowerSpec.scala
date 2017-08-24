package total

import org.scalatest.FlatSpec

class BorrowerSpec extends FlatSpec {

  val br1: Borrower = Borrower.makeBorrower("Borrower1", 1)

  "A Borrower of (\"Borrower1\", 1)" should "create itself properly" in {
    assert(Borrower.getName(br1) == "Borrower1")
    assert(Borrower.getMaxBooks(br1) == 1)
  }

  it should "set a new name and maxBooks" in {
    assert(Borrower.setName("Borrower1", Borrower("Jack", 1)) == br1)
    assert(Borrower.setMaxBooks(1, Borrower("Borrower1", 11)) == br1)
  }

  it should "return a string \"Borrower1 (1 books)\"" in {
    assert(Borrower.borrowerToString(br1) == "Borrower1 (1 books)")
  }

}
