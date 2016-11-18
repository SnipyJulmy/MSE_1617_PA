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
