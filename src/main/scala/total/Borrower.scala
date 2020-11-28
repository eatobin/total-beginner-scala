package total

import spray.json._

case class Borrower(name: String, maxBooks: Int)

object Borrower extends DefaultJsonProtocol {

  def getName(br: Borrower): String = br.name

  def setName(n: String, br: Borrower): Borrower = br.copy(name = n)

  def getMaxBooks(br: Borrower): Int = br.maxBooks

  def setMaxBooks(m: Int, br: Borrower): Borrower = br.copy(maxBooks = m)

  def borrowerToString(br: Borrower): String =
    getName(br) + " (" + getMaxBooks(br).toString + " books)"

  def borrowerJsonStringToBorrower(borrowerString: String): Borrower = borrowerString.parseJson.convertTo[Borrower]

  implicit val borrowerFormat: RootJsonFormat[Borrower] = jsonFormat2(Borrower.apply)

}
