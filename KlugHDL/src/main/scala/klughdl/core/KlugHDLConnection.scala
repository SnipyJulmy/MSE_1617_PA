package klughdl.core

/**
  * KlugHDL
  * Created by snipy on 22.11.16.
  */
case class KlugHDLConnection(from : (KlugHDLComponent, String), to : (KlugHDLComponent, String)) {
  
  def toJS : String = s"""canvas.add(newConnection(${from._1.id}.getPort("${from._2}"), ${to._1.id}.getPort("${to._2}")));"""
  
  def toJSLayout : String = s"""g.setEdge(${from._1.idStr}, ${to._1.idStr});"""
}
