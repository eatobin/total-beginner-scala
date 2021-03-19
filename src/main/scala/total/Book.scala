package total

import io.circe.Error
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax.EncoderOps

case class Book(title: String, author: String, borrower: Option[Borrower] = None)

object Book {

  def getTitle(bk: Book): String = bk.title

  def getAuthor(bk: Book): String = bk.author

  def getBorrower(bk: Book): Option[Borrower] = bk.borrower

  def setBorrower(br: Option[Borrower])(bk: Book): Book = bk.copy(borrower = br)

  private def availableString(bk: Book): String = {
    getBorrower(bk) match {
      case Some(br) => s"Checked out to ${Borrower.getName(br)}"
      case None => "Available"
    }
  }

  def toString(bk: Book): String =
    s"${getTitle(bk)} by ${getAuthor(bk)}; ${availableString(bk)}"

  def jsonStringToBook(bookString: String): Either[Error, Book] = decode[Book](bookString)

  def bookToJsonString(bk: Book): JsonString =
    bk.asJson.noSpaces
}
