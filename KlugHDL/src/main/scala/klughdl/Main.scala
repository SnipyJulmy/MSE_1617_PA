package klughdl

import java.io.File

import klughdl.components._
import klughdl.core.backend.dot.Dot
import klughdl.core.backend.json.Json
import klughdl.core.model.Model
import klughdl.core.utils.FileManager
import spinal.core._

/**
  * KlugHDL
  * Created by snipy on 10.11.16.
  */
object Main {

  def main(args: Array[String]) {

    val vhdlOutputDir: File = new File("vhdl")
    if (!vhdlOutputDir.exists())
      vhdlOutputDir.mkdir()
    else if (!vhdlOutputDir.isDirectory)
      System.err.println(s"$vhdlOutputDir is not a directory")

    val report = SpinalConfig(targetDirectory = "vhdl").generateVhdl(new HierarchicComponent)

    // Build the model

    val model = Model(report.toplevel)

    Dot(targetDirectory = "dot").generatePDFDiagram(model)

    // Dot("dot").generatePDFDiagram(model)

    val json = Json().generate(model)

    FileManager("model.json", "diagrams")
      .println(json)
      .close()
  }
}