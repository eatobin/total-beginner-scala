object Main {

  import akka.actor.{Actor, ActorSystem, Props}

  class HelloActor extends Actor {
    var s = "String!!"

    def receive = {
      case "hello" => println("hello back at you")
      case "this..." => println("...is great!")
      case "and 2" =>
        val t = 2 + 6
        println(t)
      case "s" =>
        s = s ++ " 2!!"
        println(s)
      case _ => println("huh?")
    }
  }

  def main(args: Array[String]): Unit = {
    //
    //    val tvBorrowers: List[Borrower] = List()
    //    val tvBooks: List[Book] = List()
    //
    //    Library.addBorrower(Borrower("Jim", 3), tvBorrowers)
    //    Library.addBorrower(Borrower("Sue", 3), tvBorrowers)
    //    Library.addBook(Book("War And Peace", "Tolstoy", None), tvBooks)
    //    Library.addBook(Book("Great Expectations", "Dickens", None), tvBooks)
    //    println("\nJust created new library")

    // an actor needs an ActorSystem
    val system = ActorSystem("HelloSystem")
    // create and start the actor
    val helloActor = system.actorOf(Props[HelloActor], name = "helloactor")
    // send the actor two messages
    helloActor ! "hello"
    helloActor ! "buenos dias"
    helloActor ! "this..."
    helloActor ! "this"
    helloActor ! "and 2"
    helloActor ! "s"
    // shut down the system
    system.terminate()
  }
}
