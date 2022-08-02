ThisBuild / scalaVersion := "2.13.8"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.eatobin"
ThisBuild / organizationName := "eatobin"
ThisBuild / useCoursier := true

val circeVersion = "0.14.2"

lazy val root = (project in file("."))
  .settings(
    name := "totalscala",
    libraryDependencies ++= Seq(
      "org.scalactic" %% "scalactic" % "3.2.12",
      "org.scalatest" %% "scalatest" % "3.2.12" % Test
    ),
    libraryDependencies ++= Seq(
      "io.circe" %% "circe-core",
      "io.circe" %% "circe-generic",
      "io.circe" %% "circe-parser"
    ).map(_ % circeVersion),
    scalacOptions += "-deprecation"
  )
