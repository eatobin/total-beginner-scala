import scala.concurrent.stm._

object Cheat extends App {
  //  val x: Ref[Int] = Ref(0)
  //  val y: Ref[String] = Ref.make[String]()
  //  val z: Ref.View[Int] = x.single
  val eric: Ref.View[Int] = Ref(1956).single
  val karen: Ref[Int] = Ref(1961)

  //  atomic { implicit txn =>
  //    val i = x()
  //    y() = "x was " + i
  //    val eq = atomic { implicit txn =>
  //      x() == z()
  //    }
  //    assert(eq)
  //    y.set(y.get + ", long-form access")
  //  }
  //
  //  println("y was '" + y.single() + "'")
  //  println("z was " + z())

  //  atomic { implicit txn =>
  //    y() = y() + ", first alternative"
  //    if (x getWith {
  //      _ > 0
  //    })
  //      retry
  //  } orAtomic { implicit txn =>
  //    y() = y() + ", second alternative"
  //  }
  //
  //  val prev = z.swap(10)
  //  val success = z.compareAndSet(10, 11)
  //  val et = z.transform {
  //    _ max 20
  //  }
  //  val pre = y.single.getAndTransform {
  //    _.toUpperCase
  //  }
  //  val post = y.single.transformAndGet {
  //    _.filterNot {
  //      _ == ' '
  //    }
  //  }
  //
  //  println("x: " + x)
  //  println("y: " + y)
  //  println("z: " + z)
  //  println("prev: " + prev)
  //  println("success: " + success)
  //  println("pre: " + pre)
  //  println("post: " + post)
  //  println("et: " + et)

  //  def addLast(elem: Int) {
  //    atomic { implicit txn =>
  //      val p = header.prev()
  //      val newNode = new Node(elem, p, header)
  //      p.next() = newNode
  //      header.prev() = newNode
  //    }
  //  }

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

  printKaren()
  println(s"karen: ${karen.single.get + 10}")
}
