package klughdl.core.model

import spinal.core._

/**
  * KlugHDL
  * Created by snipy on 07.12.16.
  */
sealed trait KlugHDLComponent {
  
  val name : String
  val parent : Component
  val component : Component = this match {
    case KlugHDLComponentBasic(_, c, _) => c
    case KlugHDLComponentIO(_, _) => null
  }
  
  def isIO : Boolean = this match {
    case KlugHDLComponentBasic(_, _, _) => false
    case KlugHDLComponentIO(_, _) => true
  }
  
  val id : String = if (parent == null) s"${parent}_$name" else s"${parent.definitionName}_$name"
  val idStr : String = s""""$id""""
  var ports : Set[Port] = Set()
  
  def inputDotPort() : String = {
    ports.filter(_.isInput).map(p => s"<${p.dotName}>${p.dotName}").mkString(" | ")
  }
  
  def outputDotPort() : String = {
    ports.filter(_.isOutput).map(p => s"<${p.dotName}>${p.dotName}").mkString(" | ")
  }
  
  def addPort(port : Port) : Unit = {
    ports += port
  }
}

final case class KlugHDLComponentBasic(name : String, override val component : Component, parent : Component) extends KlugHDLComponent {
  
}

final case class KlugHDLComponentIO(name : String, parent : Component) extends KlugHDLComponent {
  
}
