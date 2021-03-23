package total

import io.circe.Error
import org.scalatest.flatspec.AnyFlatSpec

class BorrowerSpec extends AnyFlatSpec {

  private val jsonStringBr: JsonString = "{\"name\":\"Borrower1\",\"maxBooks\":1}"
  private val wonkyBr: JsonString = "{\"wonky\":\"Borrower1\",\"maxBooks\":1}"
  private val br1: Either[Error, Borrower] = Borrower.jsonStringToBorrower(jsonStringBr)
  private val brWonky: Either[Error, Borrower] = Borrower.jsonStringToBorrower(wonkyBr)

  "A Borrower" should "create itself properly - br1" in {
    br1 match {
      case Left(e) => assert(e.toString == "")
      case Right(br) => assert(Borrower.getName(br) == "Borrower1")
    }


    //      assert(Borrower.getName(br1) == "Borrower1")
    //      assert(br1.name == "Borrower1")
    //      assert(Borrower.getMaxBooks(br1) == 1)
    //      assert(br1.maxBooks == 1)
  }
  //
  //  it should "set a new name and maxBooks" in {
  //    assert(Borrower.setName("Borrower1")(Borrower("Jack", 1)) == br1)
  //    assert(Borrower.setMaxBooks(1)(Borrower("Borrower1", 11)) == br1)
  //  }
  //
  //  it should "return a string \"Borrower1 (1 books)\"" in {
  //    assert(Borrower.toString(br1) == "Borrower1 (1 books)")
  //  }
  //
  //  it should "turn a Borrower into a JSON string" in {
  //    assert(Borrower.borrowerToJsonString(br1) == jsonStringBr)
  //  }

}
