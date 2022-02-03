package jtamaro.en.fun;

import jtamaro.en.Graphic;
import jtamaro.en.oo.Rotate;


public class Op {

  // prevent instantiation
  private Op() {
  }
    
  //-- nullary graphic operations
  public Graphic emptyGraphic() {
    return new EmptyGraphic();
  }

  //-- unary graphic operations
  public Graphic rotate(double degrees, Graphic graphic) {
    return new Rotate(degrees, graphic);
  }

}
