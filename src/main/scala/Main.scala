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
    }

  }

}
