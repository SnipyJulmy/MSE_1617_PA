package klughdl.core

import spinal.core.Component

/**
  * KlugHDL
  * Created by snipy on 10.11.16.
  */

class KlugHDLModel {
  
  var components: Map[Component, KlugHDLComponent] = Map()
  var connections: Map[(KlugHDLComponent, String), (KlugHDLComponent, String)] = Map()
  
  def addConnection(from: KlugHDLComponent, portFrom: String, to: KlugHDLComponent, portTo: String): Unit = {
    connections += (from, portFrom) -> (to, portTo)
  }
  
  def addComponent(component: Component): Unit = {
    components += (component -> KlugHDLComponent(component.definitionName, component.parent))
  }
  
  def getKlugHDLComponent(component: Component): KlugHDLComponent = {
    components(component)
  }
  
  def addPort(component: Component, port: Port) = {
    components(component).addPort(port)
  }
  
  def connectionToJs: String = {
    connections
      .toList.distinct.toMap // remove dupplicate
      .filter(entry => entry._1._1 != entry._2._1) // remove point on itself
      .filter(entry => !connections.toList.contains(entry.swap))
      .map { c =>
      s"""canvas.add(newConnection(${c._1._1.id}.getPort("${c._1._2}"), ${c._2._1.id}.getPort("${c._2._2}")));"""
    }.mkString("\n")
  }
  
  def getKlugHDLComponents = components.values.toList
  
  def generateJs(): Unit = {
    
    val fileManager: FileManager = FileManager("output.js", "diagrams")
    fileManager.println(components.map((entry) => entry._2.toJs).mkString("\n"))
    fileManager.close()
  }
}
