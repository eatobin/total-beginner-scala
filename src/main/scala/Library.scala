object Library {

  def addBorrower(br: Borrower, brs: List[Borrower]): Option[List[Borrower]] = {
    val coll = brs.filter(_ == br)
    if (coll.isEmpty)
      Some(brs :+ br)
    else
      None
  }

}

//val br1 = Borrower("Borrower1", 1)
//val br2 = Borrower("Borrower2", 2)
//val brs1 = List(br1,br2)
//val br3 = Borrower("Borrower3", 3)
//:paste /home/eric/scala_projects/total-beginer-scala/src/main/scala/Library.scala
