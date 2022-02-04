package jtamaro.en.oo;

import jtamaro.en.Graphic;
import jtamaro.internal.representation.GraphicImpl;


public abstract class AbstractGraphic implements Graphic {

  private final GraphicImpl implementation;

  public AbstractGraphic(GraphicImpl implementation) {
    this.implementation = implementation;
  }

  GraphicImpl getImplementation() {
    return implementation;
  }
  
  public double getWidth() {
    return implementation.getWidth();
  }

  public double getHeight() {
    return implementation.getHeight();
  }
  
}
