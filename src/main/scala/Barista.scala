// http://danielwestheide.com/blog/2013/02/27/the-neophytes-guide-to-scala-part-14-the-actor-approach-to-concurrency.html

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

class Barista extends Actor {
  var cappuccinoCount = 0
  var espressoCount = 0

  def receive = {
    case CappuccinoRequest =>
      sender ! Bill(250)
      cappuccinoCount += 1
      println(s"I have to prepare a cappuccino #$cappuccinoCount")
    case EspressoRequest =>
      sender ! Bill(200)
      espressoCount += 1
      println(s"Let's prepare an espresso #$espressoCount")
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
  //    barista ! CappuccinoRequest
  //    barista ! EspressoRequest
  //    println("I ordered a cappuccino and an espresso")
  customer ! CaffeineWithdrawalWarning
  customer ! CaffeineWithdrawalWarning
  barista ! ClosingTime
  //    system.terminate()
}
