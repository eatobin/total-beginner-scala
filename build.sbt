name := """total-beginner-scala"""

version := "1.0"

scalaVersion := "2.11.8"

// Change this to another test framework if you prefer
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.6" % "test"
libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-reflect" % "2.11.8",
  "org.scala-lang.modules" % "scala-xml_2.11" % "1.0.4",
  "com.typesafe.akka" %% "akka-actor" % "2.4.16",
  "org.scala-stm" %% "scala-stm" % "0.8",
  "io.spray" %% "spray-json" % "1.3.3"
)
