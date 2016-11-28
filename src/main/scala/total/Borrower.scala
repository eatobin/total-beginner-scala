package total

case class Borrower(name: Name, maxBooks: MaxBooks)

object Borrower {

  def getName(br: Borrower): Name = br.name

  def setName(n: Name, br: Borrower): Borrower = br.copy(name = n)

  def getMaxBooks(br: Borrower): MaxBooks = br.maxBooks

  def setMaxBooks(m: MaxBooks, br: Borrower): Borrower = br.copy(maxBooks = m)

  def borrowerToString(br: Borrower): String =
    getName(br) + " (" + getMaxBooks(br).toString + " books)"

}

// :paste /home/eric/scala_projects/total-beginner-scala/src/main/scala/total/Borrower.scala
// val br1 = total.Borrower("Borrower1", 1)
