import spinal.core._

/**
  * Created by snipy on 04.10.16.
  */
class BasicComponent extends Component
{
    val andGate = new AndGate

    val io = new Bundle
    {
        val a = in Bool
        val b = in Bool
        val c = out Bool
    }

    andGate.io.a := io.a
    andGate.io.b := io.b
    io.c := andGate.io.c
}
