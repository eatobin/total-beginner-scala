package total

case class Book(title: Title, author: Author, borrower: Option[Borrower])

object Book {

  def getTitle(bk: Book): Title = bk.title

  def getAuthor(bk: Book): Author = bk.author

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

// :paste /home/eric/scala_projects/total-beginner-scala/src/main/scala/total/Book.scala
// val bk1 = total.Book("Title1", "Author1", Some(total.Borrower("Borrower1", 1)))
// val bk2 = total.Book("Title2", "Author2", None)
