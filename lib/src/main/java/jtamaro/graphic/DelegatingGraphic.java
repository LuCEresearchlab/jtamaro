package jtamaro.graphic;

import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import jtamaro.data.Option;

/**
 * Useful to create operators like {@link Overlay}, {@link Above}, and {@link Beside}, or any other
 * operator we want to explicitly represent, but that can be expressed with existing operators.
 *
 * @hidden
 */
abstract sealed class DelegatingGraphic
    extends Graphic
    permits Above, Beside, Overlay, ActionableGraphic {

  protected final Graphic delegate;

  public DelegatingGraphic(Graphic delegate) {
    super(); // Use the special constructor
    this.delegate = delegate;
  }

  @Override
  public Point getPin() {
    return delegate.getPin();
  }

  @Override
  Rectangle2D getBBox() {
    return delegate.getBBox();
  }

  @Override
  RelativeLocation getLocation(Point point) {
    return delegate.getLocation(point);
  }

  @Override
  double xForLocation(RelativeLocation location) {
    return delegate.xForLocation(location);
  }

  @Override
  double yForLocation(RelativeLocation location) {
    return delegate.yForLocation(location);
  }

  @Override
  Option<RelativeLocation> relativeLocationOf(double x, double y) {
    return delegate.relativeLocationOf(x, y);
  }

  @Override
  protected void render(Graphics2D g2d, RenderOptions options) {
    delegate.render(g2d, options);
  }

  @Override
  public Path2D.Double getPath() {
    return delegate.getPath();
  }

  @Override
  public double getWidth() {
    return delegate.getWidth();
  }

  @Override
  public double getHeight() {
    return delegate.getHeight();
  }

  @Override
  public boolean structurallyEqualTo(Graphic other) {
    // Two cases to account for:
    // - above(a, b) = above(a, b)
    // - above(a, b) = pin(CENTER, compose(pin(BOTTOM_CENTER, a), pin(TOP_CENTER, b)))
    return other instanceof DelegatingGraphic that
        ? delegate.structurallyEqualTo(that.delegate)
        : delegate.structurallyEqualTo(other);
  }
}
