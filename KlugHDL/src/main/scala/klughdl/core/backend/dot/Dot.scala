/*
 *
 * Copyright (c) 2016  Sylvain Julmy
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; see the file COPYING. If not, write to the
 * Free Software Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

package klughdl.core.backend.dot

import java.io.File

import klughdl.core.backend.Backend
import klughdl.core.model._
import klughdl.core.utils.{DotConverterTools, FileManager}

/**
  * KlugHDL
  * Created by snipy on 24.11.16.
  */
case class Dot(targetDirectory : String) extends Backend{
  
  // TODO
  override def generate(model : Model) : String = ???
  
  // TODO
  override def generate(diagram : Diagram) = ???
  
  def generatePDfDiagramByParent(model: Model) : Dot = {
    
    // Generate a diagram for each parent in the graph
    model.diagrams.foreach {
      generateDiagram
    }
    this
  }
  
  
  def generateDiagram(diagram : Diagram) : Dot = {
    
    val extInput = "EXTERNAL_INPUT"
    val extOutput = "EXTERNAL_OUTPUT"
    
    val outputFileName = {
      if (diagram.parent == null) s"null.dot"
      else s"${diagram.parent.definitionName}.dot"
    }
    
    val outputFile = new File(s"$targetDirectory/$outputFileName")
    val fileManager : FileManager = FileManager(outputFileName, targetDirectory)
    
    // Generate basic information fot the dot diagram
    fileManager.println("digraph g {")
    fileManager.println("graph [rankdir=LR,ranksep=\"2\",nodesep=\"2\"];")
    fileManager.println("node [shape=record];")
    
    
    /*
     * Now we generate :
     * - the node with port
     * - the inputs of the parent component which are outputs in the diagram
     * - the outputs of the parent component which are inputs in the diagram
     */
    
    fileManager.println {
      diagram.components.map { entry =>
        entry._2 match {
          case KlugHDLComponentBasic(_, _, _) =>
            s"""${entry._2.name} [label="{{${entry._2.inputDotPort()}}|${entry._2.name}|{${entry._2.outputDotPort()}}}"];"""
          case KlugHDLComponentIO(name, parent) =>
            s"""
               |$extInput [label="{$extInput|{${entry._2.inputDotPort()}}}"];
               |$extOutput [label="{{${entry._2.outputDotPort()}}|$extOutput}"];
             """.stripMargin
        }
      }.mkString("\n")
    }
  
    /*
     *  Generate the connection between the nodes
     *  the connection who only consider the brother nodes
     *  are generating
     */
    fileManager.println {
      (for {
        entry <- diagram.connections
        value <- entry._2
        if !entry._1._1.isIO && !value._1.isIO
        if entry._1._1.component != diagram.parent
        if entry._1._1.parent == value._1.parent
        if entry._1._1 != value._1
      } yield
        s"""${entry._1._1.name}:${entry._1._2.dotName} -> ${value._1.name}:${value._2.dotName};""")
        .toList
        .distinct
        .mkString("\n")
    }
    
    /*
     * Generate the connection from the
     * inputs/outputs of the external world
     */
    
    // inputs
    fileManager.println {
      (for {
        entry <- diagram.connections
        value <- entry._2
        if entry._1._1.isIO
        if !value._1.isIO
      } yield
        s"""$extInput:${entry._1._2.dotName} -> ${value._1.name}:${value._2.dotName};""")
        .toList
        .distinct
        .mkString("\n")
    }
    
    // outputs
    fileManager.println {
      (for {
        entry <- diagram.connections
        value <- entry._2
        if value._1.isIO
        if !entry._1._1.isIO
      } yield
        s"""${entry._1._1.name}:${entry._1._2.dotName} -> $extOutput:${value._2.dotName};""")
        .toList
        .distinct
        .mkString("\n")
    }
    
    fileManager.println("}")
    fileManager.close()
    DotConverterTools.generatePdfFile(outputFile.getAbsolutePath)
    this
  }
}
