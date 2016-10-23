object Main {

  def main(args: Array[String]): Unit = {

    val tvBorrowers: List[Borrower] = List()
    val tvBooks: List[Book] = List()

    Library.addBorrower(Borrower("Jim", 3), tvBorrowers)
    Library.addBorrower(Borrower("Sue", 3), tvBorrowers)
    Library.addBook(Book("War And Peace", "Tolstoy", None), tvBooks)
    Library.addBook(Book("Great Expectations", "Dickens", None), tvBooks)
    println("\nJust created new library")
  }
}
