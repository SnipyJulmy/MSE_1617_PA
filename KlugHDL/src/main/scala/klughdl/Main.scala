package klughdl

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
    
    val report = SpinalConfig(targetDirectory = "vhdl").generateVhdl(new HierarchicComponent)
    
    parseComponentTree(report.toplevel)
    parseConnection(report.toplevel)
    
    parseConnectionTest(report.toplevel)
    
    val contents: String = klughdl.html.index(model).toString()
    
    val fileManager: FileManager = FileManager("index.html", "diagrams")
    fileManager.println(contents)
    fileManager.close()
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
      inputs <- parseInputs(io)
      from = model.getKlugHDLComponent(io.component)
      portFrom = Port.parsePort(io.toString())
      to = model.getKlugHDLComponent(inputs.component)
      portTo = Port.parsePort(inputs.toString())
    } model.addConnection(from, portFrom, to, portTo)

    component.children.foreach(parseConnectionTest)
  }

  def nameIoAndType(baseType: BaseType): (String, String, String) = {
    val full = baseType.toString().split("/").last
    val name = full.split(":").head.replaceAll(" ", "").replaceAll("_", ".")
    val ioType = full.split(":").last.replaceFirst(" ", "").split(" ")
    (name, ioType(0), if (ioType(1) == "input") "output" else "input")
  }
}
