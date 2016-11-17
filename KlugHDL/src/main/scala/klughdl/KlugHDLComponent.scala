package klughdl

/**
  * KlugHDL
  * Created by snipy on 10.11.16.
  */
class KlugHDLComponent(val name: String)
{
    private var ports: Set[Port] = Set()

    def addPort(port: Port) =
    {
        ports = ports + port
    }

    def generatePorts(): String =
    {
        ports.map(p => s"$name.addPort(${p.name},${p.getType});").mkString("\n")
    }

    def toJs : String =
    {
        val declaration = s"var $name = new ComponentShape();\n"
        val setName = s"$name.setName($name);\n"
        val ports = generatePorts()

        s"$declaration$setName$ports"
    }
}
