package klughdl.components

import spinal.core._

/**
  * KlugHDL
  * Created by snipy on 05.12.16.
  */
class OneLevelComponent extends Component {
  
  val io = new Bundle {
    val a : Bool = in Bool
    val b : Bool = in Bool
    val c : Bool = in Bool
    val d : Bool = in Bool
    val e : Bool = out Bool
  }
  
  val andGateAB : AndGate = new AndGate
  val andGateCD : AndGate = new AndGate
  val orGate : OrGate = new OrGate
  
  val one2one1 = new One2OneComponent
  val one2one2 = new One2OneComponent
  val one2one3 = new One2OneComponent
  val one2one4 = new One2OneComponent
  val one2one5 = new One2OneComponent
  val one2one6 = new One2OneComponent
  
  andGateAB.io.a := io.a
  andGateAB.io.b := io.b
  andGateCD.io.a := io.c
  andGateCD.io.b := io.d
  
  one2one1.io.i := andGateAB.io.c
  one2one2.io.i := one2one1.io.o
  one2one3.io.i := one2one2.io.o
  orGate.io.a := one2one3.io.o
  
  one2one4.io.i := andGateCD.io.c
  one2one5.io.i := one2one4.io.o
  one2one6.io.i := one2one5.io.o
  orGate.io.b := one2one6.io.o
  
  io.e := orGate.io.c
}

class One2OneComponent extends Component{
  val io = new Bundle{
    val i : Bool = in Bool
    val o : Bool = out Bool
  }
  io.o := io.i
}
