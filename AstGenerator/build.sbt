name := "AstGenerator"

version := "1.0"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
    "com.github.spinalhdl" % "spinalhdl-core_2.11" % "latest.release",
    "com.github.spinalhdl" % "spinalhdl-lib_2.11" % "latest.release",
    "org.graphstream" % "gs-core" % "1.3",
    "org.graphstream" % "gs-ui" % "1.3"
)

