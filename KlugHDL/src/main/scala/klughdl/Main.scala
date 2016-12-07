package klughdl

import java.io.File

import klughdl.components._
import klughdl.core.dot.DotGenerator
import klughdl.core.model.Model
import klughdl.core.old.{KlugHDLModel, Port}
import spinal.core._

/**
  * KlugHDL
  * Created by snipy on 10.11.16.
  */
object Main {
  
  val model : KlugHDLModel = new KlugHDLModel()
  
  def main(args : Array[String]) {
    
    val vhdlOutputDir : File = new File("vhdl")
    if (!vhdlOutputDir.exists())
      vhdlOutputDir.mkdir()
    else if (!vhdlOutputDir.isDirectory)
      System.err.println(s"$vhdlOutputDir is not a directory")
    
    val report = SpinalConfig(targetDirectory = "vhdl").generateVhdl(new OneLevelComponent)
    
    Model(report.toplevel).diagrams.foreach(d => println(d.parent))
    
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
