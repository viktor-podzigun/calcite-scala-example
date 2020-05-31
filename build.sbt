
val ideExcludedDirectories = SettingKey[Seq[File]]("ide-excluded-directories")

lazy val `calcite-scala-example` = (project in file("."))
  .settings(
    organization := "org.scommons.viktor-podzigun",
    name := "calcite-scala-example",
    description := "Using Apache Calcite library with Scala example",
    scalaVersion := "2.12.10",
    scalacOptions ++= Seq(
      //see https://docs.scala-lang.org/overviews/compiler-options/index.html#Warning_Settings
      //"-Xcheckinit",
      "-Xfatal-warnings",
      "-Xlint:_",
      "-Ywarn-macros:after", // Only inspect expanded trees when generating unused symbol warnings
      "-explaintypes",
      "-unchecked",
      "-deprecation",
      "-feature"
    ),
    
    ideExcludedDirectories := {
      val base = baseDirectory.value
      List(
        base / ".idea",
        base / "target"
      )
    },
    
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.0.1" % "test"
    ),

    //when run tests with coverage: "sbt clean coverage test coverageReport"
    coverageMinimum := 80
  )
