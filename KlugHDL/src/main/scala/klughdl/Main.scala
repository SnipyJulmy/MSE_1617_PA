package klughdl

import java.io.File

import klughdl.components._
import klughdl.core._
import klughdl.core.dot.DotGenerator
import spinal.core._

/**
  * KlugHDL
  * Created by snipy on 10.11.16.
  */
object Main {
  
  val model : KlugHDLModel = new KlugHDLModel()
  
  def main(args : Array[String]) {
    
    val vhdlOutputDir : File = new File("vhdl")
    if (!vhdlOutputDir.exists())
      vhdlOutputDir.mkdir()
    else if (!vhdlOutputDir.isDirectory)
      System.err.println(s"$vhdlOutputDir is not a directory")
    
    val report = SpinalConfig(targetDirectory = "vhdl").generateVhdl(new OneLevelComponent)
    
    
    parseComponent(report.toplevel)
    parseInputConnection(report.toplevel)
    parseOutputConnection(report.toplevel)
    
    DotGenerator(model, "output.dot", "dot")
      .generatePDfDiagramByParent()
    
    /*
    val contents : String = klughdl.html.index(model).toString()
    
    FileManager("index.html", "diagrams")
      .println(contents)
      .close()
      */
  }
  
  def parseComponent(component : Component) : Unit = {
    model.addComponent(component, parent = component.parent)
    parsePort(component)
    component.children.foreach(c => parseComponent(c))
  }
  
  def parsePort(component : Component) : Unit = {
    component.getAllIo.foreach { baseType : BaseType =>
      val (n, io, t) = nameIoAndType(baseType)
      val port = Port(n, io, t)
      model.addPort(component, port)
    }
  }
  
  def parseConnection(component : Component) : Unit = {
    component.getAllIo.foreach { b : BaseType =>
      b.consumers.foreach { c =>
        if (c.component != b.component && c.getInputs.contains(b)) {
          val from = model.getKlugHDLComponent(b.component)
          val portFrom = Port.parsePort(b.toString())
          val to = model.getKlugHDLComponent(c.component)
          val portTo = Port.parsePort(c.toString())
          // println(s"$from[$portFrom] -> $to[$portTo]")
          model.addConnection(from, portFrom, to, portTo)
        }
      }
    }
    component.children.foreach(parseConnection)
  }
  
  def parseInputs(node : Node) : List[Node] = {
    def inner(node : Node) : List[Node] = node match {
      case bt : BaseType => if(bt.isOutput) List(bt) else List(bt) ::: node.getInputs.map(inner).foldLeft(List() : List[Node])(_ ::: _)
      case null => List()
      case _ => node.getInputs.map(inner).foldLeft(List() : List[Node])(_ ::: _)
    }
    
    inner(node).filter {
      _ match {
        case bt : BaseType => bt.isOutput
        case _ => false
      }
    }
  }
  
  def parseInputConnection(component : Component) : Unit = {
    for {
      io <- component.getAllIo
      if io.isInput
      input <- parseInputs(io)
      from = model.getKlugHDLComponent(input.component)
      portFrom = Port.parsePort(input.toString())
      to = model.getKlugHDLComponent(io.component)
      portTo = Port.parsePort(io.toString())
      if from != to
    } {
      model.addConnection(from, portFrom, to, portTo, debug = "in")
    }
    
    component.children.foreach(parseInputConnection)
  }
  
  def parseOutputConnection(component : Component) : Unit = {
    for {
      io <- component.getAllIo
      if io.isOutput
      consumer <- io.consumers
      from = model.getKlugHDLComponent(io.component)
      portFrom = Port.parsePort(io.toString())
      to = model.getKlugHDLComponent(consumer.component)
      portTo = Port.parsePort(consumer.toString())
      if from != to
    } {
      model.addConnection(from, portFrom, to, portTo, debug = "out")
      println(s"$from[$io] -> $to[$consumer]")
    }
    component.children.foreach(parseOutputConnection)
  }
  
  def nameIoAndType(baseType : BaseType) : (String, String, String) = {
    val full = baseType.toString().split("/").last
    val name = full.split(":").head.replaceAll(" ", "").replaceAll("_", ".")
    val ioType = full.split(":").last.replaceFirst(" ", "").split(" ")
    (name, ioType(0), if (ioType(1) == "input") "output" else "input")
  }
}
