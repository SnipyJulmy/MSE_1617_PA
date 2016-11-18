package klughdl.core

/**
  * KlugHDL
  * Created by snipy on 10.11.16.
  */
sealed trait Port {
  
  val name: String
  val hdlType: String
  
  def getType: String = this match {
    case _: InputPort => "input"
    case _: OutputPort => "output"
  }
}

object Port {
  
  def apply(name: String, io: String, hdlType: String): Port = io match {
    case "input" | "in" => InputPort(name, hdlType)
    case "output" | "out" => OutputPort(name, hdlType)
  }
  
  def parsePort(rawPort : String) : String = {
    rawPort
      .replaceAll(" ","")
      .split(":").head
      .split("/").last
      .replaceAll("_",".")
  }
}

final case class InputPort(name: String, hdlType: String) extends Port

final case class OutputPort(name: String, hdlType: String) extends Port
