package total

import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class LibrarySpec extends FlatSpec {

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

  val jsonStringBorrowers = "[{\"name\":\"Borrower1\",\"maxBooks\":1},{\"name\":\"Borrower2\",\"maxBooks\":2}]"
  val jsonStringBooks = "[{\"title\":\"Title2\",\"author\":\"Author22\",\"borrower\":null},{\"title\":\"Title99\",\"author\":\"Author99\",\"borrower\":null}]"
  val jsonStringBorrowersBad = "[{\"nameX\":\"Borrower1\",\"maxBooks\":1},{\"name\":\"Borrower2\",\"maxBooks\":2}]"
  val jsonStringBooksBad = "[{\"titleX\":\"Title2\",\"author\":\"Author22\",\"borrower\":null},{\"title\":\"Title99\",\"author\":\"Author99\",\"borrower\":null}]"

  val ss = "\n--- Status Report of Test Library ---\n\nTest Library: 3 books; 3 borrowers.\n\nTitle3 by Author3; Checked out to Borrower3\nTitle1 by Author1; Checked out to Borrower1\nTitle2 by Author2; Available\n\nBorrower3 (3 books)\nBorrower1 (1 books)\nBorrower2 (2 books)\n\n--- End of Status Report ---\n"

  "A Library" should "add a Borrower or Book correctly" in {
    assert(Library.addItem(br3, brs1) == brs2)
    assert(Library.addItem(br2, brs1) == brs1)

    assert(Library.addItem(bk3, bks1) == bks2)
    assert(Library.addItem(bk2, bks1) == bks1)
  }

  it should "remove a Book correctly" in {
    assert(Library.removeBook(bk3, bks2) == bks1)
    assert(Library.removeBook(bk3, bks1) == bks1)
  }

  it should "find a Book or Borrower correctly" in {
    assert(Library.findItem("Title1", bks2, Book.getTitle).contains(bk1))
    assert(Library.findItem("Title11", bks2, Book.getTitle).isEmpty)

    assert(Library.findItem("Borrower1", brs2, Borrower.getName).contains(br1))
    assert(Library.findItem("Borrower11", brs2, Borrower.getName).isEmpty)
  }

  it should "find Books for a Borrower" in {
    assert(Library.getBooksForBorrower(br2, bks1) == List())
    assert(Library.getBooksForBorrower(br1, bks1) == List(bk1))
    assert(Library.getBooksForBorrower(br3, bks3) == List(bk3, bk4))
  }

  it should "check out a Book correctly" in {
    assert(Library.checkOut("Borrower2", "Title1", brs1, bks1) == bks1)
    assert(Library.checkOut("Borrower2", "NoTitle", brs1, bks1) == bks1)
    assert(Library.checkOut("NoName", "Title1", brs1, bks1) == bks1)
    assert(Library.checkOut("Borrower1", "Title2", brs1, bks1) == bks1)
    assert(Library.checkOut("Borrower2", "Title2", brs1, bks1) ==
      List(Book("Title2", "Author2", Some(Borrower("Borrower2", 2))), bk1))
  }

  it should "check in a Book correctly" in {
    assert(Library.checkIn("Title1", bks1) == List(Book("Title1", "Author1", None), bk2))
    assert(Library.checkIn("Title2", bks1) == bks1)
    assert(Library.checkIn("NoTitle", bks1) == bks1)
  }

  it should "parse json strings to objects" in {
    assert(Library.jsonStringToBorrowers(Right(jsonStringBorrowers)) == Right(List(Borrower("Borrower1", 1), Borrower("Borrower2", 2))))
    assert(Library.jsonStringToBooks(Right(jsonStringBooks)) == Right(List(Book("Title2", "Author22", None), Book("Title99", "Author99", None))))
  }

  it should "report json parse errors" in {
    assert(Library.jsonStringToBorrowers(Right(jsonStringBorrowersBad)) == Left("JSON parse error."))
    assert(Library.jsonStringToBooks(Right(jsonStringBooksBad)) == Left("JSON parse error."))
  }

  it should "report read file errors" in {
    val s1 = Main.readFileIntoJsonString("noFile.json")
    assert(Library.jsonStringToBorrowers(s1) == Left("File read error."))
    val s2 = Main.readFileIntoJsonString("empty.json")
    assert(Library.jsonStringToBorrowers(s2) == Right(List()))
  }

  it should "convert objects to json strings" in {
    assert(Library.borrowersToJsonString(List(Borrower("Borrower1", 1), Borrower("Borrower2", 2))) == jsonStringBorrowers)
    assert(Library.booksToJsonString(List(Book("Title2", "Author22", None), Book("Title99", "Author99", None))) == jsonStringBooks)
  }

  it should "print out a Status report" in {
    assert(Library.statusToString(bks2, brs2) == ss)
  }

}
