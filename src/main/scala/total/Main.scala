package total

import java.io._
import java.nio.file.{Files, Paths}

import scala.concurrent.stm._
import scala.io.Source

object Main {

  val tvBorrowers: Ref[List[Borrower]] = Ref(List())
  val tvBooks: Ref[List[Book]] = Ref(List())

  val jsonBorrowersFileBefore = "borrowers-before.json"
  val jsonBooksFile = "books-before.json"
  val jsonBorrowersFileAfter = "borrowers-after.json"
  val jsonBorrowersFileBad = "bad-borrowers.json"
  val emptyFile = "empty.json"

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

      println("Now let's do some BAD stuff...\n")

      println("Add a borrower that already exists (total.Borrower('Jim', 3))")
      tvBorrowers.transform(Library.addBorrower(Borrower("Jim", 3), _))
      println("No change to Test Library:")
      println(Library.statusToString(tvBooks.get, tvBorrowers.get))

      println("Add a book that already exists (total.Book('War And Peace', 'Tolstoy', None))")
      tvBooks.transform(Library.addBook(Book("War And Peace", "Tolstoy", None), _))
      println("No change to Test Library:")
      println(Library.statusToString(tvBooks.get, tvBorrowers.get))

      println("Check out a valid book to an invalid person (checkOut('JoJo', 'War And Peace', borrowers))")
      tvBooks.transform(Library.checkOut("JoJo", "War And Peace", tvBorrowers.get, _))
      println("No change to Test Library:")
      println(Library.statusToString(tvBooks.get, tvBorrowers.get))

      println("Check out an invalid book to an valid person (checkOut('Sue', 'Not A total.Book', borrowers))")
      tvBooks.transform(Library.checkOut("Sue", "Not A total.Book", tvBorrowers.get, _))
      println("No change to Test Library:")
      println(Library.statusToString(tvBooks.get, tvBorrowers.get))

      println("Last - check in a book not checked out (checkIn('War And Peace'))")
      tvBooks.transform(Library.checkIn("War And Peace", _))
      println("No change to Test Library:")
      println(Library.statusToString(tvBooks.get, tvBorrowers.get))

      println("Okay... let's finish with some persistence. First clear the whole library:")
      newEmptyV(tvBooks, tvBorrowers)

      println("Lets read in a new library from \"borrowers-before.json\" and \"books-before.json\":")
      newV(tvBooks, tvBorrowers, jsonBorrowersFileBefore, jsonBooksFile)

      println("Add... a new borrower:")
      tvBorrowers.transform(Library.addBorrower(Borrower("BorrowerNew", 300), _))
      println(Library.statusToString(tvBooks.get, tvBorrowers.get))

      println("Save the revised borrowers to \"borrowers-after.json\"")
      val jsonBrsStr = Library.borrowersToJsonString(tvBorrowers.get)
      writeJsonStringToFile(jsonBrsStr, "borrowers-after.json")

      println("Clear the whole library again:")
      newEmptyV(tvBooks, tvBorrowers)

      println("Then read in the revised library from \"borrowers-after.json\" and \"books-before.json\":")
      newV(tvBooks, tvBorrowers, jsonBorrowersFileAfter, jsonBooksFile)

      println("Last... delete the file \"borrowers-after.yml\"")
      new File(jsonBorrowersFileAfter).delete()

      println("Then try to make a library using the deleted \"borrowers-after.json\":")
      newV(tvBooks, tvBorrowers, jsonBorrowersFileAfter, jsonBooksFile)

      println("And if we read in a file with mal-formed json content... like \"bad-borrowers.json\":")
      newV(tvBooks, tvBorrowers, jsonBorrowersFileBad, jsonBooksFile)

      println("Or how about reading in an empty file... \"empty.json\":")
      newV(tvBooks, tvBorrowers, emptyFile, jsonBooksFile)

      println("And... that's all...")
      println("Thanks - bye!\n")

    }

  }

  def newEmptyV(tvBooks: Ref[List[Book]], tvBorrowers: Ref[List[Borrower]]): Unit = {
    atomic { implicit txn =>
      tvBooks.set(List[Book]())
      tvBorrowers.set(List[Borrower]())
      println(Library.statusToString(tvBooks.get, tvBorrowers.get))
    }
  }

  def readFileIntoJsonString(fp: FilePath): Option[JsonString] = {
    if (Files.exists(Paths.get(fp))) {
      val bufferedSource = Source.fromFile(fp)
      val result = Source.fromFile(fp).getLines.mkString
      bufferedSource.close
      Some(result)
    } else None
  }

  def writeJsonStringToFile(js: JsonString, fp: FilePath): Unit = {
    val file = new File(fp)
    val bw = new BufferedWriter(new FileWriter(file))
    bw.write(js)
    bw.close()
  }

  def newV(tvBooks: Ref[List[Book]], tvBorrowers: Ref[List[Borrower]], brsfp: FilePath, bksfp: FilePath): Unit = {
    val jsonBrsStr = Main.readFileIntoJsonString(brsfp)
    val jsonBksStr = Main.readFileIntoJsonString(bksfp)
    val brs = Library.jsonStringToBorrowers(jsonBrsStr)
    val bks = Library.jsonStringToBooks(jsonBksStr)

    atomic { implicit txn =>
      tvBooks.set(bks)
      tvBorrowers.set(brs)
      println(Library.statusToString(tvBooks.get, tvBorrowers.get))
    }
  }

}
