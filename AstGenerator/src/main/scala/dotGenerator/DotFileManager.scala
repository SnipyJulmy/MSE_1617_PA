package dotGenerator

import java.io._

/**
  * Created by snipy on 06.10.16.
  *
  * @param filename : complete path to the file
  */
case class DotFileManager(filename: String, directoryPath: String)
{
    private val directory = new File(directoryPath)
    if (!directory.exists())
    {
        directory.mkdir()
    }

    private val completeFileName = s"$directoryPath/$filename"

    private val file = new File(completeFileName)
    if (!file.exists())
        file.createNewFile()
    private val pw = new PrintWriter(file)
    pw.write("digraph g {\n")
    pw.write("splines=ortho\n")

    def write(str: String): DotFileManager =
    {
        pw.write(str + "\n")
        this
    }

    def close(): DotFileManager =
    {
        pw.write("}\n")
        pw.flush()
        pw.close()
        this
    }
}
