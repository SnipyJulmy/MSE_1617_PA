package klughdl.core.model

import spinal.core._

import scala.collection.mutable

/**
  * KlugHDL
  * Created by snipy on 07.12.16.
  */
class Diagram(val parent : Component) {
  
  var components : Map[Component, KlugHDLComponent] = Map()
  
  var connections = new mutable.HashMap[(KlugHDLComponent, Port), mutable.Set[(KlugHDLComponent, Port)]]
                        with mutable.MultiMap[(KlugHDLComponent, Port), (KlugHDLComponent, Port)]
  
  def foreachChildren(f : (Component) => Unit) : Unit = if (parent != null) parent.children.foreach(f)
  
  def addComponents(component : Component) : Unit = {
    components += (component -> KlugHDLComponentBasic(component.definitionName, component, component.parent))
  }
  
  // add the component which correspond to the parent of the diagrams
  // -> input and output from/to the external world of the component
  def addIoComponents(component : Component) : Unit = {
    if (component == null)
      components += (component -> DiagramsIO("NULL", component))
    else
      components += (component -> DiagramsIO(component.definitionName, component))
  }
  
  def addPort(component : Component, port : Port) : Unit = {
    components(component).addPort(port)
  }
  
  def addConnection(from : Component, portFrom : Port, to : Component, portTo : Port) : Unit = {
    connections.addBinding((components(from), portFrom), (components(to), portTo))
  }
  
  override def toString : String = {
    s"Diagram[${parent.definitionName}]" + components.map(entry => s"${entry._2}") mkString "\n"
  }
}
