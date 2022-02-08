package jtamaro.internal.representation;

import java.awt.Graphics2D;
import java.awt.geom.Path2D;

import jtamaro.internal.gui.RenderOptions;


public final class EmptyGraphicImpl extends GraphicImpl {

  public EmptyGraphicImpl() {
    setPath(makePath());
    addBoundingBoxPoints();
  }

  private static Path2D.Double makePath() {
    Path2D.Double path = new Path2D.Double();
    path.moveTo(0, 0);
    return path;
  }

  @Override
  public double getBaseline() {
    return 0;
  }

  @Override
  public void render(final Graphics2D g2, final RenderOptions o) {
    // draw no shape, just the debug info
    drawDebugInfo(g2, o);
  }

}
