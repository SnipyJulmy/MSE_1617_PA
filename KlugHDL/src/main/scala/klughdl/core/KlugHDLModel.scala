package klughdl.core

import spinal.core.Component

import scala.collection.mutable

/**
  * KlugHDL
  * Created by snipy on 10.11.16.
  */

class KlugHDLModel {
  
  var connections = new mutable.HashMap[(KlugHDLComponent, String), mutable.Set[(KlugHDLComponent, String)]]
                        with mutable.MultiMap[(KlugHDLComponent, String), (KlugHDLComponent, String)]
  
  var components : Map[Component, KlugHDLComponent] = Map()
  
  // the set of all the parents in the components tree
  var parents : Set[Component] = Set()
  
  def addConnection(from : KlugHDLComponent, portFrom : String, to : KlugHDLComponent, portTo : String, debug : String = "") : Unit = {
    println(s"$debug $from[$portFrom] -> $to[$portTo]")
    connections.addBinding((from, portFrom), (to, portTo))
  }
  
  def addComponent(component : Component, parent : Component) : Unit = {
    components += (component -> KlugHDLComponent(component.definitionName, parent))
    parents += parent
  }
  
  def numberOfDiagram : Int = parents.size
  
  def getKlugHDLComponent(component : Component) : KlugHDLComponent = {
    components(component)
  }
  
  def allChildrenComponent(parent : Component) : List[KlugHDLComponent] = {
    components.filter(_._1.parent == parent).values.toList
  }
  
  def addPort(component : Component, port : Port) : Unit = {
    components(component).addPort(port)
  }
  
  def connectionToJs : String = {
    
    (for {
      entry <- connections
      value <- entry._2
    } yield KlugHDLConnection(entry._1, value))
      .toList
      .distinct
      .map(_.toJS)
      .mkString("\n")
  }
  
  def connectionToJsLayout : String = {
    (for {
      entry <- connections
      value <- entry._2
    } yield KlugHDLConnection(entry._1, value))
      .toList
      .distinct
      .map(_.toJSLayout)
      .mkString("\n")
  }
  
  def getKlugHDLComponents : List[KlugHDLComponent] = components.values.toList
}
