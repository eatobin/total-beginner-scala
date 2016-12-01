//import java.io.{BufferedWriter, FileWriter}

import spray.json._

//object TestIt extends App with DefaultJsonProtocol with NullOptions {
//
//  case class Borrower(name: String, maxBooks: Int)
//
//  case class Book(title: String, author: String, borrower: Option[Borrower])
//
//  implicit val borrowerFormat = jsonFormat2(Borrower)
//  implicit val bookFormat = jsonFormat3(Book)
//
//  val book = Book("Title1", "Author1", Some(Borrower("Borrower1", 1))).toJson
//  //  val book = Book("Title1", "Author1", None).toJson
//
//  val w = new BufferedWriter(new FileWriter("output.txt"))
//  w.write(book.toJson.toString)
//  w.close()
//
//}

case class Value(amt: Int)

object Value

case class Item(name: String, count: Value)

object Item

object MyJsonProtocol extends DefaultJsonProtocol {
  implicit val valueFormat = jsonFormat1(Value.apply)
  implicit val itemFormat = jsonFormat2(Item.apply)
}

import MyJsonProtocol._

val json = Item("mary", Value(2)).toJson

println(json)
