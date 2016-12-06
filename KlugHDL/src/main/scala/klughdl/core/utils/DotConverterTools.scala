package klughdl.core.utils

import java.io.File

import klughdl.core.dot.DotOutputType
import klughdl.core.dot.DotOutputType.DotOutputType
import klughdl.core.dot.DotProgram._

import scala.sys.process._

/**
  * KlugHDL
  * Created by snipy on 24.11.16.
  */
object DotConverterTools {
  
  /**
    * TODO
    * @param filepath
    * @param overwrite
    */
  def generatePdfFile(filepath : String, overwrite : Boolean = true, prg : DotProgram = dot) : Unit = {
    val srcFile = new File(filepath)
    if (!srcFile.exists()) {
      System.err.println(s"file $filepath does not exist")
    } else {
      execDotProg(srcFile, prg)
    }
  }
  
  /**
    * TODO
    * @param src
    * @param prog
    * @param outputType
    */
  private def execDotProg(src : File, prog : DotProgram = dot, outputType : DotOutputType = DotOutputType.pdf) : Unit = {
    val outputFile = new File(s"${src.getAbsolutePath}.$prog.$outputType")
    val query = s"$prog -T$outputType ${src.getAbsolutePath} -o ${outputFile.getAbsolutePath}"
    println(query)
    val result = query !!
    
    if (!result.isEmpty) {
      System.err.println(result)
    }
  }
  
  /**
    * TODO
    * @param filepath
    * @param overwrite
    */
  def generateLayoutFile(filepath : String, overwrite : Boolean = true) : Unit = {
    ???
  }
}



