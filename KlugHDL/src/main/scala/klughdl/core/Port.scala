package klughdl.core

/**
  * KlugHDL
  * Created by snipy on 10.11.16.
  */
sealed trait Port {
  
  val name : String
  val hdlType : String
  
  def getType : String = this match {
    case _ : InputPort => "input"
    case _ : OutputPort => "output"
  }
}

object Port {
  
  def apply(name : String, io : String, hdlType : String) : Port = io match {
    case "input" | "in" => InputPort(name, hdlType)
    case "output" | "out" => OutputPort(name, hdlType)
  }
  
  def parsePort(rawPort : String) : String = {
    val tmp = rawPort
      .replaceAll(" ", "")
      .split(":").head
      .split("/").last
      .split("_")
    s"${tmp(tmp.length - 2)}.${tmp(tmp.length - 1)}"
  }
}

final case class InputPort(name : String, hdlType : String) extends Port

final case class OutputPort(name : String, hdlType : String) extends Port
