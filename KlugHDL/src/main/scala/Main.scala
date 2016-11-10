import spinal.core.SpinalConfig

/**
  * KlugHDL
  * Created by snipy on 10.11.16.
  */
object Main
{
    def main(args: Array[String])
    {
        val report = SpinalConfig(targetDirectory = "vhdl").generateVhdl(new HierarchicComponent)
    }
}
