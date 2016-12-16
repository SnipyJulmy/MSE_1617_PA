name := "KlugHDL"

version := "1.0"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  "com.github.spinalhdl" % "spinalhdl-core_2.11" % "latest.release",
  "com.github.spinalhdl" % "spinalhdl-lib_2.11" % "latest.release",
  "org.scala-lang.modules" %% "scala-xml" % "1.0.3",
  "net.liftweb" % "lift-json_2.10" % "2.6.3"
)

scalacOptions ++= Seq(
  "-language:postfixOps"
)
