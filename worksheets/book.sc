type Title = String
type Author = String

import spray.json._
import total.Borrower

case class Book(title: Title, author: Author, borrower: Option[Borrower] = None)

object Book extends DefaultJsonProtocol with NullOptions {

  def getTitle(bk: Book): Title = bk.title

  def getAuthor(bk: Book): Author = bk.author

  def getBorrower(bk: Book): Option[Borrower] = bk.borrower

  def setBorrower(br: Option[Borrower], bk: Book): Book = bk.copy(borrower = br)

  def availableString(bk: Book): String = {
    getBorrower(bk) match {
      case None => "Available"
      case Some(br) => "Checked out to " +
        Borrower.getName(br)
    }
  }

  def bookToString(bk: Book): String =
    getTitle(bk) +
      " by " + getAuthor(bk) +
      "; " + availableString(bk)

  implicit val bookFormat: RootJsonFormat[Book] = jsonFormat3(Book.apply)

}

val br2: Borrower = Borrower("Borrower2", 2)
val bk1: Book = Book("Title1", "Author1", None)
val bk2: Book = Book.setBorrower(Some(br2), bk1)
val bk3: Book = Book("Title3", "Author3")

Book.getTitle(bk1)
Book.getAuthor(bk1)
Book.getBorrower(bk1)
Book.getBorrower(bk2)
Book.getBorrower(bk3)
Book.bookToString(bk1)
Book.bookToString(bk2)
