package jtamaro.en;

import jtamaro.en.graphic.Above;
import jtamaro.en.graphic.Beside;
import jtamaro.en.graphic.CircularSector;
import jtamaro.en.graphic.Compose;
import jtamaro.en.graphic.Ellipse;
import jtamaro.en.graphic.EmptyGraphic;
import jtamaro.en.graphic.EquilateralTriangle;
import jtamaro.en.graphic.Overlay;
import jtamaro.en.graphic.Pin;
import jtamaro.en.graphic.Rectangle;
import jtamaro.en.graphic.Rotate;
import jtamaro.en.graphic.Text;
import jtamaro.en.graphic.Triangle;


public final class Graphics {

  // prevent instantiation
  private Graphics() {
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

  //OLD
  public static Graphic equilateralTriangle(double side, Color color) {
    return new EquilateralTriangle(side, color);
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
