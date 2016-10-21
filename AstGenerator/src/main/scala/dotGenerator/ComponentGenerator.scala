package dotGenerator

import spinal.core.{BaseType, Component, Node}

import scala.language.implicitConversions

/**
  * Dot DSL
  * Created by snipy on 14.10.16.
  */

case class ComponentGenerator(component: Component, filename: String, targetDirectory: String)
{
    private val fileCompletePath = s"$targetDirectory/$filename"
    private val dotFileManager = DotFileManager(filename = filename, directoryPath = targetDirectory)

    def generateDotFile(): ComponentGenerator =
    {
        // Graph delcaration and option
        dotFileManager.write("digraph G {")
        dotFileManager.write("graph [rankdir = LR];")
        dotFileManager.write("node[shape=record];")

        // add Input and Output node for the component
        val input = parseIO(component, _.isInput)

        val inputNode = s"""InputNode [rank=0,label ="{Input|{${input.mkString("|")}}}"];"""
        dotFileManager.write(inputNode)

        val output = parseIO(component, _.isOutput)

        val outputNode = s"""OutputNode [rank=2,label="{{${output.mkString("|")}}|Output}"];"""
        dotFileManager.write(outputNode)

        // Parse all component
        component.children.foreach
        { c =>
            val componentInput = parseIO(c, _.isInput)
            val componentOutput = parseIO(c, _.isOutput)
            val componentNode =
                s""" ${c.definitionName}[label="{${componentInput.mkString("|")}}
                    #|${c.toString()}|
                    #{${componentOutput.mkString("|")}}"];"""
                    .stripMargin('#')
                    .replaceAll("\n", "")
            dotFileManager.write(componentNode)
        }

        // Parse edge
        component.children.foreach // foreach children component
        { c =>
            c.getAllIo.filter(_.isOutput).foreach // foreach output of the children component
            { o =>
                o.consumers.foreach // for each consumer of the output
                { c =>
                    println(mkEdge(o, c))
                    // dotFileManager.write(edge)
                }
            }
        }

        // Close bracket and file
        dotFileManager.write("}")
        dotFileManager.close()
        this
    }

    private def mkEdge(a: Node, b: Node): String =
    {
        val pathFrom = pathArray(a)
        val from = s"${pathFrom(pathFrom.length - 2)}:${pathFrom.last.replaceAll("_", ".")}"

        val to =
        {
            if (b.component.parent == null) // root component
            {
                // TODO
                s"${a.toString()} ${b.toString()}"
            }
            else
            {
                val pathTo = pathArray(b)
                s"${pathTo(pathTo.length - 2)}:${pathTo.last.replaceAll("_", ".")}"
            }
        }

        s"""$from -> $to"""
    }

    private def pathArray(node: Node): Array[String] =
    {
        node.toString().split(":")(0).split("/")
    }

    private def parseIO(component: Component, p: (BaseType) => Boolean): Set[String] =
    {
        component
            .getAllIo.filter(p(_)) // get all input
            .map(b => b.toString()) // convert to string
            .map(s => s.split(":")(0)) // keep all before the :
            .map(s => s.split("/").last) // keep all after the last /
            .map(s => s.replaceAll(" ", "")) // remove whitespace
            .map(s => s.replaceAll("_", ".")) // replace _ by .
            .map(s => s"<$s>$s")
            .toSet
    }

    def generatePdfFile(): ComponentGenerator =
    {
        Dot2PdfGenerator(fileCompletePath).generatePdfFile()
        this
    }
}
