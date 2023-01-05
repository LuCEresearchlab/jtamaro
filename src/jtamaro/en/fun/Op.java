package jtamaro.en.fun;

import jtamaro.en.Color;
import jtamaro.en.Graphic;
import jtamaro.en.oo.Above;
import jtamaro.en.oo.Beside;
import jtamaro.en.oo.CircularSector;
import jtamaro.en.oo.Compose;
import jtamaro.en.oo.Ellipse;
import jtamaro.en.oo.EmptyGraphic;
import jtamaro.en.oo.Overlay;
import jtamaro.en.oo.Pin;
import jtamaro.en.oo.Rectangle;
import jtamaro.en.oo.Rotate;
import jtamaro.en.oo.Text;
import jtamaro.en.oo.Triangle;


public final class Op {

  // prevent instantiation
  private Op() {
  }
  

  //-- nullary graphic operations
  public static Graphic emptyGraphic() {
    return new EmptyGraphic();
  }

  public static Graphic circularSector(double radius, double angle, Color color) {
    return new CircularSector(radius, angle, color);
  }

  public static Graphic ellipse(double width, double height, Color color) {
    return new Ellipse(width, height, color);
  }

  public static Graphic rectangle(double width, double height, Color color) {
    return new Rectangle(width, height, color);
  }

  public static Graphic text(String content, String font, double points, Color color) {
    return new Text(content, font, points, color);
  }

  public static Graphic triangle(double side, Color color) {
    return new Triangle(side, color);
  }


  //-- unary graphic operations
  public static Graphic rotate(double degrees, Graphic graphic) {
    return new Rotate(degrees, graphic);
  }

  public static Graphic pin(String horizontalPlace, String verticalPlace, Graphic graphic) {
    return new Pin(horizontalPlace, verticalPlace, graphic);
  }

  
  //-- binary graphic operations
  public static Graphic compose(Graphic foregroundGraphic, Graphic backgroundGraphic) {
    return new Compose(foregroundGraphic, backgroundGraphic);
  }

  public static Graphic overlay(Graphic foregroundGraphic, Graphic backgroundGraphic) {
    return new Overlay(foregroundGraphic, backgroundGraphic);
  }

  public static Graphic beside(Graphic leftGraphic, Graphic rightGraphic) {
    return new Beside(leftGraphic, rightGraphic);
  }

  public static Graphic above(Graphic topGraphic, Graphic bottomGraphic) {
    return new Above(topGraphic, bottomGraphic);
  }

}
