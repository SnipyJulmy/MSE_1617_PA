package klughdl.core

import java.io.{File, PrintWriter}

/**
  * KlugHDL
  * Created by snipy on 10.11.16.
  */
case class FileManager(filename: String, directoryPath: String) {
  
  private val directory: File = new File(directoryPath)
  if (!directory.exists()) {
    directory.mkdir()
  }
  else if (!directory.isDirectory) {
    throw new IllegalArgumentException(s"$directoryPath is not a directory !")
  }
  
  private val completeFileName = s"$directoryPath/$filename"
  private val outputFile = new File(completeFileName)
  if (!outputFile.exists()) outputFile.createNewFile()
  private val pw = new PrintWriter(outputFile)
  
  def println(str: String): FileManager = {
    pw.write(str + "\n")
    this
  }
  
  def print(str: String): FileManager = {
    pw.write(str)
    this
  }
  
  def close(): FileManager = {
    pw.flush()
    pw.close()
    this
  }
}
