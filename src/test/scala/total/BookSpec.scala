package total

import org.scalatest._

class BookSpec extends FlatSpec with Matchers {

  val br2: Borrower = Borrower.makeBorrower("Borrower2", 2)
  val bk1: Book = Book.makeBook("Title1", "Author1")
  //  val bk1 = Book("Title1", "Author1", Some(Borrower("Borrower1", 1)))
  //  val bk2 = Book("Title2", "Author2", None)
  //  val br2 = Some(Borrower("Borrower2", 2))
  val bk2: Book = Book.setBorrower(Some(br2), bk1)
  val bk3 = Book("Title2", "Author2", Some(Borrower("Borrower2", 2)))

  "A Book" should "create itself properly" in {
    Book.getTitle(bk1) should be("Title1")
    Book.getAuthor(bk1) should be("Author1")
    Book.getBorrower(bk2) should be(Some(br2))
    //    Book.getBorrower(bk2) should be(None)
  }

  //  it should "set a new Borrower" in {
  //    Book.setBorrower(br2, bk2) should be(bk3)
  //  }
  //
  //  it should "return a string \"Title1 by Author1; Checked out to Borrower1\"" in {
  //    Book.bookToString(bk1) should be("Title1 by Author1; Checked out to Borrower1")
  //  }
  //
  //  it should "return a string \"Title2 by Author2; Available\"" in {
  //    Book.bookToString(bk2) should be("Title2 by Author2; Available")
  //  }

}
