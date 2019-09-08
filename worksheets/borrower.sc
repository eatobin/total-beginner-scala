type Name = String
type MaxBooks = Int

import spray.json._

case class Borrower(name: Name, maxBooks: MaxBooks)

object Borrower extends DefaultJsonProtocol {

  def getName(br: Borrower): Name = br.name

  def setName(n: Name, br: Borrower): Borrower = br.copy(name = n)

  def getMaxBooks(br: Borrower): MaxBooks = br.maxBooks

  def setMaxBooks(m: MaxBooks, br: Borrower): Borrower = br.copy(maxBooks = m)

  def borrowerToString(br: Borrower): String =
    getName(br) + " (" + getMaxBooks(br).toString + " books)"

  implicit val borrowerFormat: RootJsonFormat[Borrower] = jsonFormat2(Borrower.apply)

}

val br1: Borrower = Borrower("Borrower1", 1)
Borrower.getName(br1)
Borrower.getMaxBooks(br1)
Borrower.setName("NewBorrower", br1)
Borrower.setMaxBooks(11, br1)
Borrower.borrowerToString(br1)
