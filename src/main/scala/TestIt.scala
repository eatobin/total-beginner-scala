import java.io.{BufferedWriter, FileWriter}

import spray.json._

//
//object TestIt extends App with DefaultJsonProtocol with NullOptions{
//
//  case class Borrower(name: String, maxBooks: Int)
//  case class Book(title: String, author: String, borrower: Borrower)
//
//  implicit val bookFormat = jsonFormat3(Book)
//  implicit val borrowerFormat = jsonFormat2(Borrower)
//  val book = Book("Title1", "Author1", Borrower("Borrower1", 1))
//  val w = new BufferedWriter(new FileWriter("output.json"))
//  w.write(book.toJson.prettyPrint)
//  w.close()
//}


object TestIt extends App with DefaultJsonProtocol with NullOptions {

  case class Borrower(title: String, maxBooks: Int)

  case class Book(name: String, author: String, borrower: Borrower)

  implicit val joe = jsonFormat2(Borrower)
  implicit val ginger = jsonFormat3(Book)

  val json = Book("mary", "bary", Borrower("Title", 22)).toJson

  val w = new BufferedWriter(new FileWriter("output.json"))
  w.write(json.toJson.prettyPrint)
  w.close()
}

//val json = Item("mary", Value(2)).toJson
