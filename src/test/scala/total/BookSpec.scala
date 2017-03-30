package total

import org.scalatest._

class BookSpec extends FlatSpec with Matchers {

  val br2: Borrower = Borrower.makeBorrower("Borrower2", 2)
  val bk1: Book = Book.makeBook("Title1", "Author1")
  val bk2: Book = Book.setBorrower(Some(br2), bk1)

  "A Book" should "create itself properly" in {
    Book.getTitle(bk1) should be("Title1")
    Book.getAuthor(bk1) should be("Author1")
    Book.getBorrower(bk1) should be(None)
    Book.getBorrower(bk2) should be(Some(br2))
  }

  it should "return a string \"Title1 by Author1; Checked out to Borrower1\"" in {
    Book.bookToString(bk1) should be("Title1 by Author1; Available")
  }

  it should "return a string \"Title1 by Author1; Checked out to Borrower2\"" in {
    Book.bookToString(bk2) should be("Title1 by Author1; Checked out to Borrower2")
  }

}
