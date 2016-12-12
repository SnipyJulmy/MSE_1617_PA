package klughdl

import java.io.File

import klughdl.components._
import klughdl.core.dot.DotGenerator
import klughdl.core.model.Model
import spinal.core._

/**
  * KlugHDL
  * Created by snipy on 10.11.16.
  */
object Main {
  
  def main(args : Array[String]) {
    
    val vhdlOutputDir : File = new File("vhdl")
    if (!vhdlOutputDir.exists())
      vhdlOutputDir.mkdir()
    else if (!vhdlOutputDir.isDirectory)
      System.err.println(s"$vhdlOutputDir is not a directory")
    
    val report = SpinalConfig(targetDirectory = "vhdl").generateVhdl(new HierarchicComponent)
    
    Model(report.toplevel).diagrams.foreach(d => d.generateDot(targetDirectory = "dot"))
    
    /*
    DotGenerator("output.dot", "dot")
      .generatePDfDiagramByParent()
     */
    
    /*
    val contents : String = klughdl.html.index(model).toString()
    
    FileManager("index.html", "diagrams")
      .println(contents)
      .close()
      */
  }
}
