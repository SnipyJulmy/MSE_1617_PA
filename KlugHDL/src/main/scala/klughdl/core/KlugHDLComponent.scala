package klughdl.core

import spinal.core.Component

/**
  * KlugHDL
  * Created by snipy on 10.11.16.
  */
class KlugHDLComponent(val name : String, val parent : Component) {
  
  val id : String = if (parent == null) s"${parent}_$name" else s"${parent.definitionName}_$name"
  val idStr : String = s""""$id""""
  private var ports : Set[Port] = Set()

  def inputDotPort() : String = {
    ports.filter(_.isInput).map(p => s"<${p.dotName}>${p.dotName}").mkString(" | ")
  }
  
  def outputDotPort() : String = {
    ports.filter(_.isOutput).map(p => s"<${p.dotName}>${p.dotName}").mkString(" | ")
  }
  
  def addPort(port : Port) : Unit = {
    ports = ports + port
  }
  
  def toJs : String = {
    val declaration = s"var $id = new ComponentShape();\n"
    val setName = s"$id.setName($idStr);\n"
    val ports = s"${generatePorts()}\n"
    val layoutXY = s"$layoutX$layoutY\n"
    val canvas = s"canvas.add($id);\n"
    s"$declaration$setName$ports$layoutXY$canvas"
  }
  
  def layoutX : String = s"""$id.setX(g.node("$id").x);\n"""
  
  def layoutY : String = s"""$id.setY(g.node("$id").y);\n"""
  
  def generatePorts() : String = {
    ports.map(p => s"""$id.addPort("${p.name}","${p.getType}");""").mkString("\n")
  }
  
  override def toString : String = s"$name"
}

object KlugHDLComponent {
  
  def apply(name : String, parent : Component) : KlugHDLComponent = new KlugHDLComponent(name, parent)
}
