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

  case class Value(amt: Int)

  case class Item(name: String, count: Value)

  implicit val valueFormat = jsonFormat1(Value)
  implicit val itemFormat = jsonFormat2(Item)

  val json = Item("mary", Value(2)).toJson

  val w = new BufferedWriter(new FileWriter("output.json"))
  w.write(json.toJson.prettyPrint)
  w.close()
}

//val json = Item("mary", Value(2)).toJson
