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

package klughdl.core.model

import spinal.core._

sealed trait Port {
  
  val name : String
  val hdlType : String
  lazy val dotName : String = name.replaceAll("\\.", "")
  
  
  
  def isInput : Boolean = this match {
    case InputPort(_, _) => true
    case OutputPort(_, _) => false
  }
  
  def isOutput : Boolean = this match {
    case InputPort(_, _) => false
    case OutputPort(_, _) => true
  }
  
  def getType : String = this match {
    case _ : InputPort => "input"
    case _ : OutputPort => "output"
  }
}

object Port {
  
  def parsePort(rawPort : String) : String = {
    val tmp = rawPort
      .replaceAll(" ", "")
      .split(":").head
      .split("/").last
      .split("_")
    s"${tmp(tmp.length - 2)}.${tmp(tmp.length - 1)}"
  }
  
  def apply(bt : BaseType) : Port = {
    def nameIoAndType(baseType : BaseType) : (String, String, String) = {
      val full = baseType.toString().split("/").last
      val name = full.split(":").head.replaceAll(" ", "").replaceAll("_", ".")
      val ioType = full.split(":").last.replaceFirst(" ", "").split(" ")
      (name, ioType(0), if (ioType(1) == "input") "output" else "input")
    }
    
    val (n, io, t) = nameIoAndType(bt)
    Port(n, io, t)
  }
  
  def apply(name : String, io : String, hdlType : String) : Port = io match {
    case "input" | "in" => InputPort(name, hdlType)
    case "output" | "out" => OutputPort(name, hdlType)
  }
}

final case class InputPort(name : String, hdlType : String) extends Port

final case class OutputPort(name : String, hdlType : String) extends Port
