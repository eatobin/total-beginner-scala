import spray.json._

case class Value(amt: Int)

object Value extends DefaultJsonProtocol {
  implicit val valueFormat = jsonFormat1(Value.apply)
}

// import MyJsonProtocol._

val json = Value(2).toJson

println(json)
