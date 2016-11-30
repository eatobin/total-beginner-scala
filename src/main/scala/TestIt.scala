import java.io.{BufferedWriter, FileWriter}

import spray.json._

object TestIt extends App with DefaultJsonProtocol with NullOptions {

  case class Borrower(name: String, maxBooks: Int)

  case class Book(title: String, author: String, borrower: Borrower)

  implicit val borrowerFormat = jsonFormat2(Borrower)
  implicit val bookFormat = jsonFormat3(Book)

  val book = Book("Title1", "Author1", Borrower("Borrower1", 1)).toJson

  val w = new BufferedWriter(new FileWriter("output.json"))
  w.write(book.toJson.prettyPrint)
  w.close()

}

