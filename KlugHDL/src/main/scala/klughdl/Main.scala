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

package klughdl

import java.io.File

import klughdl.components._
import klughdl.core.backend.json.Json
import klughdl.core.model.Model
import klughdl.core.utils.FileManager
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
    
    val report = SpinalConfig(targetDirectory = "vhdl").generateVhdl(new SmallComponent)
    
    // Build the model
    
    val model = Model(report.toplevel)
    
    val json = Json().generate(model)
    
    FileManager("model.json", "diagrams")
      .println(json)
      .close()
  }
}
