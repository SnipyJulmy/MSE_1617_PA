import org.graphstream.graph.Graph
import org.graphstream.graph.implementations.{DefaultGraph, MultiGraph}
import spinal.core.SpinalConfig
import spinal.core._

/**
  * Created by snipy on 02.10.16.
  */

//noinspection FieldFromDelayedInit
object Main
{
    def main(args: Array[String])
    {
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer")

        val report = SpinalConfig(targetDirectory = "vhdl").generateVhdl(new BasicComponent)

        parseAstTree(report.toplevel)
    }

    def parseAstTree(root: Component): Unit =
    {
        def parseIO(component: Component, level: Int, g: Graph): Unit =
        {
            component.getAllIo.foreach
            {
                n: spinal.core.BaseType =>
                {
                    println(s"${"\t" * level}${n.toString()}")
                    if (n.isInput)
                    {
                        val from =
                        {
                            val tmp = n.getInput(0)
                            if (tmp == null)
                                "Root Component input"
                            else
                                tmp.toString()
                        }

                        if (n.getInput(0) != null)
                        {
                            val label = n.getInput(0).toString()
                            addEdge(g, component.definitionName, n.getInput(0).component.definitionName, label = label)
                        }

                        println(s"${"\t" * (level + 1)}-> input from $from")
                    }
                    else if (n.isOutput)
                    {
                        if (n.consumers.isEmpty)
                            println(s"${"\t" * (level + 1)}-> Root Component output")
                        else
                            n.consumers.foreach
                            { n_consumers =>
                                val label = s"${n.toString()} --> ${n_consumers.toString()}"
                                println(s"${"\t" * (level + 1)}-> output to ${n_consumers.toString()}")
                                addEdge(g,n_consumers.component.definitionName,n.component.definitionName,label = label)
                            }
                    }
                }
            }
        }

        def parseComponent(crt: Component, level: Int, g: Graph): Unit =
        {
            val node: org.graphstream.graph.Node = g.addNode(crt.definitionName)
            node.addAttribute("ui.label", crt.definitionName)

            println("\t" * level + crt.definitionName)
            println(s"${"\t" * level}{")
            parseIO(crt, level + 1, g)
            println(s"${"\t" * level}}")

            crt.children.foreach((crt: Component) => parseComponent(crt, level + 1, g))
        }

        def addEdge(graph: Graph, from: String, to: String, start: Int = 0, label: String = ""): Unit =
        {
            if (graph.getEdge(from + to + start) == null)
            {
                val edge: org.graphstream.graph.Edge = graph.addEdge(from + to + start.toString, to, from, true)
                edge.addAttribute("ui.label", label)
                edge.addAttribute("ui.style", "text-background-mode : plain;")
                //for java/scala interaction
                //noinspection RemoveRedundantReturn
                return
            }
            else
                addEdge(graph, from, to, start = start + 1, label = label)

        }

        val g: Graph = new MultiGraph("Basic visualization")

        parseComponent(root, 0, g)

        g.display(true)
    }
}
