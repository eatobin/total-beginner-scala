case class Book(title: String, author: String, borrower: Option[Borrower])

object Book {

  def getTitle(bk: Book): String = bk.title

  def getAuthor(bk: Book): String = bk.author

  def getBorrower(bk: Book): Option[Borrower] = bk.borrower

  def setBorrower(br: Option[Borrower], bk: Book): Book = bk.copy(borrower = br)

  def availableString(bk: Book): String = {
    getBorrower(bk) match {
      case None => "Available"
      case Some(br) => "Checked out to " + Borrower.getName(br)
    }
  }

  def bookToString(bk: Book): String =
    Book.getTitle(bk) +
      " by " + getAuthor(bk) +
      "; " + availableString(bk)

}

// :paste /home/eric/scala_projects/total-beginner-scala/src/main/scala/Book.scala
// :paste /Users/eatobin/scala_projects/total-beginner-scala/src/main/scala/Book.scala
// val bk1 = Book("Title1", "Author1", Some(Borrower("Borrower1", 1)))
// val bk2 = Book("Title2", "Author2", None)
