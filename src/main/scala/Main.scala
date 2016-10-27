import scala.concurrent.stm._

object Main {

  val tvBorrowers: Ref[List[Borrower]] = Ref(List())
  val tvBooks: Ref[List[Book]] = Ref(List())

  def main(args: Array[String]): Unit = {

    atomic { implicit txn =>
      tvBorrowers.transform(Library.addBorrower(Borrower("Jim", 3), _))
      tvBorrowers.transform(Library.addBorrower(Borrower("Sue", 3), _))
      tvBooks.transform(Library.addBook(Book("War And Peace", "Tolstoy", None), _))
      tvBooks.transform(Library.addBook(Book("Great Expectations", "Dickens", None), _))
      println("\nJust created new library")
      println(Library.statusToString(tvBooks.get, tvBorrowers.get))

      println("Check out War And Peace to Sue")
      tvBooks.transform(Library.checkOut("Sue", "War And Peace", tvBorrowers.get, _))
      println(Library.statusToString(tvBooks.get, tvBorrowers.get))

      println("Now check in War And Peace from Sue...")
      tvBooks.transform(Library.checkIn("War And Peace", _))
      println("...and check out Great Expectations to Jim")
      tvBooks.transform(Library.checkOut("Jim", "Great Expectations", tvBorrowers.get, _))
      println(Library.statusToString(tvBooks.get, tvBorrowers.get))

      println("Add Eric and The Cat In The Hat")
      tvBorrowers.transform(Library.addBorrower(Borrower("Eric", 1), _))
      tvBooks.transform(Library.addBook(Book("The Cat In The Hat", "Dr. Seuss", None), _))
      println("Check Out Dr. Seuss to Eric")
      tvBooks.transform(Library.checkOut("Eric", "The Cat In The Hat", tvBorrowers.get, _))
      println(Library.statusToString(tvBooks.get, tvBorrowers.get))

      println("Now let's do some BAD stuff...")

      println("Add a borrower that already exists (Borrower('Jim', 3))")
      tvBorrowers.transform(Library.addBorrower(Borrower("Jim", 3), _))
      println("No change to Test Library:")
      println(Library.statusToString(tvBooks.get, tvBorrowers.get))

      println("Add a book that already exists (Book('War And Peace', 'Tolstoy', None))")
      tvBooks.transform(Library.addBook(Book("War And Peace", "Tolstoy", None), _))
      println("No change to Test Library:")
      println(Library.statusToString(tvBooks.get, tvBorrowers.get))

      println("Check out a valid book to an invalid person (checkOut('JoJo', 'War And Peace', borrowers))")
      tvBooks.transform(Library.checkOut("JoJo", "War And Peace", tvBorrowers.get, _))
      println("No change to Test Library:")
      println(Library.statusToString(tvBooks.get, tvBorrowers.get))
    }

  }

}
