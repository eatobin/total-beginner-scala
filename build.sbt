name := """total-beginner-scala"""

version := "1.0"

scalaVersion := "2.11.8"

// Change this to another test framework if you prefer
libraryDependencies += "org.scalatest" % "scalatest_2.11" % "3.0.1"
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.4.14",
  "org.scala-stm" %% "scala-stm" % "0.8",
  "io.spray" %% "spray-json" % "1.3.2"
)
