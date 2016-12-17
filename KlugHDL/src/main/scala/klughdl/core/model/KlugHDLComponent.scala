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

sealed trait KlugHDLComponent {
  
  val name : String
  val parent : Component
  val component : Component = this match {
    case KlugHDLComponentBasic(_, c, _) => c
    case KlugHDLComponentIO(_, _) => null
  }
  val id : String = if (parent == null) s"${parent}_$name" else s"${parent.definitionName}_$name"
  val idStr : String = s""""$id""""
  var ports : Set[Port] = Set()
  
  def isIO : Boolean = this match {
    case KlugHDLComponentBasic(_, _, _) => false
    case KlugHDLComponentIO(_, _) => true
  }
  
  def addPort(port : Port) : Unit = {
    ports += port
  }
}

final case class KlugHDLComponentBasic(name : String, override val component : Component, parent : Component) extends KlugHDLComponent

final case class KlugHDLComponentIO(name : String, parent : Component) extends KlugHDLComponent
