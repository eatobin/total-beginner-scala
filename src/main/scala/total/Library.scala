package total

import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._
import total.Book._
import total.Borrower._

object Library {

  def addItem[A](x: A, xs: List[A]): List[A] = {
    if (xs.contains(x))
      xs
    else
      x :: xs
  }

  def removeBook(bk: Book, bks: List[Book]): List[Book] =
    bks.filter(_ != bk)

  def findItem[A](tgt: String, coll: List[A], f: A => String): Option[A] = {
    val result = coll.filter(item => f(item) == tgt)
    result.headOption
  }

  def getBooksForBorrower(br: Borrower, bks: List[Book]): List[Book] =
    bks.filter(bk => getBorrower(bk).contains(br))

  def numBooksOut(br: Borrower, bks: List[Book]): Int =
    getBooksForBorrower(br, bks).length

  def notMaxedOut(br: Borrower, bks: List[Book]): Boolean =
    numBooksOut(br, bks) < getMaxBooks(br)

  def bookNotOut(bk: Book): Boolean =
    getBorrower(bk).isEmpty

  def bookOut(bk: Book): Boolean =
    getBorrower(bk).isDefined

  def checkOut(n: String, t: String, brs: List[Borrower], bks: List[Book]): List[Book] = {
    val mbk = findItem(t, bks, getTitle)
    val mbr = findItem(n, brs, getName)

    if (mbk.isDefined && mbr.isDefined && notMaxedOut(mbr.get, bks) && bookNotOut(mbk.get)) {
      val newBook = setBorrower(mbr, mbk.get)
      val fewerBooks = removeBook(mbk.get, bks)
      addItem(newBook, fewerBooks)
    } else bks
  }

  def checkIn(t: String, bks: List[Book]): List[Book] = {
    val mbk = findItem(t, bks, getTitle)

    if (mbk.isDefined && bookOut(mbk.get)) {
      val newBook = setBorrower(None, mbk.get)
      val fewerBooks = removeBook(mbk.get, bks)
      addItem(newBook, fewerBooks)
    } else bks
  }

  def jsonStringToBorrowers(s: Either[Error, JsonString]): Either[Error, List[Borrower]] = {
    s match {
      case Right(r) =>
        decode[List[Borrower]](r)
      case Left(l) =>
        Left(l)
    }
  }

  def jsonStringToBooks(s: Either[Error, JsonString]): Either[Error, List[Book]] = {
    s match {
      case Right(r) =>
        decode[List[Book]](r)
      case Left(l) =>
        Left(l)
    }
  }

  def borrowersToJsonString(brs: List[Borrower]): JsonString =
    brs.asJson.noSpaces

  def booksToJsonString(bks: List[Book]): JsonString =
    bks.asJson.noSpaces

  def libraryToString(bks: List[Book], brs: List[Borrower]): String =
    "Test Library: " +
      bks.length.toString + " books; " +
      brs.length.toString + " borrowers."

  def statusToString(bks: List[Book], brs: List[Borrower]): String =
    "\n--- Status Report of Test Library ---\n\n" +
      libraryToString(bks, brs) + "\n\n" +
      bks.map(bk => bookToString(bk)).mkString("\n") + "\n\n" +
      brs.map(br => borrowerToString(br)).mkString("\n") + "\n\n" +
      "--- End of Status Report ---\n"

}
