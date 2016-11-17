package klughdl.core

import spinal.core.Component

/**
  * KlugHDL
  * Created by snipy on 10.11.16.
  */
class KlugHDLComponent(val name: String, val parent: Component)
{
    private var ports: Set[Port] = Set()

    def addPort(port: Port) =
    {
        ports = ports + port
    }

    def toJs: String =
    {

        val declaration = s"var $name = new ComponentShape();\n"
        val setName = s"$name.setName($name);\n"
        val ports = generatePorts()

        s"$declaration$setName$ports"
    }

    def generatePorts(): String =
    {
        ports.map(p => s"$name.addPort(${p.name},${p.getType});").mkString("\n")
    }
}

object KlugHDLComponent
{
    def apply(name: String, parent: Component): KlugHDLComponent = new KlugHDLComponent(name, parent)
}