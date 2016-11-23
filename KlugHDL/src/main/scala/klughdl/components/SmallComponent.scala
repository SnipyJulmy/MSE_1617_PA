package klughdl.components

import spinal.core._

/**
  * KlugHDL
  * Created by snipy on 21.11.16.
  */
class SmallComponent extends Component {
  
  val io = new Bundle {
    val a : Bool = in Bool
    val b : Bool = in Bool
    val c : Bool = out Bool
  }
  
  val andGate = new AndGate
  andGate.io.a := io.a
  andGate.io.b := io.b
  
  val orGate = new OrGate
  orGate.io.a := io.a
  orGate.io.b := io.b
  
  io.c := orGate.io.c & andGate.io.c
}
