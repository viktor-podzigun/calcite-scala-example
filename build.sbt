
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
      "org.postgresql" % "postgresql" % "42.2.5",
      "com.typesafe" % "config" % "1.3.3",
      "org.scaldi" %% "scaldi" % "0.5.8",
      "ch.qos.logback" % "logback-classic" % "1.2.3"
    ) ++ Seq(
      "org.scalatest" %% "scalatest" % "3.0.1",
      "com.whisk" %% "docker-testkit-scalatest" % "0.9.8",
      "com.whisk" %% "docker-testkit-impl-spotify" % "0.9.8"
    ).map(_  % "test"),

    //when run tests with coverage: "sbt clean coverage test coverageReport"
    coverageMinimum := 80
  )
