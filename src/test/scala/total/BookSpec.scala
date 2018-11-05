package total

import org.scalatest.FlatSpec
import total.Book._

class BookSpec extends FlatSpec {

  val br2: Borrower = Borrower("Borrower2", 2)
  val bk1: Book = Book("Title1", "Author1", None)
  val bk2: Book = setBorrower(Some(br2), bk1)

  "A Book" should "create itself properly" in {
    assert(getTitle(bk1) == "Title1")
    assert(getAuthor(bk1) == "Author1")
    assert(getBorrower(bk1).isEmpty)
    assert(getBorrower(bk2).contains(br2))
  }

  it should "return a string \"Title1 by Author1; Checked out to Borrower1\"" in {
    assert(bookToString(bk1) == "Title1 by Author1; Available")
  }

  it should "return a string \"Title1 by Author1; Checked out to Borrower2\"" in {
    assert(bookToString(bk2) == "Title1 by Author1; Checked out to Borrower2")
  }

}
