package jtamaro.internal.representation;

import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Path2D;

import jtamaro.internal.gui.RenderOptions;


public final class CircularSectorImpl extends GraphicImpl {

  private final double radius;
  private final double angle;
  private final ColorImpl color;


  public CircularSectorImpl(final double radius, final double angle, final ColorImpl color) {
    this.radius = radius;
    this.angle = angle;
    this.color = color;
    final Arc2D.Double arc = new Arc2D.Double(0, 0, radius * 2, radius * 2, 0, -angle, Arc2D.PIE);
    setPath(new Path2D.Double(arc));
    setBaseY(radius); // even if below BB bottom
    addBoundingBoxPoints();
    addPoint(Place.CENTER, 0, 0);
    addPoint(Place.BEGIN, radius, 0);
    final double angleRad = angle * 2 * Math.PI / 360;
    // Note: The following ex, ey calculation only works for circle, not for ellipse
    // https://math.stackexchange.com/questions/493104/evaluating-int-ab-frac12-r2-mathrm-d-theta-to-find-the-area-of-an-ellips/687384#687384
    final double ex = radius * Math.cos(angleRad);
    final double ey = -radius * Math.sin(angleRad);
    addPoint(Place.END, ex, ey);
  }

  public ColorImpl getColor() {
    return color;
  }
  
  @Override
  public void render(final Graphics2D g2, final RenderOptions o) {
    g2.setPaint(color.toAWT());
    g2.fill(getPath());
    drawDebugInfo(g2, o);
  }

  @Override
  public void dump(final StringBuilder sb, final String indent) {
    super.dump(sb, indent);
    appendField(sb, indent, "radius", ""+radius);
    appendField(sb, indent, "angle", ""+angle);
    appendField(sb, indent, "color", ""+color);
  }

}
