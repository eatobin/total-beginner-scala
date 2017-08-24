package total

import org.scalatest.FlatSpec

class BookSpec extends FlatSpec {

  val br2: Borrower = Borrower.makeBorrower("Borrower2", 2)
  val bk1: Book = Book.makeBook("Title1", "Author1", None)
  val bk2: Book = Book.setBorrower(Some(br2), bk1)

  "A Book" should "create itself properly" in {
    assert(Book.getTitle(bk1) == "Title1")
    assert(Book.getAuthor(bk1) == "Author1")
    assert(Book.getBorrower(bk1).isEmpty)
    assert(Book.getBorrower(bk2).contains(br2))
  }

  it should "return a string \"Title1 by Author1; Checked out to Borrower1\"" in {
    assert(Book.bookToString(bk1) == "Title1 by Author1; Available")
  }

  it should "return a string \"Title1 by Author1; Checked out to Borrower2\"" in {
    assert(Book.bookToString(bk2) == "Title1 by Author1; Checked out to Borrower2")
  }

}
