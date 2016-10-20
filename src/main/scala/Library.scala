object Library {

  def addBorrower(br: Borrower, brs: List[Borrower]): Option[List[Borrower]] = {
    val coll = brs.filter(_ == br)
    if (coll.isEmpty)
      Some(brs :+ br)
    else
      None
  }

  def addBook(bk: Book, bks: List[Book]): Option[List[Book]] = {
    val coll = bks.filter(_ == bk)
    if (coll.isEmpty)
      Some(bks :+ bk)
    else
      None
  }

  def removeBook(bk: Book, bks: List[Book]): Option[List[Book]] = {
    val coll = bks.filter(_ == bk)
    if (coll.contains(bk))
      Some(bks.filter(_ != bk))
    else
      None
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
    lazy val fewerBooks = Library.removeBook(mbk.get, bks).get

    if (mbk.isDefined && mbr.isDefined && notMaxedOut && bookNotOut)
      Library.addBook(newBook, fewerBooks).get
    else
      bks
  }

}

//val br1 = Borrower("Borrower1", 1)
//val br2 = Borrower("Borrower2", 2)
//val brs1 = List(br1,br2)
//val br3 = Borrower("Borrower3", 3)
//:paste /home/eric/scala_projects/total-beginner-scala/src/main/scala/Library.scala
