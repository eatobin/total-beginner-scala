package total

import io.circe.Error
import org.scalatest.flatspec.AnyFlatSpec

class BookSpec extends AnyFlatSpec {

  private val jsonStringBk1: JsonString = "{\"title\":\"Title1\",\"author\":\"Author1\",\"borrower\":null}"
  private val jsonStringBk2: JsonString = "{\"title\":\"Title1\",\"author\":\"Author1\",\"borrower\":{\"name\":\"Borrower2\",\"maxBooks\":2}}"
  private val br2: Borrower = Borrower("Borrower2", 2)
  private val bk1: Book = Book.jsonStringToBook(jsonStringBk1).getOrElse(Book("", ""))
  private val bk2: Book = Book.setBorrower(Some(br2))(bk1)
  private val bk3: Book = Book("Title3", "Author3")

  "A Book" should "create itself properly" in {
    assert(Book.getTitle(bk1) == "Title1")
    assert(Book.getAuthor(bk1) == "Author1")
    assert(Book.getBorrower(bk1).isEmpty)
    assert(Book.getBorrower(bk2).contains(br2))
    assert(Book.getBorrower(bk3).isEmpty)
  }

  it should "return a string \"Title1 by Author1; Available\"" in {
    assert(Book.toString(bk1) == "Title1 by Author1; Available")
  }

  it should "return a string \"Title1 by Author1; Checked out to Borrower2\"" in {
    assert(Book.toString(bk2) == "Title1 by Author1; Checked out to Borrower2")
  }

  it should "convert from JSON" in {
    val bkJson1: Either[Error, Book] = Book.jsonStringToBook(jsonStringBk1)
    assert(bkJson1 == Right(bk1))
    val bkJson2: Either[Error, Book] = Book.jsonStringToBook(jsonStringBk2)
    assert(bkJson2 == Right(bk2))
  }

  it should "turn a Book into a JSON string" in {
    assert(Book.bookToJsonString(bk1) == jsonStringBk1)
  }

}
