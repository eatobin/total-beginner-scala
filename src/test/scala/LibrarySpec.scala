import org.scalatest._

class LibrarySpec extends FlatSpec with Matchers {

  val bk1 = Book("Title1", "Author1", Some(Borrower("Borrower1", 1)))
  val bk2 = Book("Title2", "Author2", None)
  val bk3 = Book("Title2", "Author2", Some(Borrower("Borrower2", 2)))
  val br1 = Borrower("Borrower1", 1)
  val br2 = Borrower("Borrower2", 2)
  val br3 = Borrower("Borrower3", 3)
  val brs1 = List(br1, br2)
  val brs2 = List(br1, br2, br3)


  "A Library" should "add a Borrower correctly" in {
    Library.addBorrower(br3, brs1) should be(Some(brs2))
    Library.addBorrower(br2, brs1) should be(None)
  }

  //  it should "set a new Borrower" in {
  //    Book.setBorrower(Some(br2), bk2) should be(bk3)
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
