package jtamaro.internal.representation;

import java.awt.Graphics2D;
import java.awt.geom.Path2D;

import jtamaro.internal.gui.RenderOptions;


public final class EmptyGraphicImpl extends GraphicImpl {

  public EmptyGraphicImpl() {
    final Path2D.Double path = new Path2D.Double();
    path.moveTo(0, 0);
    setPath(path);
    setBaseY(0);
    addBoundingBoxLocations();
  }

  @Override
  public void render(final Graphics2D g2, final RenderOptions o) {
    // draw no shape
  }

}
