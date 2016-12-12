package klughdl.core.model

import klughdl.core.dot.DotGenerator
import klughdl.core.utils.Debug
import spinal.core._

import scala.collection.mutable
import scala.util.{Failure, Success, Try}

/**
  * KlugHDL
  * Created by snipy on 07.12.16.
  */
class Diagram(val parent : Component) {
  
  var components : Map[Component, KlugHDLComponent] = Map()
  
  var connections = new mutable.HashMap[(KlugHDLComponent, Port), mutable.Set[(KlugHDLComponent, Port)]]
                        with mutable.MultiMap[(KlugHDLComponent, Port), (KlugHDLComponent, Port)]
  
  def generateDot(targetDirectory : String) : Unit = {
    DotGenerator(targetDirectory).generateDiagram(this)
  }
  
  override def toString : String = {
    s"Diagram[${parent.definitionName}]" + components.map(entry => s"${entry._2}") mkString "\n"
  }
  
  override def equals(o : scala.Any) : Boolean = o match {
    case d : Diagram => this.parent == d.parent
    case _ => super.equals(o)
  }
  
  protected[model] def foreachChildren(f : (Component) => Unit) : Unit = {
    if (parent != null) parent.children.foreach(f)
    // only 1 children --> toplevel !
    else f(Model.topLevel)
  }
  
  protected[model] def addComponents(component : Component) : Unit = {
    components += (component -> KlugHDLComponentBasic(component.definitionName, component, component.parent))
  }
  
  // add the component which correspond to the parent of the diagrams
  // -> input and output from/to the external world of the component
  protected[model] def addIoComponents(component : Component) : Unit = {
    if (component == null)
      components += (component -> KlugHDLComponentIO("NULL", component))
    else
      components += (component -> KlugHDLComponentIO(component.definitionName, component))
  }
  
  protected[model] def addPort(component : Component, port : Port) : Unit = {
    components(component).addPort(port)
  }
  
  protected[model] def addConnection(from : Component, portFrom : Port, to : Component, portTo : Port) : Unit = {
    connections.addBinding((components(from), portFrom), (components(to), portTo))
  }
  
  protected[model] def addConnection(con : (Component, Port, Component, Port)) : Unit = {
    println(con)
    if(components.contains(con._1) && components.contains(con._3))
      connections.addBinding((components(con._1), con._2), (components(con._3), con._4))
  }
}
