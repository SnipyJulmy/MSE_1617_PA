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
        def parseIO(component: Component, level : Int): Unit =
        {
            component.getAllIo.foreach(n => println(s"${"\t" * level}${n.toString()}"))
        }

        def parseComponent(crt : Component, level : Int) : Unit =
        {
            println("\t" * level + crt.definitionName)
            println(s"${"\t" * level }{")
            parseIO(crt,level + 1)
            println(s"${"\t" * level }}")

            crt.children.foreach(parseComponent(_,level + 1))
        }

        parseComponent(root,0)
    }
}
