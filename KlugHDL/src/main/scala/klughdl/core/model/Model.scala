/*
 *
 * Copyright (c) 2016  Sylvain Julmy
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; see the file COPYING. If not, write to the
 * Free Software Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

package klughdl.core.model

import spinal.core._

case class Model(toplevel : Component) {
  
  var diagrams : Set[Diagram] = Set()
  
  generateDiagrams(toplevel)
  diagrams.foreach(generateComponent)
  diagrams.foreach(generatePort)
  diagrams.foreach(generateConnection)
  
  override def toString : String = s"Model : " + diagrams.mkString("\n")
  
  // TODO move to the backend
  private def generateDiagrams(component : Component) : Unit = {
    diagrams += new Diagram(component.parent)
    component.children.foreach(generateDiagrams)
  }
  
  private def generateComponent(diagram : Diagram) : Model = {
    // add all the children of the parent to the diagrams
    diagram.foreachChildren(diagram.addComponents, toplevel)
    // add the input and output parent connection, except it's top level
    if (diagram.parent != null)
      diagram.addIoComponents(diagram.parent)
    this
  }
  
  private def generatePort(diagram : Diagram) : Unit = {
    def generatePort(entry : (Component, KlugHDLComponent)) : Unit = {
      if (entry._1 != null) entry._1.getAllIo.foreach(bt => entry._2.addPort(Port(bt)))
    }
    
    diagram.components.foreach(generatePort)
  }
  
  private def generateConnection(diagram : Diagram) : Unit = {
    // Generate the input connection between the brother
    def parseInputConnection(component : Component) : Unit = {
      
      def parseInputs(node : Node) : List[BaseType] = {
        def inner(node : Node) : List[BaseType] = node match {
          case bt : BaseType =>
            if (bt.isOutput) List(bt)
            else List(bt) ::: node.getInputs.map(inner).foldLeft(List() : List[BaseType])(_ ::: _)
          case null => List()
          case _ => node.getInputs.map(inner).foldLeft(List() : List[BaseType])(_ ::: _)
        }
        
        inner(node).filter {
          _ match {
            case bt : BaseType => bt.isOutput
            case _ => false
          }
        }
      }
      
      for {
        io <- component.getAllIo
        if io.isInput
        input <- parseInputs(io)
      } {
        diagram.addConnection(input.component, Port(input), io.component, Port(io))
      }
    }
    
    // Generate the output connection between the brother
    def parseOutputConnection(component : Component) : Unit = {
      
      def parseConsumers(node : Node) : List[BaseType] = {
        def inner(node : Node) : List[BaseType] = node match {
          case bt : BaseType => if (bt.isInput) List(bt) else List(bt) ::: node.consumers.map(inner).foldLeft(List() : List[BaseType])(
            _ ::: _)
          case null => List()
          case _ => node.consumers.map(inner).foldLeft(List() : List[BaseType])(_ ::: _)
        }
        
        inner(node).filter {
          _ match {
            case bt : BaseType => bt.isInput
            case _ => false
          }
        }
      }
      
      for {
        io <- component.getAllIo
        if io.isInput
        consumer <- parseConsumers(io)
      } {
        diagram.addConnection(io.component, Port(io), consumer.component, Port(consumer))
      }
    }
    
    // Generate the connection with the parent
    def parseParentConnection(component : Component) : Unit = {
      
      def parseInputParentConnection(component : Component) : Unit = {
        if (component.parent != null) {
          val con = for {
            io_p <- component.parent.getAllIo
            io <- component.getAllIo
            if io.getInputs.contains(io_p)
          } yield (io_p.component, Port(io_p), io.component, Port(io))
          con.foreach(diagram.addConnection)
        }
      }
      
      def parseOutputParentConnection(component : Component) : Unit = {
        if (component.parent != null) {
          val con = for {
            io_p <- component.parent.getAllIo
            io <- getInputs(io_p)
            if io != null
          } yield (io.component, Port(io), io_p.component, Port(io_p))
          con.foreach(diagram.addConnection)
        }
      }
      
      parseInputParentConnection(component)
      parseOutputParentConnection(component)
      
    }
    
    diagram.components.keys.foreach { c =>
      if (c != null) {
        parseInputConnection(c)
        parseOutputConnection(c)
        parseParentConnection(c)
      }
    }
  }
  
  private def getInputs(bt : BaseType) : List[BaseType] = {
    val comp = bt.component
    
    def inner(n : Node, acc : List[Node]) : List[Node] = {
      if (n == null) acc
      else if (n.component != comp) {
        acc
      }
      else if (n.getInputsCount > 1) {
        n.getInputs.flatMap(n => inner(n, Nil)).toList
      }
      else {
        inner(n.getInputs.next(), n :: acc)
      }
    }
    
    inner(bt, Nil).map(_.getInputs.next().asInstanceOf[BaseType])
  }
}
