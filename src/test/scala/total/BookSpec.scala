package total

import io.circe.Error
import org.scalatest.flatspec.AnyFlatSpec
import total.Book._

class BookSpec extends AnyFlatSpec {

  private val jsonStringBk1: String = "{\"title\":\"Title1\",\"author\":\"Author1\",\"borrower\":null}"
  private val jsonStringBk2: String = "{\"title\":\"Title1\",\"author\":\"Author1\",\"borrower\":{\"name\":\"Borrower2\",\"maxBooks\":2}}"
  private val br2: Borrower = Borrower("Borrower2", 2)
  private val bk1: Book = bookJsonStringToBook(jsonStringBk1).getOrElse(Book("", ""))
  private val bk2: Book = setBorrower(Some(br2), bk1)
  private val bk3: Book = Book("Title3", "Author3")

  "A Book" should "create itself properly" in {
    assert(getTitle(bk1) == "Title1")
    assert(getAuthor(bk1) == "Author1")
    assert(getBorrower(bk1).isEmpty)
    assert(getBorrower(bk2).contains(br2))
    assert(getBorrower(bk3).isEmpty)
  }

  it should "return a string \"Title1 by Author1; Available\"" in {
    assert(bookToString(bk1) == "Title1 by Author1; Available")
  }

  it should "return a string \"Title1 by Author1; Checked out to Borrower2\"" in {
    assert(bookToString(bk2) == "Title1 by Author1; Checked out to Borrower2")
  }

  it should "convert from JSON" in {
    val bkJson1: Either[Error, Book] = bookJsonStringToBook(jsonStringBk1)
    assert(bkJson1 == Right(bk1))
    val bkJson2: Either[Error, Book] = bookJsonStringToBook(jsonStringBk2)
    assert(bkJson2 == Right(bk2))
  }

}
