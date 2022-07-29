package com.eatobin.totalscala

import io.circe.Error
import org.scalatest.flatspec.AnyFlatSpec
import com.eatobin.totalscala.Borrower._

class BorrowerSpec extends AnyFlatSpec {

  private val jsonStringBr: JsonString = "{\"name\":\"Borrower1\",\"maxBooks\":1}"
  private val wonkyBr: JsonString = "{\"wonky\":\"Borrower1\",\"maxBooks\":1}"
  private val br1: Either[Error, Borrower] = jsonStringToBorrower(jsonStringBr)
  private val brWonky: Either[Error, Borrower] = jsonStringToBorrower(wonkyBr)

  "A Borrower" should "create itself properly - br1" in {
    br1 match {
      case Left(e) => assert(e.toString == "")
      case Right(br) => assert(getName(br) == "Borrower1")
    }
    br1 match {
      case Left(e) => assert(e.toString == "")
      case Right(br) => assert(br.name == "Borrower1")
    }
    br1 match {
      case Left(e) => assert(e.toString == "")
      case Right(br) => assert(getMaxBooks(br) == 1)
    }
    br1 match {
      case Left(e) => assert(e.toString == "")
      case Right(br) => assert(br.maxBooks == 1)
    }
  }

  it should "NOT create itself properly - brWonky" in {
    brWonky match {
      case Left(e) => assert(e.toString == "DecodingFailure(Missing required field, List(DownField(name)))")
      case Right(br) => assert(getName(br) == "")
    }
  }

  it should "set a new name and maxBooks" in {
    br1 match {
      case Left(e) => assert(e.toString == "")
      case Right(br) => assert(setName("Borrower1", Borrower("Jack", 1)) == br)
    }
    br1 match {
      case Left(e) => assert(e.toString == "")
      case Right(br) => assert(setMaxBooks(1, Borrower("Borrower1", 11)) == br)
    }
  }

  it should "return a string \"Borrower1 (1 books)\"" in {
    br1 match {
      case Left(e) => assert(e.toString == "")
      case Right(br) => assert(borrowerToString(br) == "Borrower1 (1 books)")
    }
  }

  it should "turn a Borrower into a JSON string" in {
    br1 match {
      case Left(e) => assert(e.toString == "")
      case Right(br) => assert(borrowerToJsonString(br) == jsonStringBr)
    }
  }
}
