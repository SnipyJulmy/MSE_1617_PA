package klughdl.core.dot

import klughdl.core.utils.{DotConverterTools, FileManager}
import klughdl.core.{KlugHDLConnection, KlugHDLModel}

/**
  * KlugHDL
  * Created by snipy on 24.11.16.
  */
case class DotGenerator(model : KlugHDLModel, filename : String, targetDirectory : String) {
  
  val filePath : String = s"$targetDirectory/$filename"
  
  def generateDotFile() : DotGenerator = {
    
    val fileManager : FileManager = FileManager(filename, targetDirectory)
    fileManager.println("digraph g {")
    fileManager.println("graph [rankdir=LR,ranksep=\"1\",nodesep=\"1\"];")
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
