package dotGenerator

import org.graphstream.graph.implementations.MultiGraph
import spinal.core.Component

/**
  * Created by snipy on 06.10.16.
  */

case class SpinalDotGenerator(rootComponent: Component, filename: String, targetDirectory: String)
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
        nRootInput.addAttribute("level",new Integer(0))

        // Add root component output
        val nRootOutput: GNode = graph.addNode(rO)
        nRootOutput.addAttribute("label", rO)
        nRootOutput.addAttribute("name", rO)
        nRootOutput.addAttribute("level",new Integer(0))

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

    private def parseComponent(component: Component, level : Int = 1): Unit =
    {
        println(s"${component.definitionName}")
        component.children.foreach(parseComponent(_,level = level + 1))
        val n: GNode = graph.addNode(component.definitionName)
        n.addAttribute("label", component.definitionName)
        n.addAttribute("name", component.definitionName)
        n.addAttribute("level",new Integer(level))
    }

    private def addDotNode(n: GNode): Unit =
    {
        val label = n.getId match
        {
            case `rI` =>
                s"""label="Top Level Input" """
            case `rO` =>
                s"""label="Top Level Output" """
            case _ =>
                s"""label="${n.getAttribute("label")} | level = ${n.getAttribute("level")}" """
        }

        val shape = n.getId match
        {
            case `rI` =>
                s"""shape=none"""
            case `rO` =>
                s"""shape=none"""
            case _ =>
                s"""shape=box"""
        }

        dotFileManager.write(s"""${n.getAttribute("name")} [$label , $shape]""")
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
