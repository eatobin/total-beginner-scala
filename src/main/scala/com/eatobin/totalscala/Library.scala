package com.eatobin.totalscala

import com.eatobin.totalscala.Book._
import com.eatobin.totalscala.Borrower._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

//noinspection DuplicatedCode,DuplicatedCode
object Library {

  def addItem[A](x: A)(implicit xs: List[A]): List[A] = {
    if (xs.contains(x))
      xs
    else
      x :: xs
  }

  def removeBook(bk: Book)(bks: List[Book]): List[Book] =
    bks.filter(_ != bk)

  def removeBookImp(bk: Book)(implicit bks: List[Book]): List[Book] =
    bks.filter(_ != bk)

  def findItem[A](tgt: String)(coll: List[A])(f: A => String): Option[A] = {
    val result = coll.filter(item => f(item) == tgt)
    result.headOption
  }

  def getBooksForBorrower(br: Borrower, bks: List[Book]): List[Book] =
    bks.filter(bk => getBorrower(bk).contains(br))

  private def numBooksOut(br: Borrower, bks: List[Book]): Int =
    getBooksForBorrower(br, bks).length

  private def notMaxedOut(br: Borrower, bks: List[Book]): Boolean =
    numBooksOut(br, bks) < getMaxBooks(br)

  private def bookNotOut(bk: Book): Boolean =
    Book.getBorrower(bk).isEmpty

  private def bookOut(bk: Book): Boolean =
    Book.getBorrower(bk).isDefined

  def checkOut(n: String)(t: String)(brs: List[Borrower])(bks: List[Book]): List[Book] = {
    val mbk = findItem(t)(bks)(getTitle)
    val mbr = findItem(n)(brs)(getName)

    if (mbk.isDefined && mbr.isDefined && notMaxedOut(mbr.get, bks) && bookNotOut(mbk.get)) {
      val newBook = setBorrower(mbr, mbk.get)
      val fewerBooks = removeBook(mbk.get)(bks)
      addItem(newBook)(fewerBooks)
    } else bks
  }

  def checkIn(t: String)(bks: List[Book]): List[Book] = {
    val mbk = findItem(t)(bks)(getTitle)

    if (mbk.isDefined && bookOut(mbk.get)) {
      val newBook = setBorrower(None, mbk.get)
      val fewerBooks = removeBook(mbk.get)(bks)
      addItem(newBook)(fewerBooks)
    } else bks
  }

  def jsonStringToBorrowers(s: Either[ErrorString, JsonString]): Either[ErrorString, List[Borrower]] = {
    s match {
      case Right(r) =>
        val result = decode[List[Borrower]](r)
        result match {
          case Right(brs) => Right(brs)
          case Left(e) => Left(e.toString)
        }
      case Left(l) =>
        Left(l)
    }
  }

  def jsonStringToBooks(s: Either[ErrorString, JsonString]): Either[ErrorString, List[Book]] = {
    s match {
      case Right(r) =>
        val result = decode[List[Book]](r)
        result match {
          case Right(bks) => Right(bks)
          case Left(e) => Left(e.toString)
        }
      case Left(l) =>
        Left(l)
    }
  }

  def borrowersToJsonString(brs: List[Borrower]): JsonString =
    brs.asJson.noSpaces

  def booksToJsonString(bks: List[Book]): JsonString =
    bks.asJson.noSpaces

  def libraryHeader(brs: List[Borrower], bks: List[Book]) =
    s"Test Library: ${bks.length.toString} books; ${brs.length.toString} borrowers."

  def libraryToString(bks: List[Book], brs: List[Borrower]): String =
    "\n--- Status Report of Test Library ---\n\n" +
      libraryHeader(brs, bks) + "\n\n" +
      bks.map(bk => Book.bookToString(bk)).mkString("\n") + "\n\n" +
      brs.map(br => Borrower.borrowerToString(br)).mkString("\n") + "\n\n" +
      "--- End of Status Report ---\n"

}
