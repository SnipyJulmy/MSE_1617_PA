package dotGenerator

import org.graphstream.graph.implementations.MultiGraph
import spinal.core.Component

/**
  * Created by snipy on 06.10.16.
  */

case class SpinalDotGenerator(targetDirectory: String, filename: String, rootComponent: Component)
{
    private type GNode = org.graphstream.graph.Node
    private type GEdge = org.graphstream.graph.Edge

    private val fileCompletePath = s"$targetDirectory/$filename"
    private val graph: org.graphstream.graph.Graph = new MultiGraph(filename)
    private val dotFileManager = DotFileManager(filename = filename, directoryPath = targetDirectory)

    private val rI = "RootComponentInput"
    private val rO = "RootComponentOutput"


    def generateDotFile(): SpinalDotGenerator =
    {
        // Add root component input
        val nRootInput: GNode = graph.addNode(rI)
        nRootInput.addAttribute("label", rI)
        nRootInput.addAttribute("name", rI)

        // Add root component output
        val nRootOutput: GNode = graph.addNode(rO)
        nRootOutput.addAttribute("label", rO)
        nRootOutput.addAttribute("name", rO)

        parseComponent(rootComponent)
        parseIO(rootComponent)
        parseRootIO(rootComponent)

        val totNode = graph.getNodeCount
        for (i <- 0 until totNode)
        {
            addDotNode(graph.getNode(i))
        }
        dotFileManager.close()
        this
    }

    /*
        Assume generateDotFile() has been already call
     */
    def generatePdfFile(): SpinalDotGenerator =
    {
        Dot2PdfGenerator(fileCompletePath).generatePdfFile()
        this
    }

    private def parseRootIO(root: Component): Unit =
    {
        root.getAllIo.foreach
        { n =>
            if (n.isInput)
            {
                println(root.definitionName)
                addEdge(rI, root.definitionName)
            }
            else if (n.isOutput)
            {
                println(root.definitionName)
                addEdge(root.definitionName, rO)
            }
        }
    }

    private def parseIO(component: Component): Unit =
    {
        println(s"parse io of ${component.definitionName}")
        component.getAllIo.foreach
        { n =>
            if (n.isInput)
            {
                if (n.getInput(0) != null)
                {
                    addEdge(n.getInput(0).component.definitionName, component.definitionName)
                }
            }
            else if (n.isOutput)
            {
                n.consumers.foreach
                { n_consumers: spinal.core.Node =>
                    addEdge(n.component.definitionName, n_consumers.component.definitionName)
                }
            }
            else
            {
                System.err.println("IO not input or output")
            }
        }

        component.children.foreach(parseIO)
    }

    private def parseComponent(component: Component): Unit =
    {
        println(s"${component.definitionName}")
        component.children.foreach(parseComponent)
        val n: GNode = graph.addNode(component.definitionName)
        n.addAttribute("label", component.definitionName)
        n.addAttribute("name", component.definitionName)
    }

    private def addDotNode(n: GNode): Unit =
    {
        dotFileManager.write(s"""${n.getAttribute("name")} [label="${n.getAttribute("label")}", shape=box]""")
    }

    private def addEdge(from: String, to: String, start: Int = 0): Unit =
    {
        if (graph.getEdge(from + to + start) == null)
        {
            graph.addEdge(from + to + start.toString, from, to, true)
            val edgeLabel = """""""" // label = ""
            dotFileManager.write(s"""$from -> $to[label = $edgeLabel];""")
            //for java/scala interaction
            //noinspection RemoveRedundantReturn
            return
        }
        else
            addEdge(from, to, start = start + 1)
    }
}
