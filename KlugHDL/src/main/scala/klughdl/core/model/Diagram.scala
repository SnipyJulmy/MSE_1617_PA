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

import scala.collection.mutable

class Diagram(val parent: Component) {

  var components: Map[Component, KlugHDLComponent] = Map()
  var connections = new mutable.HashMap[(KlugHDLComponent, Port), mutable.Set[(KlugHDLComponent, Port)]]
    with mutable.MultiMap[(KlugHDLComponent, Port), (KlugHDLComponent, Port)]

  override def toString: String = {
    s"Diagram[${parent.definitionName}]" + components.map(entry => s"${entry._2}") mkString "\n"
  }

  override def equals(o: scala.Any): Boolean = o match {
    case d: Diagram => this.parent == d.parent
    case _ => super.equals(o)
  }

  protected[model] def foreachChildren(f: (Component) => Unit, topLevel: Component): Unit = {
    if (parent != null) parent.children.foreach(f)
    // only 1 children --> toplevel !
    else f(topLevel)
  }

  protected[model] def addComponents(component: Component): Unit = {
    components += (component -> KlugHDLComponentBasic(component.definitionName, component, component.parent))
  }

  // add the component which correspond to the parent of the diagrams
  // -> input and output from/to the external world of the component
  protected[model] def addIoComponents(component: Component): Unit = {
    if (component == null)
      components += (component -> KlugHDLComponentIO("NULL", component))
    else
      components += (component -> KlugHDLComponentIO(component.definitionName, component))
  }

  protected[model] def addPort(component: Component, port: Port): Unit = {
    components(component).addPort(port)
  }

  protected[model] def addConnection(from: Component, portFrom: Port, to: Component, portTo: Port): Unit = {
    connections.addBinding((components(from), portFrom), (components(to), portTo))
  }

  protected[model] def addConnection(con: (Component, Port, Component, Port)): Unit = {
    if (components.contains(con._1) && components.contains(con._3))
      connections.addBinding((components(con._1), con._2), (components(con._3), con._4))
  }
}
