package klughdl

import java.io.File

import klughdl.components._
import klughdl.core._
import spinal.core._

/**
  * KlugHDL
  * Created by snipy on 10.11.16.
  */
object Main {
  
  val model: KlugHDLModel = new KlugHDLModel()
  
  def main(args: Array[String]) {

    val vhdlOutputDir: File = new File("vhdl")
    if (!vhdlOutputDir.exists())
      vhdlOutputDir.mkdir()
    else if (!vhdlOutputDir.isDirectory)
      System.err.println(s"$vhdlOutputDir is not a directory")

    val report = SpinalConfig(targetDirectory = "vhdl").generateVhdl(new HierarchicComponent)
    
    parseComponentTree(report.toplevel)
    parseConnectionTest(report.toplevel)
    parseOutputConnection(report.toplevel)
    
    val contents: String = klughdl.html.index(model).toString()
    
    FileManager("index.html", "diagrams")
      .println(contents)
      .close()
  }
  
  def parseComponentTree(component: Component): Unit = {
    model.addComponent(component)
    parsePort(component)
    component.children.foreach(c => parseComponentTree(c))
  }
  
  def parsePort(component: Component): Unit = {
    component.getAllIo.foreach { baseType: BaseType =>
      val (n, io, t) = nameIoAndType(baseType)
      val port = Port(n, io, t)
      model.addPort(component, port)
    }
  }
  
  def parseConnection(component: Component): Unit = {
    component.getAllIo.foreach { b: BaseType =>
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
  
  def parseInputs(node: Node): List[Node] = node match {
    case bt: BaseType => List(bt) ::: node.getInputs.map(parseInputs).foldLeft(List(): List[Node])(_ ::: _)
    case null => List()
    case _ => node.getInputs.map(parseInputs).foldLeft(List(): List[Node])(_ ::: _)
  }
  
  def parseConnectionTest(component: Component): Unit = {

    for {
      io <- component.getAllIo
      if io.isInput
      inputs <- parseInputs(io)
      from = model.getKlugHDLComponent(inputs.component)
      portFrom = Port.parsePort(inputs.toString())
      to = model.getKlugHDLComponent(io.component)
      portTo = Port.parsePort(io.toString())
      if from != to
    } model.addConnection(from, portFrom, to, portTo,debug = "in")

    component.children.foreach(parseConnectionTest)
  }

  def parseOutputConnection(component: Component): Unit = {
    for {
      io <- component.getAllIo
      if io.isOutput
      consumer <- io.consumers
      from = model.getKlugHDLComponent(io.component)
      portFrom = Port.parsePort(io.toString())
      to = model.getKlugHDLComponent(consumer.component)
      portTo = Port.parsePort(consumer.toString())
      if from != to
    } model.addConnection(from,portFrom,to,portTo,debug = "out")
    component.children.foreach(parseOutputConnection)
  }

  def nameIoAndType(baseType: BaseType): (String, String, String) = {
    val full = baseType.toString().split("/").last
    val name = full.split(":").head.replaceAll(" ", "").replaceAll("_", ".")
    val ioType = full.split(":").last.replaceFirst(" ", "").split(" ")
    (name, ioType(0), if (ioType(1) == "input") "output" else "input")
  }
}
