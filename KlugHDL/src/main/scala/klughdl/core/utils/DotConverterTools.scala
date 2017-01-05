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

package klughdl.core.utils

import java.io.File

import klughdl.core.backend.dot.DotOutputType
import klughdl.core.backend.dot.DotOutputType.DotOutputType
import klughdl.core.backend.dot.DotProgram.DotProgram
import klughdl.core.backend.dot.DotProgram._

import scala.sys.process._

// TODO move to the backend
object DotConverterTools {
  
  
  // TODO exec query don't work and don't know why
  def generatePdfFile(filepath : String, overwrite : Boolean = true, prg : DotProgram = dot) : Unit = {
    val srcFile = new File(filepath)
    if (!srcFile.exists()) {
      System.err.println(s"file $filepath does not exist")
    } else {
      execDotProg(srcFile, prg)
    }
  }
  
  private def execDotProg(src : File, prog : DotProgram = dot, outputType : DotOutputType = DotOutputType.pdf) : Unit = {
    val outputFile = new File(s"${src.getAbsolutePath}.$prog.$outputType")
    val query = s"$prog -T$outputType ${src.getAbsolutePath} -o ${outputFile.getAbsolutePath}"
    println(query)
    
    val result = query !!
    
    if (!result.isEmpty) {
      System.err.println(result)
    }
  }
  
  def generateLayoutFile(filepath : String, overwrite : Boolean = true) : Unit = {
    ???
  }
}



