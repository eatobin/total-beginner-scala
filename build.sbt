name := """total-beginner-scala"""

version := "1.0"

scalaVersion := "2.12.3"

// Change this to another test framework if you prefer
libraryDependencies += "org.scalatest" % "scalatest_2.12" % "3.0.3" % "test"
libraryDependencies ++= Seq(
  "io.spray" % "spray-json_2.12" % "1.3.3",
  "org.scala-stm" % "scala-stm_2.12" % "0.8"
)
