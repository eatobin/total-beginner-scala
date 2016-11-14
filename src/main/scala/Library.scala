object Library {

  def addBorrower(br: Borrower, brs: List[Borrower]): List[Borrower] = {
    if (brs.contains(br))
      brs
    else
      br :: brs
  }

  def addBook(bk: Book, bks: List[Book]): List[Book] = {
    if (bks.contains(bk))
      bks
    else
      bk :: bks
  }

  def removeBook(bk: Book, bks: List[Book]): List[Book] = {
    if (bks.contains(bk))
      bks.filter(_ != bk)
    else
      bks
  }

  def findBook(t: String, bks: List[Book]): Option[Book] = {
    val coll = bks.filter(bk => Book.getTitle(bk) == t)
    coll.headOption
  }

  def findBorrower(n: String, brs: List[Borrower]): Option[Borrower] = {
    val coll = brs.filter(br => Borrower.getName(br) == n)
    coll.headOption
  }

  def getBooksForBorrower(br: Borrower, bks: List[Book]): List[Book] =
    bks.filter(bk => Book.getBorrower(bk).contains(br))

  def numBooksOut(br: Borrower, bks: List[Book]): Int =
    getBooksForBorrower(br, bks).length

  def notMaxedOut(br: Borrower, bks: List[Book]): Boolean =
    numBooksOut(br, bks) < Borrower.getMaxBooks(br)

  def bookNotOut(bk: Book): Boolean =
    Book.getBorrower(bk).isEmpty

  def bookOut(bk: Book): Boolean =
    Book.getBorrower(bk).isDefined

  def checkOut(n: String, t: String, brs: List[Borrower], bks: List[Book]): List[Book] = {
    val mbk = findBook(t, bks)
    val mbr = findBorrower(n, brs)

    if (mbk.isDefined && mbr.isDefined && notMaxedOut(mbr.get, bks) && bookNotOut(mbk.get)) {
      val newBook = Book.setBorrower(mbr, mbk.get)
      val fewerBooks = removeBook(mbk.get, bks)
      addBook(newBook, fewerBooks)
    } else bks
  }

  def checkIn(t: String, bks: List[Book]): List[Book] = {
    val mbk = findBook(t, bks)

    if (mbk.isDefined && bookOut(mbk.get)) {
      val newBook = Book.setBorrower(None, mbk.get)
      val fewerBooks = removeBook(mbk.get, bks)
      addBook(newBook, fewerBooks)
    } else bks
  }

  def libraryToString(bks: List[Book], brs: List[Borrower]): String =
    "Test Library: " +
      bks.length.toString + " books; " +
      brs.length.toString + " borrowers."

  def statusToString(bks: List[Book], brs: List[Borrower]): String =
    "\n--- Status Report of Test Library ---\n\n" +
      libraryToString(bks, brs) + "\n\n" +
      bks.map(bk => Book.bookToString(bk)).mkString("\n") + "\n\n" +
      brs.map(br => Borrower.borrowerToString(br)).mkString("\n") + "\n\n" +
      "--- End of Status Report ---\n"

}

//val br1 = Borrower("Borrower1", 1)
//val br2 = Borrower("Borrower2", 2)
//val brs1 = List(br1,br2)
//val br3 = Borrower("Borrower3", 3)
//:paste /home/eric/scala_projects/total-beginner-scala/src/main/scala/Library.scala
//:paste /Users/eatobin/scala_projects/total-beginner-scala/src/main/scala/Library.scala
