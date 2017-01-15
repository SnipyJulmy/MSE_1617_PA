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

package klughdl.core.backend.json

import klughdl.core.backend.Backend
import klughdl.core.model._
import klughdl.core.utils.FileManager
import net.liftweb.json.JsonDSL._
import net.liftweb.json._
import spinal.core.Component

case class Json(targetDirectory: String = "json") extends Backend {

  private val extInput = "EXTERNAL_INPUT"
  private val extOutput = "EXTERNAL_OUTPUT"

  def generateJsonModel(model: Model) : Unit = {
    val json = generateJson(model)
    FileManager("model.json",targetDirectory)
      .println(pretty(render(json)))
      .close()
  }

  override def generate(model: Model): String = {
    val json = generateJson(model)
    pretty(render(json))
  }

  private def generateJson(model: Model): JValue = {
    ("tree" -> generateTree(model)) ~
      ("model" -> model.diagrams.map("diagram" -> generateJson(_))) ~
      ("extInput" -> s"$extInput") ~
      ("extOutput" -> s"$extOutput")
  }

  private def generateTree(model: Model): JValue = {
    def inner(component: Component): JValue = {

      if (component.children.nonEmpty) {
        ("text" -> component.definitionName) ~
          ("nodes" -> component.children.map(inner))
      }
      else "text" -> component.definitionName

    }

    inner(model.topLevel)
  }

  private def generateJson(diagram: Diagram): JValue = {
    val parentName = if (diagram.parent == null) "null" else diagram.parent.definitionName
    ("name" -> parentName) ~
      ("isTopLevel" -> s"${diagram.parent == null}") ~
      ("components" -> diagram.components.map(c => generateJson(c._2))) ~
      ("connections" -> (for {
        entry <- diagram.connections
        value <- entry._2
        if !entry._1._1.isIO && !value._1.isIO
        if entry._1._1.component != diagram.parent
        if entry._1._1.parent == value._1.parent
        if entry._1._1 != value._1
      } yield ("from" -> ("name" -> s"${entry._1._1.name}") ~ ("port" -> s"${entry._1._2.name}")) ~
        ("to" -> ("name" -> s"${value._1.name}") ~ ("port" -> s"${value._2.name}")))) ~
      ("inputs" -> (for {
        entry <- diagram.connections
        value <- entry._2
        if entry._1._1.isIO
        if !value._1.isIO
      } yield ("from" -> ("name" -> s"$extInput") ~ ("port" -> s"${entry._1._2.name}")) ~
        ("to" -> ("name" -> s"${value._1.name}") ~ ("port" -> s"${value._2.name}")))) ~
      ("outputs" -> (for {
        entry <- diagram.connections
        value <- entry._2
        if value._1.isIO
        if !entry._1._1.isIO
      } yield ("from" -> ("name" -> s"${entry._1._1.name}") ~ ("port" -> s"${entry._1._2.name}")) ~
        ("to" -> ("name" -> s"$extOutput") ~ ("port" -> s"${value._2.name}"))))
  }

  private def generateJson(klugHDLComponent: KlugHDLComponent): JValue = klugHDLComponent match {
    case KlugHDLComponentBasic(name, _, _) =>
      ("name" -> name) ~
        ("type" -> "default") ~
        ("ports" -> klugHDLComponent.ports.map(generateJson))
    case KlugHDLComponentIO(name, _) =>
      ("name" -> name) ~
        ("type" -> "io") ~
        ("ports" -> klugHDLComponent.ports.map(generateJson))
  }

  private def generateJson(port: Port): JValue = port match {
    case InputPort(name, hdlType) =>
      ("name" -> name) ~
        ("type" -> hdlType) ~
        ("portType" -> "input")
    case OutputPort(name, hdlType) =>
      ("name" -> name) ~
        ("type" -> hdlType) ~
        ("portType" -> "output")
  }

  override def generate(diagram: Diagram): String = {
    val json = generateJson(diagram)
    pretty(render(json))
  }
}
