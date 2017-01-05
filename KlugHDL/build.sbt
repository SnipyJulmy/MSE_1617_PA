name := "KlugHDL"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "com.github.spinalhdl" % "spinalhdl-core_2.11" % "latest.release",
  "com.github.spinalhdl" % "spinalhdl-lib_2.11" % "latest.release",
  "org.scala-lang" % "scala-compiler" % "2.11.8",
  "net.liftweb" % "lift-json_2.10" % "2.6.3"
)

scalacOptions ++= Seq(
  "-language:postfixOps"
)
