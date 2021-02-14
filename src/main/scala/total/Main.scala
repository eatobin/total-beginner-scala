package total

import total.Library._

import scala.io.Source

//noinspection DuplicatedCode,DuplicatedCode
object Main {

  var borrowers: List[Borrower] = List.empty
  var books: List[Book] = List.empty

  val jsonBorrowersFileBefore = "src/main/resources/borrowers-before.json"
  val jsonBooksFile = "src/main/resources/books-before.json"
  val jsonBorrowersFileAfter = "src/main/resources/borrowers-after.json"
  val jsonBorrowersFileBad = "src/main/resources/bad-borrowers.json"
  val emptyFile = "src/main/resources/resources/empty.json"

  def main(args: Array[String]): Unit = {

    borrowers = Library.addItem(Borrower("Jim", 3), borrowers)
    borrowers = Library.addItem(Borrower("Sue", 3), borrowers)
    books = Library.addItem(Book("War And Peace", "Tolstoy", None), books)
    books = Library.addItem(Book("Great Expectations", "Dickens", None), books)
    println("\nJust created new library")
    println(statusToString(books, borrowers))

    println("Check out War And Peace to Sue")
    books = Library.checkOut("Sue", "War And Peace", borrowers, books)
    println(statusToString(books, borrowers))

    println("Now check in War And Peace from Sue...")
    books = Library.checkIn("War And Peace", books)
    println("...and check out Great Expectations to Jim")
    books = Library.checkOut("Jim", "Great Expectations", borrowers, books)
    println(statusToString(books, borrowers))

    println("Add Eric and The Cat In The Hat")
    borrowers = Library.addItem(Borrower("Eric", 1), borrowers)
    books = Library.addItem(Book("The Cat In The Hat", "Dr. Seuss", None), books)
    println("Check Out Dr. Seuss to Eric")
    books = Library.checkOut("Eric", "The Cat In The Hat", borrowers, books)
    println(statusToString(books, borrowers))

    println("Now let's do some BAD stuff...\n")

    println("Add a borrower that already exists (total.Borrower('Jim', 3))")
    borrowers = Library.addItem(Borrower("Jim", 3), borrowers)
    println("No change to Test Library:")
    println(statusToString(books, borrowers))

    println("Add a book that already exists (Book('War And Peace', 'Tolstoy', None))")
    books = Library.addItem(Book("War And Peace", "Tolstoy", None), books)
    println("No change to Test Library:")
    println(statusToString(books, borrowers))

    println("Check out a valid book to an invalid person (checkOut('JoJo', 'War And Peace', borrowers))")
    books = Library.checkOut("JoJo", "War And Peace", borrowers, books)
    println("No change to Test Library:")
    println(statusToString(books, borrowers))

    println("Check out an invalid book to an valid person (checkOut('Sue', 'Not A total.Book', borrowers))")
    books = Library.checkOut("Sue", "Not A total.Book", borrowers, books)
    println("No change to Test Library:")
    println(statusToString(books, borrowers))

    println("Last - check in a book not checked out (checkIn('War And Peace'))")
    books = Library.checkIn("War And Peace", books)
    println("No change to Test Library:")
    println(statusToString(books, borrowers))

    println("Okay... let's finish with some persistence. First clear the whole library:")
    newEmpty()

    //    println("Lets read in a new library from \"borrowers-before.json\" and \"books-before.json\":")
    //    newV(books, borrowers, jsonBorrowersFileBefore, jsonBooksFile)
    //    println("Add... a new borrower:")
    //    borrowers = Library.addItem(Borrower("BorrowerNew", 300), _))
    //    println(statusToString(books, borrowers))
    //
    //    println("Save the revised borrowers to \"borrowers-after.json\"")
    //    val jsonBrsStr = borrowersToJsonString(borrowers)
    //    writeJsonStringToFile(jsonBrsStr)
    //
    //    println("Clear the whole library again:")
    //    newEmptyV(books, borrowers)
    //
    //    println("Then read in the revised library from \"borrowers-after.json\" and \"books-before.json\":")
    //    newV(books, borrowers, jsonBorrowersFileAfter, jsonBooksFile)
    //
    //    println("Last... delete the file \"borrowers-after.json\"")
    //    new File(jsonBorrowersFileAfter).delete()
    //    newEmptyV(books, borrowers)
    //
    //    println("Then try to make a library using the deleted \"borrowers-after.json\" and \"books-before.json\":")
    //    newV(books, borrowers, jsonBorrowersFileAfter, jsonBooksFile)
    //
    //    println("And if we read in a file with mal-formed json content... like \"bad-borrowers.json\" and \"books-before.json\":")
    //    newV(books, borrowers, jsonBorrowersFileBad, jsonBooksFile)
    //
    //    println("Or how about reading in an empty file... \"empty.json\" (for borrowers and books):")
    //    newV(books, borrowers, emptyFile, emptyFile)
    //
    //    println("And... that's all...")
    //    println("Thanks - bye!\n")

  }

  def newEmpty(): Unit = {
    books = List.empty
    borrowers = List.empty
    println(statusToString(books, borrowers))
  }


  def readFileIntoJsonString(fp: String): Either[ErrorString, JsonString] =
    try {
      val bufferedSource = Source.fromFile(fp)
      val js = bufferedSource.getLines().mkString
      bufferedSource.close
      Right(js)
    } catch {
      case e: Exception =>
        Left(e.getMessage)
    }

  //
  //  def writeJsonStringToFile(js: JsonString): Unit = {
  //    val file = new File("src/main/resources/borrowers-after.json")
  //    val bw = new BufferedWriter(new FileWriter(file))
  //    bw.write(js)
  //    bw.close()
  //  }
  //
  //  def newV(tvBooks: Ref[List[Book]], tvBorrowers: Ref[List[Borrower]], brsfp: String, bksfp: String): Unit = {
  //    val jsonBrsStr: Either[ErrorString, JsonString] = Main.readFileIntoJsonString(brsfp)
  //    val jsonBksStr: Either[ErrorString, JsonString] = Main.readFileIntoJsonString(bksfp)
  //    val brs = jsonStringToBorrowers(jsonBrsStr)
  //    val bks = jsonStringToBooks(jsonBksStr)
  //
  //    atomic { implicit txn =>
  //      brs match {
  //        case Right(r) =>
  //          tvBorrowers.set(r)
  //        case Left(l) =>
  //          println(l)
  //      }
  //      bks match {
  //        case Right(r) =>
  //          tvBooks.set(r)
  //        case Left(l) =>
  //          println(l)
  //      }
  //      println(statusToString(tvBooks.get, tvBorrowers.get))
  //    }
  //  }

}
