package total

import io.circe.Error
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

case class Borrower(name: String, maxBooks: Int)

object Borrower {

  def getName(br: Borrower): String = br.name

  def setName(n: String)(br: Borrower): Borrower = br.copy(name = n)

  def getMaxBooks(br: Borrower): Int = br.maxBooks

  def setMaxBooks(m: Int)(br: Borrower): Borrower = br.copy(maxBooks = m)

  def toString(br: Borrower): String =
    s"${getName(br)} (${getMaxBooks(br)} books)"

  def jsonStringToBorrower(borrowerString: String): Either[Error, Borrower] =
    decode[Borrower](borrowerString)

  def borrowerToJsonString(br: Borrower): JsonString =
    br.asJson.noSpaces

}
