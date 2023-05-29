val scala3Version = "3.2.2"

lazy val root = project
  .in(file("."))
  .settings(
    name := "price-basket-challenge",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.2.15" % Test,
      "org.scalameta" %% "munit" % "0.7.29" % Test,
      "org.scala-lang.modules" %% "scala-parser-combinators" % "2.2.0"
    ))
