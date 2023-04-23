package jtamaro.en.graphic;

import jtamaro.en.Graphic;
import jtamaro.internal.representation.GraphicImpl;


public abstract class AbstractGraphic implements Graphic {

  private final GraphicImpl implementation;

  public AbstractGraphic(GraphicImpl implementation) {
    this.implementation = implementation;
  }

  public GraphicImpl getImplementation() {
    return implementation;
  }

  @Override
  public double getWidth() {
    return implementation.getWidth();
  }

  @Override
  public double getHeight() {
    return implementation.getHeight();
  }

}
