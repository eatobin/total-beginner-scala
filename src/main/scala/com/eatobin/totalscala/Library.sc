sealed trait IntOrBool

case class I(i: Int) extends IntOrBool

case class B(b: Boolean) extends IntOrBool

val i = I(99)

val b = B(true)

sealed trait MixedType

case class Tup(x: Int, y: Int) extends MixedType

case class Person(first: String, last: String) extends MixedType

val myTup = Tup(2, 99)

val myP = Person(first = "Al", last = "Jones")

def matcher(x: MixedType): Unit = x match {
  case Tup(x, y) => println(s"Tuple matched with $x $y")
  case Person(f, l) => println(s"Person matched with $f $l")
}

matcher(myTup)

matcher(myP)

case class Borrower(name: String, maxBooks: Int)

sealed trait Book

case class BookIn(title: String, author: String) extends Book

case class BookOut(title: String, author: String, borrower: Borrower) extends Book

val br = Borrower(name = "Borrower2", maxBooks = 2)
val bk1 = BookIn(title = "Title1", author = "Author1")
val bk2 = BookOut(title = "Title2", author = "Author2", borrower = br)

println(br)
println(bk1)
println(bk2)
