package klughdl.analyzer

import klughdl.components.SmallComponent

/**
  * Created by snipy
  * Project KlugHDL
  */
object Main{
  def main(args: Array[String]): Unit = {
    val analyzer = new KlugHDL
    analyzer.analyze(new SmallComponent)
  }
}
