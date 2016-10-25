import akka.actor.{Actor, ActorRef, ActorSystem, Props}

class Barista extends Actor {
  def receive = {
    case CappuccinoRequest =>
      sender ! Bill(250)
      println("I have to prepare a cappuccino!")
    case EspressoRequest =>
      sender ! Bill(200)
      println("Let's prepare an espresso.")
    case ClosingTime => context.system.terminate()
  }
}

class Customer(caffeineSource: ActorRef) extends Actor {
  def receive = {
    case CaffeineWithdrawalWarning => caffeineSource ! EspressoRequest
    case Bill(cents) => println(s"I have to pay $cents cents, or else!")
  }
}

case class Bill(cents: Int)

case object ClosingTime

sealed trait CoffeeRequest

case object CappuccinoRequest extends CoffeeRequest

case object EspressoRequest extends CoffeeRequest

case object CaffeineWithdrawalWarning

object Barista extends App {
  val system = ActorSystem("Barista")
  val barista = system.actorOf(Props[Barista], "Barista")
  val customer = system.actorOf(Props(classOf[Customer], barista), "Customer")
  //  barista ! CappuccinoRequest
  //  barista ! EspressoRequest
  //  println("I ordered a cappuccino and an espresso")
  customer ! CaffeineWithdrawalWarning
  barista ! ClosingTime
  system.terminate()
}
