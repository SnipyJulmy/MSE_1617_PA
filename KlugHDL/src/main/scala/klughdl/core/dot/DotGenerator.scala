package klughdl.core.dot

import java.io.File

import klughdl.core.model.{DiagramsIO, KlugHDLComponentBasic, Model}
import klughdl.core.utils.{DotConverterTools, FileManager}

/**
  * KlugHDL
  * Created by snipy on 24.11.16.
  */
case class DotGenerator(filename : String, targetDirectory : String) {
  
  val filePath : String = s"$targetDirectory/$filename"
  
  def generatePDfDiagramByParent() : DotGenerator = {
    
    // Generate a diagram for each parent in the graph
    Model.diagrams.foreach { d =>
      val outputFileName =
        if (d.parent == null) s"${filename}_null.dot"
        else s"${filename}_${d.parent.definitionName}.dot"
      
      val outputFile = new File(s"$targetDirectory/$outputFileName")
      val fileManager : FileManager = FileManager(outputFileName, targetDirectory)
      fileManager.println("digraph g {")
      fileManager.println("graph [rankdir=LR,ranksep=\"2\",nodesep=\"2\"];")
      fileManager.println("node [shape=record];")
      
      // Generate the node with port
      fileManager.println {
        d.components.map { entry =>
          entry._2 match {
            case KlugHDLComponentBasic(name, component, parent) =>
              s"""${entry._2.name} [label="{{${entry._2.inputDotPort()}}|${entry._2.name}|{${entry._2.outputDotPort()}}}"];"""
            case DiagramsIO(name, parent) => s""""""
          }
        }.mkString("\n")
      }
      
      /*
       *  Now we generate the parent but with two nodes :
       *  - the inputs of the parent component which are outputs in the diagram
       *  - the outputs of the parent component which are inputs in the diagram
       */
      
      // TODO
      
      /*
       *  Generate the connection between the nodes
       *  the connection who only consider the brother nodes
       *  are generating
       */
      
      /*
      fileManager.println {
        (for {
          entry <- d.connections
          value <- entry._2
        } yield
          s"""${entry._1._1.name}:${entry._1._2.dotName} -> ${value._1.name}:${value._2.dotName};""")
          .toList
          .distinct
          .mkString("\n")
      }
      */
      
      fileManager.println("}")
      fileManager.close()
      DotConverterTools.generatePdfFile(outputFile.getAbsolutePath)
    }
    
    this
  }
}
