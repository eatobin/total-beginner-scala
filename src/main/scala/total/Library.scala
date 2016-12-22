package total

import spray.json._

object Library extends DefaultJsonProtocol with NullOptions {

  def addBorrower(br: Borrower, brs: Borrowers): Borrowers = {
    if (brs.contains(br))
      brs
    else
      br :: brs
  }

  def addBook(bk: Book, bks: Books): Books = {
    if (bks.contains(bk))
      bks
    else
      bk :: bks
  }

  def addItem[A](x: A, xs: List[A]): List[A] = {
    if (xs.contains(x))
      xs
    else
      x :: xs
  }

  def removeBook(bk: Book, bks: Books): Books = {
    if (bks.contains(bk))
      bks.filter(_ != bk)
    else
      bks
  }

  def findBook(t: Title, bks: Books): Option[Book] = {
    val coll = bks.filter(bk => Book.getTitle(bk) == t)
    coll.headOption
  }

  def findItem[A](tgt: String, coll: List[A], f: (A) => String): Option[A] = {
    val result = coll.filter(item => f(item) == tgt)
    result.headOption
  }

  def findBorrower(n: Name, brs: Borrowers): Option[Borrower] = {
    val coll = brs.filter(br => Borrower.getName(br) == n)
    coll.headOption
  }

  def getBooksForBorrower(br: Borrower, bks: Books): Books =
    bks.filter(bk => Book.getBorrower(bk).contains(br))

  def numBooksOut(br: Borrower, bks: Books): Int =
    getBooksForBorrower(br, bks).length

  def notMaxedOut(br: Borrower, bks: Books): Boolean =
    numBooksOut(br, bks) < Borrower.getMaxBooks(br)

  def bookNotOut(bk: Book): Boolean =
    Book.getBorrower(bk).isEmpty

  def bookOut(bk: Book): Boolean =
    Book.getBorrower(bk).isDefined

  def checkOut(n: Name, t: Title, brs: Borrowers, bks: Books): Books = {
    val mbk = findBook(t, bks)
    val mbr = findBorrower(n, brs)

    if (mbk.isDefined && mbr.isDefined && notMaxedOut(mbr.get, bks) && bookNotOut(mbk.get)) {
      val newBook = Book.setBorrower(mbr, mbk.get)
      val fewerBooks = removeBook(mbk.get, bks)
      addBook(newBook, fewerBooks)
    } else bks
  }

  def checkIn(t: Title, bks: Books): Books = {
    val mbk = findBook(t, bks)

    if (mbk.isDefined && bookOut(mbk.get)) {
      val newBook = Book.setBorrower(None, mbk.get)
      val fewerBooks = removeBook(mbk.get, bks)
      addBook(newBook, fewerBooks)
    } else bks
  }

  def jsonStringToBorrowers(s: Either[ErrorString, JsonString]): Either[ErrorString, Borrowers] = {
    s match {
      case Right(r) =>
        try {
          Right(r.parseJson.convertTo[Borrowers])
        } catch {
          case e: Exception =>
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
          case e: Exception =>
            Left("JSON parse error.")
        }
      case Left(l) =>
        Left(l)
    }
  }

  def borrowersToJsonString(brs: Borrowers): JsonString =
    brs.toJson.compactPrint

  def booksToJsonString(bks: Books): JsonString =
    bks.toJson.compactPrint

  def libraryToString(bks: Books, brs: Borrowers): String =
    "Test Library: " +
      bks.length.toString + " books; " +
      brs.length.toString + " borrowers."

  def statusToString(bks: Books, brs: Borrowers): String =
    "\n--- Status Report of Test Library ---\n\n" +
      libraryToString(bks, brs) + "\n\n" +
      bks.map(bk => Book.bookToString(bk)).mkString("\n") + "\n\n" +
      brs.map(br => Borrower.borrowerToString(br)).mkString("\n") + "\n\n" +
      "--- End of Status Report ---\n"

}

//val br1 = total.Borrower("Borrower1", 1)
//val br2 = total.Borrower("Borrower2", 2)
//val brs1 = List(br1,br2)
//val br3 = total.Borrower("Borrower3", 3)
//:paste /home/eric/scala_projects/total-beginner-scala/src/main/scala/total/Library.scala
