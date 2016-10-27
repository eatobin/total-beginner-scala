// http://nbronson.github.io/scala-stm/index.html
// http://nbronson.github.io/scala-stm/api/0.7/index.html#package

import scala.concurrent.stm._

object Cheat extends App {
  val eric: Ref.View[Int] = Ref(1956).single
  val karen: Ref[Int] = Ref(1961)

  println(s"eric: ${eric.get + 1}")
  eric.set(2016)
  println("eric: " + eric.get)

  def printKaren(): Unit = {
    atomic { implicit txn =>
      println(s"karen: ${karen.get + 1}")
      karen.set(2016)
      println("karen: " + karen.get)
    }
  }

  def add30(i: Int): Int = i + 30

  printKaren()
  println(s"karen: ${karen.single.get + 10}")

  atomic { implicit txn =>
    karen.transform(i => i + 3)
  }

  println("karen1: " + karen.single.get)

  atomic { implicit txn =>
    karen.transform(add30)
    karen.transform(i => add30(i))
    println("karen2: " + karen.get)
  }
}
