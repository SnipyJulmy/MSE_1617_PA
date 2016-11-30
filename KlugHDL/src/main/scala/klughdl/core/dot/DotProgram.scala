package klughdl.core.dot

/**
  * KlugHDL
  * Created by snipy on 30.11.16.
  */
object DotProgram extends Enumeration {
  
  type DotProgram = Value
  val dot, neato, circo, fdp, twopi = Value
}