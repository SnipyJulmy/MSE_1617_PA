package dotGenerator

import java.io.File
import sys.process._

/**
  * Created by snipy on 07.10.16.
  */
case class Dot2PdfGenerator(filepath: String)
{
    def generatePdfFile(): Dot2PdfGenerator =
    {
        val file = new File(filepath)
        if (file.exists())
        {
            val output = filepath.replace(".dot",".pdf")
            // val result = s"dot -Tpdf $filepath -o $output " !!
            val result = s"circo -Tpdf $filepath -o $output " !!

            if (!result.isEmpty)
            {
                System.err.println(result)
            }
        }
        else
            System.err.println
            {
                s"No dot file named $filepath found \n call generateDotFile()"
            }
        this
    }
}
