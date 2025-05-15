package jtamaro.graphic;

import java.awt.Graphics2D;

/**
 * An empty graphic. When an empty graphic is composed with any other graphic, it behaves as a
 * neutral element: the result is always identical to the other graphic.
 */
final class EmptyGraphic extends Graphic {

  public EmptyGraphic() {
    super();
  }

  @Override
  protected void render(Graphics2D g2d, RenderOptions options) {
    // Do nothing
  }

  @Override
  protected String getInspectLabel() {
    return "emptyGraphic";
  }

  @Override
  public boolean structurallyEqualTo(Graphic other) {
    return other instanceof EmptyGraphic;
  }

  @Override
  public boolean equals(Object other) {
    return other instanceof EmptyGraphic;
  }

  @Override
  public int hashCode() {
    return EmptyGraphic.class.hashCode();
  }
}
