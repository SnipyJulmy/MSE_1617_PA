package klughdl

import klughdl.components._
import klughdl.core.backend.dot.Dot
import klughdl.core.model.Model
import spinal.core._

/**
  * Example for generating pdf diagam with dot
  * of a SpinalHDL component
  */
object DotExample extends App {

  // Create the vhdl rtl
  val report = SpinalConfig(targetDirectory = "vhdl").generateVhdl(new SmallComponent)

  // generate the diagram
  Dot(targetDirectory = "dot").generatePDFDiagram(Model(report.toplevel))
}
