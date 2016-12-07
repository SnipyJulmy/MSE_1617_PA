package klughdl.core.model

import spinal.core._

/**
  * KlugHDL
  * Created by snipy on 10.11.16.
  */
sealed trait Port {
  
  val name : String
  val hdlType : String
  val dotName : String = name.replaceAll("\\.", "")
  
  def isInput : Boolean = this match {
    case InputPort(_, _) => true
    case OutputPort(_, _) => false
  }
  
  def isOutput : Boolean = this match {
    case InputPort(_, _) => false
    case OutputPort(_, _) => true
  }
  
  def getType : String = this match {
    case _ : InputPort => "input"
    case _ : OutputPort => "output"
  }
}

object Port {
  
  def parsePort(rawPort : String) : String = {
    val tmp = rawPort
      .replaceAll(" ", "")
      .split(":").head
      .split("/").last
      .split("_")
    s"${tmp(tmp.length - 2)}.${tmp(tmp.length - 1)}"
  }
  
  def apply(bt : BaseType) : Port = {
    def nameIoAndType(baseType : BaseType) : (String, String, String) = {
      val full = baseType.toString().split("/").last
      val name = full.split(":").head.replaceAll(" ", "").replaceAll("_", ".")
      val ioType = full.split(":").last.replaceFirst(" ", "").split(" ")
      (name, ioType(0), if (ioType(1) == "input") "output" else "input")
    }
    
    val (n, io, t) = nameIoAndType(bt)
    Port(n, io, t)
  }
  
  def apply(name : String, io : String, hdlType : String) : Port = io match {
    case "input" | "in" => InputPort(name, hdlType)
    case "output" | "out" => OutputPort(name, hdlType)
  }
}

final case class InputPort(name : String, hdlType : String) extends Port

final case class OutputPort(name : String, hdlType : String) extends Port