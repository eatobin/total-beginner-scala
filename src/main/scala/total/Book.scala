package total

import spray.json._

case class Book(title: String, author: String, borrower: Option[Borrower] = None)

object Book extends DefaultJsonProtocol with NullOptions {

  def getTitle(bk: Book): String = bk.title

  def getAuthor(bk: Book): String = bk.author

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

  def bookJsonStringToBorrower(bookString: String): Book = bookString.parseJson.convertTo[Book]

  implicit val bookFormat: RootJsonFormat[Book] = jsonFormat3(Book.apply)

}
