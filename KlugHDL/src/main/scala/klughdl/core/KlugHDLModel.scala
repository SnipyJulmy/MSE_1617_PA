package klughdl.core

import spinal.core.Component

/**
  * KlugHDL
  * Created by snipy on 10.11.16.
  */

class KlugHDLModel
{
    var components : Map[Component,KlugHDLComponent] = Map()

    def addComponent(component: Component) : Unit = {
        components += (component -> KlugHDLComponent(component.definitionName,component.parent))
    }

    def getKlugHDLComponent(component: Component) : KlugHDLComponent = {
        components(component)
    }

    def addPort(component: Component, port: Port) = {
        components(component).addPort(port)
    }

    def generateJs() : Unit =
    {
        val fileManager : FileManager = FileManager("output.js","diagrams")
        fileManager.println(components.map((entry) => entry._2.toJs).mkString("\n"))
        fileManager.close()
    }
}
