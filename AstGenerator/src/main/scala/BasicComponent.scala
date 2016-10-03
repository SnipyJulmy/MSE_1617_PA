import spinal.core._

/**
  * Created by snipy on 02.10.16.
  */

class BasicComponent extends Component
{
    val andGate = new AndGate
    val xorGate = new XorGate
    val andXorGate1 = new AndXor
    val orGate = new OrGate
    val andXorGate2 = new AndXor

    val io = new Bundle
    {
        val a = in Bool
        val b = in Bool
        val c = in Bool
        val d = out Bool
        val e = out Bool
    }

    andGate.io.a := io.a
    andGate.io.b := io.b
    orGate.io.a := io.a
    orGate.io.b := io.c
    xorGate.io.a := io.b
    xorGate.io.b := io.c

    andXorGate1.io.a := io.a
    andXorGate1.io.b := io.b

    andXorGate2.io.a := io.a
    andXorGate2.io.b := io.b

    io.d := andGate.io.c & xorGate.io.c & orGate.io.c
    io.e := andXorGate1.io.c | andXorGate1.io.d | andXorGate2.io.c | andXorGate2.io.d
}

class AndGate extends Component
{
    val io = new Bundle
    {
        val a = in Bool
        val b = in Bool
        val c = out Bool
    }

    io.c := io.a & io.b
}

class OrGate extends Component
{
    val io = new Bundle
    {
        val a = in Bool
        val b = in Bool
        val c = out Bool
    }

    io.c := io.a & io.b
}

class XorGate extends Component
{
    val io = new Bundle
    {
        val a = in Bool
        val b = in Bool
        val c = out Bool
    }

    io.c := io.a & io.b
}

class AndXor extends Component
{
    val xorGate = new XorGate
    val andGate = new AndGate

    val io = new Bundle
    {
        val a = in Bool
        val b = in Bool
        val c = out Bool
        val d = out Bool
    }

    xorGate.io.a := io.a
    xorGate.io.b := io.b
    andGate.io.a := io.a
    andGate.io.b := io.b

    io.c := xorGate.io.c
    io.d := andGate.io.c
}