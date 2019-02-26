ThisBuild / scalaVersion := "2.12.8"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "total-beginner-scala",
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.0.5" % Test,
      "io.spray" %% "spray-json" % "1.3.4",
      "org.scala-stm" %% "scala-stm" % "0.8"
    )
  )
