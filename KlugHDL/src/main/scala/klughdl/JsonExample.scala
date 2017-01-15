package klughdl

import klughdl.components.SmallComponent
import spinal.core.SpinalConfig
import klughdl.core.backend.json.Json
import klughdl.core.model.Model

/**
  * Example for generating the json model
  * of a SpinalHDL component
  */
object JsonExample extends App {
  // Create the vhdl rtl
  val report = SpinalConfig(targetDirectory = "vhdl").generateVhdl(new SmallComponent)

  // generate the model
  Json(targetDirectory = "json").generateJsonModel(Model(report.toplevel))
}
