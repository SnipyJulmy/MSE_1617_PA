package klughdl.core.backend

import klughdl.core.model._
import net.liftweb.json.JsonDSL._
import net.liftweb.json._

case class Json() extends Backend {
  
  private val extInput = "EXTERNAL_INPUT"
  private val extOutput = "EXTERNAL_OUTPUT"
  
  override def generate(model : Model) : String = {
    val json = generateJson(model)
    pretty(render(json))
  }
  
  private def generateJson(model : Model) : JValue = {
    model.diagrams.map("diagram" -> generateJson(_))
  }
  
  private def generateJson(diagram : Diagram) : JValue = {
    val parentName = if (diagram.parent == null) "null" else diagram.parent.definitionName
    ("name" -> parentName) ~
      ("components" -> diagram.components.map(c => generateJson(c._2))) ~
      ("connections" -> (for {
        entry <- diagram.connections
        value <- entry._2
        if !entry._1._1.isIO && !value._1.isIO
        if entry._1._1.component != diagram.parent
        if entry._1._1.parent == value._1.parent
        if entry._1._1 != value._1
      } yield ("from" -> ("name" -> s"${entry._1._1.name}") ~ ("port" -> s"${entry._1._2.name}")) ~
        ("to" -> ("name" -> s"${value._1.name}") ~ ("port" -> s"${value._2.name}")))) ~
      ("inputs" -> (for {
        entry <- diagram.connections
        value <- entry._2
        if entry._1._1.isIO
        if !value._1.isIO
      } yield ("from" -> ("name" -> s"$extInput") ~ ("port" -> s"${entry._1._2.name}")) ~
        ("to" -> ("name" -> s"${value._1.name}") ~ ("port" -> s"${value._2.name}")))) ~
      ("outputs" -> (for {
        entry <- diagram.connections
        value <- entry._2
        if value._1.isIO
        if !entry._1._1.isIO
      } yield ("from" -> ("name" -> s"${entry._1._1.name}") ~ ("port" -> s"${entry._1._2.name}")) ~
        ("to" -> ("name" -> s"$extOutput") ~ ("port" -> s"${value._2.name}"))))
  }
  
  private def generateJson(klugHDLComponent : KlugHDLComponent) : JValue = klugHDLComponent match {
    case KlugHDLComponentBasic(name, _, _) =>
      ("name" -> name) ~
        ("type" -> "default") ~
        ("ports" -> klugHDLComponent.ports.map(generateJson))
    case KlugHDLComponentIO(name, _) =>
      ("name" -> name) ~
        ("type" -> "io") ~
        ("ports" -> klugHDLComponent.ports.map(generateJson))
  }
  
  private def generateJson(port : Port) : JValue = port match {
    case InputPort(name, hdlType) =>
      ("name" -> name) ~
        ("type" -> hdlType) ~
        ("portType" -> "input")
    case OutputPort(name, hdlType) =>
      ("name" -> name) ~
        ("type" -> hdlType) ~
        ("portType" -> "output")
  }
}
