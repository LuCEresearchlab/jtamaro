package jtamaro.internal.representation;

import java.awt.Graphics2D;
import java.awt.geom.Path2D;

import jtamaro.internal.gui.RenderOptions;


/**
 * Useful to create operators like Overlay, Above, and Beside,
 * or any other operator we want to explicitly represent,
 * but that can be expressed with existing operators.
 */
public abstract class DelegatingGraphicImpl extends GraphicImpl {

  private final GraphicImpl delegate;


  public DelegatingGraphicImpl(GraphicImpl delegate) {
    this.delegate = delegate;
  }

  @Override
  public TightBoundingBox getBBox() {
    return delegate.getBBox();
  }

  @Override
  public Location getLocation(final PointImpl point) {
    return delegate.getLocation(point);
  }

  @Override
  protected double xForLocation(final Location location) {
    return delegate.xForLocation(location);
  }

  @Override
  protected double yForLocation(final Location location) {
    return delegate.yForLocation(location);
  }

  @Override
  public double getWidth() {
    return delegate.getWidth();
  }

  @Override
  public double getHeight() {
    return delegate.getHeight();
  }

  /*
  @Override
  public double getBaseY() {
    return delegate.getBaseY();
  }
  */

  @Override
  public Path2D.Double getPath() {
    return delegate.getPath();
  }

  @Override
  public void render(final Graphics2D g2, final RenderOptions o) {
    delegate.render(g2, o);
  }

}
