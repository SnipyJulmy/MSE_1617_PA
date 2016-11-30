package klughdl.core.utils

import java.io.{File, PrintWriter}

import scala.util.{Failure, Success, Try}

/**
  * KlugHDL
  * Created by snipy on 10.11.16.
  */
case class FileManager(filename : String, targetDirectory : String, deleteTargetBeforeRun : Boolean = false) {
  
  private val directory : File = new File(targetDirectory)
  if (!directory.exists()) {
    directory.mkdir()
  }
  else if (!directory.isDirectory) {
    throw new IllegalArgumentException(s"$targetDirectory is not a directory !")
  }
  
  private val completeFileName = s"$targetDirectory/$filename"
  private val outputFile = new File(completeFileName)
  
  if (deleteTargetBeforeRun) {
    if (outputFile.isFile && outputFile.exists()) outputFile.delete()
  }
  
  if (!outputFile.exists()) outputFile.createNewFile()
  private val pw = new PrintWriter(outputFile)
  
  def println(str : String) : FileManager = print(str + "\n")
  
  def print(str : String) : FileManager = {
    Try {
      pw.write(str)
      this
    } match {
      case Failure(exception) =>
        exception.printStackTrace(System.err)
        this
      case Success(value) =>
        value
    }
  }
  
  def close() : FileManager = {
    pw.flush()
    pw.close()
    this
  }
}
