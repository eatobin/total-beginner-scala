package total

import spray.json._

case class Borrower(name: Name, maxBooks: MaxBooks)

object Borrower extends DefaultJsonProtocol {

  def makeBorrower(n: Name, mb: MaxBooks): Borrower = Borrower(name = n, maxBooks = mb)

  def getName(br: Borrower): Name = br.name

  def setName(n: Name, br: Borrower): Borrower = br.copy(name = n)

  def getMaxBooks(br: Borrower): MaxBooks = br.maxBooks

  def setMaxBooks(m: MaxBooks, br: Borrower): Borrower = br.copy(maxBooks = m)

  def borrowerToString(br: Borrower): String =
    getName(br) + " (" + getMaxBooks(br).toString + " books)"

  implicit val borrowerFormat: RootJsonFormat[Borrower] = jsonFormat2(Borrower.apply)

}
