package total

import spray.json._
import total.Book._
import total.Borrower._

object Library extends DefaultJsonProtocol with NullOptions {

  def addItem[A](x: A, xs: List[A]): List[A] = {
    if (xs.contains(x))
      xs
    else
      x :: xs
  }

  def removeBook(bk: Book, bks: Books): Books =
    bks.filter(_ != bk)

  def findItem[A](tgt: String, coll: List[A], f: A => String): Option[A] = {
    val result = coll.filter(item => f(item) == tgt)
    result.headOption
  }

  def getBooksForBorrower(br: Borrower, bks: Books): Books =
    bks.filter(bk => getBorrower(bk).contains(br))

  def numBooksOut(br: Borrower, bks: Books): Int =
    getBooksForBorrower(br, bks).length

  def notMaxedOut(br: Borrower, bks: Books): Boolean =
    numBooksOut(br, bks) < getMaxBooks(br)

  def bookNotOut(bk: Book): Boolean =
    getBorrower(bk).isEmpty

  def bookOut(bk: Book): Boolean =
    getBorrower(bk).isDefined

  def checkOut(n: Name, t: Title, brs: Borrowers, bks: Books): Books = {
    val mbk = findItem(t, bks, getTitle)
    val mbr = findItem(n, brs, getName)

    if (mbk.isDefined && mbr.isDefined && notMaxedOut(mbr.get, bks) && bookNotOut(mbk.get)) {
      val newBook = setBorrower(mbr, mbk.get)
      val fewerBooks = removeBook(mbk.get, bks)
      addItem(newBook, fewerBooks)
    } else bks
  }

  def checkIn(t: Title, bks: Books): Books = {
    val mbk = findItem(t, bks, getTitle)

    if (mbk.isDefined && bookOut(mbk.get)) {
      val newBook = setBorrower(None, mbk.get)
      val fewerBooks = removeBook(mbk.get, bks)
      addItem(newBook, fewerBooks)
    } else bks
  }

  def jsonStringToBorrowers(s: Either[ErrorString, JsonString]): Either[ErrorString, Borrowers] = {
    s match {
      case Right(r) =>
        try {
          Right(r.parseJson.convertTo[Borrowers])
        } catch {
          case _: Exception =>
            Left("JSON parse error.")
        }
      case Left(l) =>
        Left(l)
    }
  }

  def jsonStringToBooks(s: Either[ErrorString, JsonString]): Either[ErrorString, Books] = {
    s match {
      case Right(r) =>
        try {
          Right(r.parseJson.convertTo[Books])
        } catch {
          case _: Exception =>
            Left("JSON parse error.")
        }
      case Left(l) =>
        Left(l)
    }
  }

  def borrowersToJsonString(brs: Borrowers): JsonString =
    brs.toJson.sortedPrint

  def booksToJsonString(bks: Books): JsonString =
    bks.toJson.sortedPrint

  def libraryToString(bks: Books, brs: Borrowers): String =
    "Test Library: " +
      bks.length.toString + " books; " +
      brs.length.toString + " borrowers."

  def statusToString(bks: Books, brs: Borrowers): String =
    "\n--- Status Report of Test Library ---\n\n" +
      libraryToString(bks, brs) + "\n\n" +
      bks.map(bk => bookToString(bk)).mkString("\n") + "\n\n" +
      brs.map(br => borrowerToString(br)).mkString("\n") + "\n\n" +
      "--- End of Status Report ---\n"

}
