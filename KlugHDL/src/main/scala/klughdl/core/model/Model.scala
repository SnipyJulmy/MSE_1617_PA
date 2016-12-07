package klughdl.core.model

import spinal.core._

/**
  * KlugHDL
  * Created by snipy on 07.12.16.
  */
object Model {
  
  var diagrams : Set[Diagram] = Set()
  
  def apply(topLevel : Component) : Model.type = {
    generateDiagrams(topLevel)
    diagrams.foreach(generateComponent)
    diagrams.foreach(generatePort)
    diagrams.foreach(generateConnection)
    this
  }
  
  override def toString : String = s"Model : " + diagrams.mkString("\n")
  
  private def generateDiagrams(component : Component) : Unit = {
    diagrams += new Diagram(component.parent)
    component.children.foreach(generateDiagrams)
  }
  
  private def generateComponent(diagram : Diagram) : Model.type = {
    // add all the children of the parent to the diagrams
    diagram.foreachChildren(diagram.addComponents)
    // add the input and output parent connection
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
          case bt : BaseType => if (bt.isOutput) List(bt) else List(bt) ::: node.getInputs.map(inner).foldLeft(List() : List[BaseType])(
            _ ::: _)
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
      println(s"PARENT CONNECTION EXEC")
    }
    
    diagram.components.keys.foreach { c =>
      if (c != null) {
        parseInputConnection(c)
        parseOutputConnection(c)
        parseParentConnection(c)
      }
    }
  }
}
