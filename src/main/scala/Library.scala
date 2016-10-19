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

}

//val br1 = Borrower("Borrower1", 1)
//val br2 = Borrower("Borrower2", 2)
//val brs1 = List(br1,br2)
//val br3 = Borrower("Borrower3", 3)
//:paste /home/eric/scala_projects/total-beginner-scala/src/main/scala/Library.scala
