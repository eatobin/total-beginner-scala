case class Borrower(name: String, maxBooks: Int)

object Borrower {

  def getName(br: Borrower): String = br.name

  def setName(n: String, br: Borrower): Borrower = br.copy(name = n)

  def getMaxBooks(br: Borrower): Int = br.maxBooks

  def setMaxBooks(m: Int, br: Borrower): Borrower = br.copy(maxBooks = m)

  def borrowerToString(br: Borrower): String =
    getName(br) ++ " (" ++ getMaxBooks(br).toString ++ " books)"

}

// :paste /Users/eatobin/scala_projects/total-beginner-scala/src/main/scala/Borrower.scala
// val br1 = Borrower("Borrower1", 1)
