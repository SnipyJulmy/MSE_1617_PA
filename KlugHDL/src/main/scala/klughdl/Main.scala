package klughdl

import klughdl.components.HierarchicComponent
import klughdl.core.{FileManager, KlugHDLModel, Port}
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

    val fileManager: FileManager = FileManager("index.html", "diagrams")
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
      model.addPort(component, Port(n, io, t))
    }
  }

  def nameIoAndType(baseType: BaseType): (String, String, String) = {

    val full = baseType.toString().split("/").last
    val name = full.split(":").head.replaceAll(" ", "").replaceAll("_", ".")
    val ioType = full.split(":").last.replaceFirst(" ", "").split(" ")
    (name, ioType(0), ioType(1))
  }
}
