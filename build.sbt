ThisBuild / scalaVersion := "2.13.4"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.example"
ThisBuild / organizationName := "example"
ThisBuild / useCoursier := false

val circeVersion = "0.12.3"

lazy val root = (project in file("."))
  .settings(
    name := "total",
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.1.2" % "test"
    ),
    libraryDependencies ++= Seq(
      "io.circe" %% "circe-core",
      "io.circe" %% "circe-generic",
      "io.circe" %% "circe-parser"
    ).map(_ % circeVersion),
    scalacOptions += "-deprecation"
  )
