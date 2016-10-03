import spinal.core.SpinalConfig
import spinal.core._

/**
  * Created by snipy on 02.10.16.
  */

//noinspection FieldFromDelayedInit
object Main
{
    def main(args: Array[String])
    {
        val report = SpinalConfig(targetDirectory = "vhdl").generateVhdl(new BasicComponent)
        parseAstTree(report.toplevel)
    }

    def parseAstTree(root: Component): Unit =
    {
        def parseComponent(crt : Component, level : Int) : Unit =
        {
            println("\t" * level + crt.definitionName)
            crt.children.foreach(parseComponent(_,level + 1))
        }

        parseComponent(root,0)
    }
}
