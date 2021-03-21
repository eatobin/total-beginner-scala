package total

import org.scalatest.flatspec.AnyFlatSpec

class LibrarySpec extends AnyFlatSpec {

  val br1: Borrower = Borrower("Borrower1", 1)
  val br2: Borrower = Borrower("Borrower2", 2)
  val br3: Borrower = Borrower("Borrower3", 3)

  implicit val brs1: List[Borrower] = List(br1, br2)
  val brs2: List[Borrower] = List(br3, br1, br2)

  val bk1: Book = Book("Title1", "Author1", Some(br1))
  val bk2: Book = Book("Title2", "Author2", None)
  val bk3: Book = Book("Title3", "Author3", Some(br3))
  val bk4: Book = Book("Title4", "Author4", Some(Borrower("Borrower3", 3)))

  implicit val bks1: List[Book] = List(bk1, bk2)
  val bks2: List[Book] = List(bk3, bk1, bk2)
  val bks3: List[Book] = List(bk1, bk2, bk3, bk4)

  val jsonStringBorrowers: JsonString = "[{\n  \"maxBooks\": 1,\n  \"name\": \"Borrower1\"\n}, {\n  \"maxBooks\": 2,\n  \"name\": \"Borrower2\"\n}]"
  val jsonStringBooks: JsonString = "[{\n  \"author\": \"Author22\",\n  \"borrower\": null,\n  \"title\": \"Title2\"\n}, {\n  \"author\": \"Author99\",\n  \"borrower\": null,\n  \"title\": \"Title99\"\n}]"
  val jsonStringBooksBorrower: JsonString = "[{\n  \"author\": \"Author22\",\n  \"borrower\": {\n  \"maxBooks\": 1,\n  \"name\": \"Borrower1\"\n},\n  \"title\": \"Title2\"\n}, {\n  \"author\": \"Author99\",\n  \"borrower\": null,\n  \"title\": \"Title99\"\n}]"
  val jsonStringBorrowersBad: JsonString = "[{\"nameX\":\"Borrower1\",\"maxBooks\":1},{\"name\":\"Borrower2\",\"maxBooks\":2}]"
  val jsonStringBooksBad: JsonString = "[{\"titleX\":\"Title2\",\"author\":\"Author22\",\"borrower\":null},{\"title\":\"Title99\",\"author\":\"Author99\",\"borrower\":null}]"
  val jsonStringBorrowersBad2: JsonString = "[{\"name\"\"Borrower1\",\"max-books\":1},{\"name\":\"Borrower2\",\"max-books\":2}]"

  val ss = "\n--- Status Report of Test Library ---\n\nTest Library: 3 books; 3 borrowers.\n\nTitle3 by Author3; Checked out to Borrower3\nTitle1 by Author1; Checked out to Borrower1\nTitle2 by Author2; Available\n\nBorrower3 (3 books)\nBorrower1 (1 books)\nBorrower2 (2 books)\n\n--- End of Status Report ---\n"

  "A Library" should "add a Borrower or Book correctly" in {
    assert(Library.addItem(br3)(brs1) == brs2)
    assert(Library.addItem(br2) == brs1)

    assert(Library.addItem(bk3)(bks1) == bks2)
    assert(Library.addItem(bk2) == bks1)
  }

  it should "remove a Book correctly (and implicitly)" in {
    assert(Library.removeBook(bk3)(bks2) == bks1)
    assert(Library.removeBook(bk3)(bks1) == bks1)
    assert(Library.removeBookImp(bk3) == bks1)
    assert(Library.removeBookImp(bk3)(bks1) == bks1)
  }

  it should "find a Book or Borrower correctly" in {
    assert(Library.findItem("Title1")(bks2)(Book.getTitle).contains(bk1))
    assert(Library.findItem("Title11")(bks2)(Book.getTitle).isEmpty)

    assert(Library.findItem("Borrower1")(brs2)(Borrower.getName).contains(br1))
    assert(Library.findItem("Borrower11")(brs2)(Borrower.getName).isEmpty)
  }

  it should "find List[Book] for a Borrower" in {
    assert(Library.getBooksForBorrower(br2)(bks1) == List())
    assert(Library.getBooksForBorrower(br1)(bks1) == List(bk1))
    assert(Library.getBooksForBorrower(br3)(bks3) == List(bk3, bk4))
  }

  it should "check out a Book correctly" in {
    assert(Library.checkOut("Borrower2")("Title1")(brs1)(bks1) == bks1)
    assert(Library.checkOut("Borrower2")("NoTitle")(brs1)(bks1) == bks1)
    assert(Library.checkOut("NoName")("Title1")(brs1)(bks1) == bks1)
    assert(Library.checkOut("Borrower1")("Title2")(brs1)(bks1) == bks1)
    assert(Library.checkOut("Borrower2")("Title2")(brs1)(bks1) ==
      List(Book("Title2", "Author2", Some(Borrower("Borrower2", 2))), bk1))
  }

  it should "check in a Book correctly" in {
    assert(Library.checkIn("Title1")(bks1) == List(Book("Title1", "Author1", None), bk2))
    assert(Library.checkIn("Title2")(bks1) == bks1)
    assert(Library.checkIn("NoTitle")(bks1) == bks1)
  }

  it should "parse json strings to objects" in {
    assert(Library.jsonStringToBorrowers(Right(jsonStringBorrowers)) ==
      Right(List(Borrower("Borrower1", 1), Borrower("Borrower2", 2))))
    assert(Library.jsonStringToBooks(Right(jsonStringBooks)) ==
      Right(List(Book("Title2", "Author22", None), Book("Title99", "Author99", None))))
    assert(Library.jsonStringToBooks(Right(jsonStringBooksBorrower)) ==
      Right(List(Book("Title2", "Author22", Some(Borrower("Borrower1", 1))), Book("Title99", "Author99", None))))
  }

  it should "report json parse errors" in {
    assert(Library.jsonStringToBorrowers(Right(jsonStringBorrowersBad)) ==
      Left("DecodingFailure(Attempt to decode value on failed cursor, List(DownField(name), DownArray))"))
    assert(Library.jsonStringToBorrowers(Right(jsonStringBorrowersBad2)) ==
      Left("""io.circe.ParsingFailure: expected : got '"Borro...' (line 1, column 9)"""))
    assert(Library.jsonStringToBooks(Right(jsonStringBooksBad)) ==
      Left("DecodingFailure(Attempt to decode value on failed cursor, List(DownField(title), DownArray))"))
  }

  it should "report read file errors" in {
    val s1 = Total.readFileIntoJsonString("noFile.json")
    assert(Library.jsonStringToBorrowers(s1) == Left("noFile.json (No such file or directory)"))
    val s2 = Total.readFileIntoJsonString("src/main/resources/empty.json")
    assert(Library.jsonStringToBorrowers(s2) == Right(List()))
  }

  it should "convert objects to json strings" in {
    assert(Library.borrowersToJsonString(List(Borrower("Borrower1", 1), Borrower("Borrower2", 2))) ==
      """[{"name":"Borrower1","maxBooks":1},{"name":"Borrower2","maxBooks":2}]""")
    assert(Library.booksToJsonString(List(Book("Title2", "Author22", None), Book("Title99", "Author99", None))) ==
      """[{"title":"Title2","author":"Author22","borrower":null},{"title":"Title99","author":"Author99","borrower":null}]""")
  }

  it should "print out a Status report" in {
    assert(Library.toString(bks2, brs2) == ss)
  }

}
