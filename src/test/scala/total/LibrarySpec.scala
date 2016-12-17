package total

import org.scalatest._

class LibrarySpec extends FlatSpec with Matchers {

  val br1 = Borrower("Borrower1", 1)
  val br2 = Borrower("Borrower2", 2)
  val br3 = Borrower("Borrower3", 3)

  val brs1 = List(br1, br2)
  val brs2 = List(br3, br1, br2)

  val bk1 = Book("Title1", "Author1", Some(br1))
  val bk2 = Book("Title2", "Author2", None)
  val bk3 = Book("Title3", "Author3", Some(br3))
  val bk4 = Book("Title4", "Author4", Some(Borrower("Borrower3", 3)))

  val bks1 = List(bk1, bk2)
  val bks2 = List(bk3, bk1, bk2)
  val bks3 = List(bk1, bk2, bk3, bk4)

  val ss = "\n--- Status Report of Test Library ---\n\nTest Library: 3 books; 3 borrowers.\n\nTitle3 by Author3; Checked out to Borrower3\nTitle1 by Author1; Checked out to Borrower1\nTitle2 by Author2; Available\n\nBorrower3 (3 books)\nBorrower1 (1 books)\nBorrower2 (2 books)\n\n--- End of Status Report ---\n"


  "A Library" should "add a total.Borrower correctly" in {
    Library.addBorrower(br3, brs1) should be(brs2)
    Library.addBorrower(br2, brs1) should be(brs1)
  }

  it should "add a total.Book correctly" in {
    Library.addBook(bk3, bks1) should be(bks2)
    Library.addBook(bk2, bks1) should be(bks1)
  }

  it should "remove a total.Book correctly" in {
    Library.removeBook(bk3, bks2) should be(bks1)
    Library.removeBook(bk3, bks1) should be(bks1)
  }

  it should "find a total.Book correctly" in {
    Library.findBook("Title1", bks2) should be(Some(bk1))
    Library.findBook("Title11", bks2) should be(None)
  }

  it should "find a total.Borrower correctly" in {
    Library.findBorrower("Borrower1", brs2) should be(Some(br1))
    Library.findBorrower("Borrower11", brs2) should be(None)
  }

  it should "find Books for a total.Borrower" in {
    Library.getBooksForBorrower(br2, bks1) should be(List())
    Library.getBooksForBorrower(br1, bks1) should be(List(bk1))
    Library.getBooksForBorrower(br3, bks3) should be(List(bk3, bk4))
  }

  it should "check out a total.Book correctly" in {
    Library.checkOut("Borrower2", "Title1", brs1, bks1) should be(bks1)
    Library.checkOut("Borrower2", "NoTitle", brs1, bks1) should be(bks1)
    Library.checkOut("NoName", "Title1", brs1, bks1) should be(bks1)
    Library.checkOut("Borrower1", "Title2", brs1, bks1) should be(bks1)
    Library.checkOut("Borrower2", "Title2", brs1, bks1) should
      be(List(Book("Title2", "Author2", Some(Borrower("Borrower2", 2))), bk1))
  }

  it should "check in a total.Book correctly" in {
    Library.checkIn("Title1", bks1) should
      be(List(Book("Title1", "Author1", None), bk2))
    Library.checkIn("Title2", bks1) should
      be(bks1)
    Library.checkIn("NoTitle", bks1) should
      be(bks1)
  }

  it should "parse json strings to objects" in {
    Library.jsonStringToBorrowers(Some("[{\"name\":\"Borrower1\",\"maxBooks\":1},{\"name\":\"Borrower2\",\"maxBooks\":2}]")) should
      be(Some(List(Borrower("Borrower1", 1), Borrower("Borrower2", 2))))
    Library.jsonStringToBooks(Some("[{\"title\":\"Title2\",\"author\":\"Author22\",\"borrower\":null},{\"title\":\"Title99\",\"author\":\"Author99\",\"borrower\":null}]")) should
      be(Some(List(Book("Title2", "Author22", None), Book("Title99", "Author99", None))))
  }

  it should "convert objects to json strings" in {
    Library.borrowersToJsonString(List(Borrower("Borrower1", 1), Borrower("Borrower2", 2))) should
      be("[{\"name\":\"Borrower1\",\"maxBooks\":1},{\"name\":\"Borrower2\",\"maxBooks\":2}]")
    Library.booksToJsonString(List(Book("Title2", "Author22", None), Book("Title99", "Author99", None))) should
      be("[{\"title\":\"Title2\",\"author\":\"Author22\",\"borrower\":null},{\"title\":\"Title99\",\"author\":\"Author99\",\"borrower\":null}]")
  }

  it should "print out a Status report" in {
    Library.statusToString(bks2, brs2) should be(ss)
  }

}
