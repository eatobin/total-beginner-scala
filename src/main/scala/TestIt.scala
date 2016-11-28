import java.io.{BufferedWriter, FileWriter}

import spray.json._

object TestIt extends App with DefaultJsonProtocol {

  case class Foo(name: String, x: String)

  implicit val fooFormat = jsonFormat2(Foo.apply)
  val foo = Foo("xxx", "jj")
  //  println(foo.toJson)
  val w = new BufferedWriter(new FileWriter("output.json"))
  w.write(foo.toJson.toString())
  w.close
}
