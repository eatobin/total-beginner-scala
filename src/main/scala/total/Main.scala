package total

import java.io._

import total.Library._

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

      tvBorrowers.transform(addItem(Borrower("Jim", 3), _))
      tvBorrowers.transform(addItem(Borrower("Sue", 3), _))
      tvBooks.transform(addItem(Book("War And Peace", "Tolstoy", None), _))
      tvBooks.transform(addItem(Book("Great Expectations", "Dickens", None), _))
      println("\nJust created new library")
      println(statusToString(tvBooks.get, tvBorrowers.get))

      println("Check out War And Peace to Sue")
      tvBooks.transform(checkOut("Sue", "War And Peace", tvBorrowers.get, _))
      println(statusToString(tvBooks.get, tvBorrowers.get))

      println("Now check in War And Peace from Sue...")
      tvBooks.transform(checkIn("War And Peace", _))
      println("...and check out Great Expectations to Jim")
      tvBooks.transform(checkOut("Jim", "Great Expectations", tvBorrowers.get, _))
      println(statusToString(tvBooks.get, tvBorrowers.get))

      println("Add Eric and The Cat In The Hat")
      tvBorrowers.transform(addItem(Borrower("Eric", 1), _))
      tvBooks.transform(addItem(Book("The Cat In The Hat", "Dr. Seuss", None), _))
      println("Check Out Dr. Seuss to Eric")
      tvBooks.transform(checkOut("Eric", "The Cat In The Hat", tvBorrowers.get, _))
      println(statusToString(tvBooks.get, tvBorrowers.get))

      println("Now let's do some BAD stuff...\n")

      println("Add a borrower that already exists (total.Borrower('Jim', 3))")
      tvBorrowers.transform(addItem(Borrower("Jim", 3), _))
      println("No change to Test Library:")
      println(statusToString(tvBooks.get, tvBorrowers.get))

      println("Add a book that already exists (total.Book('War And Peace', 'Tolstoy', None))")
      tvBooks.transform(addItem(Book("War And Peace", "Tolstoy", None), _))
      println("No change to Test Library:")
      println(statusToString(tvBooks.get, tvBorrowers.get))

      println("Check out a valid book to an invalid person (checkOut('JoJo', 'War And Peace', borrowers))")
      tvBooks.transform(checkOut("JoJo", "War And Peace", tvBorrowers.get, _))
      println("No change to Test Library:")
      println(statusToString(tvBooks.get, tvBorrowers.get))

      println("Check out an invalid book to an valid person (checkOut('Sue', 'Not A total.Book', borrowers))")
      tvBooks.transform(checkOut("Sue", "Not A total.Book", tvBorrowers.get, _))
      println("No change to Test Library:")
      println(statusToString(tvBooks.get, tvBorrowers.get))

      println("Last - check in a book not checked out (checkIn('War And Peace'))")
      tvBooks.transform(checkIn("War And Peace", _))
      println("No change to Test Library:")
      println(statusToString(tvBooks.get, tvBorrowers.get))

      println("Okay... let's finish with some persistence. First clear the whole library:")
      newEmptyV(tvBooks, tvBorrowers)

      println("Lets read in a new library from \"borrowers-before.json\" and \"books-before.json\":")
      newV(tvBooks, tvBorrowers, jsonBorrowersFileBefore, jsonBooksFile)
      println("Add... a new borrower:")
      tvBorrowers.transform(addItem(Borrower("BorrowerNew", 300), _))
      println(statusToString(tvBooks.get, tvBorrowers.get))

      println("Save the revised borrowers to \"borrowers-after.json\"")
      val jsonBrsStr = borrowersToJsonString(tvBorrowers.get)
      writeJsonStringToFile(jsonBrsStr, "borrowers-after.json")

      println("Clear the whole library again:")
      newEmptyV(tvBooks, tvBorrowers)

      println("Then read in the revised library from \"borrowers-after.json\" and \"books-before.json\":")
      newV(tvBooks, tvBorrowers, jsonBorrowersFileAfter, jsonBooksFile)

      println("Last... delete the file \"borrowers-after.json\"")
      new File(jsonBorrowersFileAfter).delete()
      newEmptyV(tvBooks, tvBorrowers)

      println("Then try to make a library using the deleted \"borrowers-after.json\" and \"borrowers-after.json\":")
      newV(tvBooks, tvBorrowers, jsonBorrowersFileAfter, jsonBorrowersFileAfter)

      println("And if we read in a file with mal-formed json content... like \"bad-borrowers.json\" and \"borrowers-after.json\":")
      newV(tvBooks, tvBorrowers, jsonBorrowersFileBad, jsonBorrowersFileAfter)

      println("Or how about reading in an empty file... \"empty.json\" (for borrowers and books):")
      newV(tvBooks, tvBorrowers, emptyFile, emptyFile)

      println("And... that's all...")
      println("Thanks - bye!\n")

    }

  }

  def newEmptyV(tvBooks: Ref[List[Book]], tvBorrowers: Ref[List[Borrower]]): Unit = {
    atomic { implicit txn =>
      tvBooks.set(List[Book]())
      tvBorrowers.set(List[Borrower]())
      println(statusToString(tvBooks.get, tvBorrowers.get))
    }
  }

  def readFileIntoJsonString(fp: FilePath): Either[ErrorString, JsonString] =
    try {
      val bufferedSource = Source.fromFile(fp)
      val js = bufferedSource.getLines.mkString
      bufferedSource.close
      Right(js)
    } catch {
      case _: Exception => Left("File read error.")
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
    val brs = jsonStringToBorrowers(jsonBrsStr)
    val bks = jsonStringToBooks(jsonBksStr)

    atomic { implicit txn =>
      brs match {
        case Right(r) =>
          tvBorrowers.set(r)
        case Left(l) =>
          println(l)
      }
      bks match {
        case Right(r) =>
          tvBooks.set(r)
        case Left(l) =>
          println(l)
      }
      println(statusToString(tvBooks.get, tvBorrowers.get))
    }
  }

}
