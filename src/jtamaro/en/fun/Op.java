package jtamaro.en.fun;

import jtamaro.en.Color;
import jtamaro.en.Graphic;
import jtamaro.en.oo.CircularSector;
import jtamaro.en.oo.Compose;
import jtamaro.en.oo.Ellipse;
import jtamaro.en.oo.EmptyGraphic;
import jtamaro.en.oo.Rectangle;
import jtamaro.en.oo.Rotate;
import jtamaro.en.oo.Text;
import jtamaro.en.oo.Triangle;


public final class Op {

  // prevent instantiation
  private Op() {
  }
    
  //-- nullary graphic operations
  public Graphic emptyGraphic() {
    return new EmptyGraphic();
  }

  public Graphic circularSector(double radius, double angle, Color color) {
    return new CircularSector(radius, angle, color);
  }

  public Graphic ellipse(double width, double height, Color color) {
    return new Ellipse(width, height, color);
  }

  public Graphic rectangle(double width, double height, Color color) {
    return new Rectangle(width, height, color);
  }

  public Graphic text(String content, String font, double points, Color color) {
    return new Text(content, font, points, color);
  }

  public Graphic triangle(double side, Color color) {
    return new Triangle(side, color);
  }

  //-- unary graphic operations
  public Graphic rotate(double degrees, Graphic graphic) {
    return new Rotate(degrees, graphic);
  }

  //-- binary graphic operations
  public Graphic compose(Graphic foregroundGraphic, Graphic backgroundGraphic) {
    return new Compose(foregroundGraphic, backgroundGraphic);
  }

}
