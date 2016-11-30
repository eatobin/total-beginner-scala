import java.io.{BufferedWriter, FileWriter}

import spray.json._

object TestIt extends App with DefaultJsonProtocol {

  case class Foo(name: String, x: String, z: Int)

  implicit val fooFormat = jsonFormat3(Foo.apply)
  val foo = Foo("xxx", "jj", 60)
  val w = new BufferedWriter(new FileWriter("output.json"))
  w.write(foo.toJson.prettyPrint)
  w.close()
}
