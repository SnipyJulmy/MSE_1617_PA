package klughdl.core.utils

/**
  * KlugHDL
  * Created by snipy on 12.12.16.
  */
object Debug {
  def log(expr: => Unit): Unit = {
    println(s"\n${"=" * 10} BEGIN ${"=" * 10}\n")
    expr
    println(s"\n${"=" * 10}  END  ${"=" * 10}\n")
  }
}
