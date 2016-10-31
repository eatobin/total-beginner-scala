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

  def checkOut(n: String, t: String, brs: List[Borrower], bks: List[Book]): List[Book] = {
    lazy val mbk = Library.findBook(t, bks)
    lazy val mbr = Library.findBorrower(n, brs)
    lazy val booksOut = Library.getBooksForBorrower(mbr.get, bks).length
    lazy val maxBooksAllowed = Borrower.getMaxBooks(mbr.get)
    lazy val notMaxedOut = booksOut < maxBooksAllowed
    lazy val bookNotOut = Book.getBorrower(mbk.get).isEmpty
    lazy val newBook = Book.setBorrower(mbr, mbk.get)
    lazy val fewerBooks = Library.removeBook(mbk.get, bks)

    if (mbk.isDefined && mbr.isDefined && notMaxedOut && bookNotOut)
      Library.addBook(newBook, fewerBooks)
    else
      bks
  }

  def checkIn(t: String, bks: List[Book]): List[Book] = {
    lazy val mbk = Library.findBook(t, bks)
    lazy val bookOut = Book.getBorrower(mbk.get).isDefined
    lazy val newBook = Book.setBorrower(None, mbk.get)
    lazy val fewerBooks = Library.removeBook(mbk.get, bks)

    if (mbk.isDefined && bookOut)
      Library.addBook(newBook, fewerBooks)
    else
      bks
  }

  def libraryToString(bks: List[Book], brs: List[Borrower]): String =
    "Test Library: " +
      bks.length.toString + " books; " +
      brs.length.toString + " borrowers."

  def statusToString(bks: List[Book], brs: List[Borrower]): String =
    "\n--- Status Report of Test Library ---\n\n" +
      Library.libraryToString(bks, brs) + "\n\n" +
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
