package klughdl.core.backend

import klughdl.core.model.Model

/**
  * Prg 1 - klughdl.core.backend
  * Created by snipy on 16.12.16.
  */
trait Backend {
  
  def generate(model : Model) : String
  
}
