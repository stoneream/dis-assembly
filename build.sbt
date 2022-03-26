ThisBuild / scalaVersion := "2.13.5"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.github.stoneream"

lazy val root = (project in file(".")).settings(
  name := "dis-assembly",
  libraryDependencies ++= Seq(
    "org.scala-lang.modules" %% "scala-swing" % "3.0.0",
    "org.scalatest" %% "scalatest" % "3.2.8"
  )
)

scalafmtOnCompile := true
