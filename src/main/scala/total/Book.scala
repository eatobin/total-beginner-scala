package total

import spray.json._

case class Book(title: Title, author: Author, borrower: Option[Borrower])

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
    Book.getTitle(bk) +
      " by " + getAuthor(bk) +
      "; " + availableString(bk)

  implicit val bookFormat: RootJsonFormat[Book] = jsonFormat3(Book.apply)

}
