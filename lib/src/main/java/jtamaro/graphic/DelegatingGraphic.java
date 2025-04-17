package jtamaro.graphic;

import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import jtamaro.data.Option;

/**
 * Useful to create operators like {@link Overlay}, {@link Above}, and {@link Beside}, or any other
 * operator we want to explicitly represent, but that can be expressed with existing operators.
 */
abstract sealed class DelegatingGraphic
    extends Graphic
    permits Above, Beside, Overlay, LabeledGraphic {

  private final Graphic delegate;

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
  Location getLocation(Point point) {
    return delegate.getLocation(point);
  }

  @Override
  double xForLocation(Location location) {
    return delegate.xForLocation(location);
  }

  @Override
  double yForLocation(Location location) {
    return delegate.yForLocation(location);
  }

  @Override
  public Option<Graphic> nodeContaining(double x, double y) {
    return delegate.nodeContaining(x, y);
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
}
