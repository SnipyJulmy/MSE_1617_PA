package klughdl.core.dot

import java.io.File

import klughdl.core.utils.{DotConverterTools, FileManager}
import klughdl.core.{KlugHDLConnection, KlugHDLModel}

/**
  * KlugHDL
  * Created by snipy on 24.11.16.
  */
case class DotGenerator(model : KlugHDLModel, filename : String, targetDirectory : String) {
  
  val filePath : String = s"$targetDirectory/$filename"
  
  def generatePDfDiagramByParent() : DotGenerator = {
    model.parents.foreach(p => println(s"PARENT : $p"))
    
    model.allChildrenComponent(null).foreach(k => println(s"null : $k"))
    
    model.parents.foreach { p =>
      // Generate a diagram for each parent in the graph
      val outputFileName =
        if (p == null) s"${filename}_null.dot"
        else s"${filename}_${p.definitionName}.dot"
      val outputFile = new File(s"$targetDirectory/$outputFileName")
      val fileManager : FileManager = FileManager(outputFileName, targetDirectory)
      fileManager.println("digraph g {")
      fileManager.println("graph [rankdir=LR,ranksep=\"2\",nodesep=\"2\"];")
      fileManager.println("node [shape=record];")
      
      // Generate the node with port
      fileManager.println {
        model.allChildrenComponent(p).map { c =>
          s"""${c.name} [label="{{${c.inputDotPort()}}|${c.name}|{${c.outputDotPort()}}}"];"""
        }.mkString("\n")
      }
      
      // TODO generate Input and Output of the parent
      
      for {
          entry <- model.connections
          value <- entry._2
          if entry._1._1.parent == p
          if value._1.parent == p
      } {
        println(s"${entry._1._1}[${entry._1._2}] -> ${value._1}[${value._2}]")
      }
      
      // Generate the connection between the nodes
      // the connection who only consider the brother nodes
      // are generating
      fileManager.println {
        (for {
          entry <- model.connections
          value <- entry._2
          if entry._1._1.parent == p
          if value._1.parent == p
        } yield KlugHDLConnection(entry._1, value))
          .toList
          .distinct
          .map(_.toDot)
          .mkString("\n")
      }
      
      fileManager.println("}")
      fileManager.close()
      
      DotConverterTools.generatePdfFile(outputFile.getAbsolutePath)
    }
    this
  }
  
  def generateDotFileFull() : DotGenerator = {
    
    val fileManager : FileManager = FileManager(filename, targetDirectory)
    fileManager.println("digraph g {")
    fileManager.println("graph [rankdir=LR,ranksep=\"2\",nodesep=\"2\"];")
    fileManager.println("node [shape=record];")
    
    // generate node with port
    fileManager.println {
      model.getKlugHDLComponents.map { c =>
        s"""${c.name} [label="{{${c.inputDotPort()}}|${c.name}|{${c.outputDotPort()}}}"];"""
      }.mkString("\n")
    }
    
    // generate connection
    fileManager.println {
      (for {
        entry <- model.connections
        value <- entry._2
      } yield KlugHDLConnection(entry._1, value))
        .toList
        .distinct
        .map(_.toDot)
        .mkString("\n")
    }
    
    fileManager.println("}")
    fileManager.close()
    this
  }
  
  def generatePdfFile() : DotGenerator = {
    DotConverterTools.generatePdfFile(filePath)
    this
  }
  
}
