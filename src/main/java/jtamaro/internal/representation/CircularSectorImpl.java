package jtamaro.internal.representation;

import jtamaro.internal.gui.RenderOptions;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Path2D;


public final class CircularSectorImpl extends GraphicImpl {

  private final double radius;
  private final double angle;
  private final ColorImpl color;
  private final Location arcCenter;
  private final Location arcStart;
  private final Location arcEnd;


  public CircularSectorImpl(final double radius, final double angle, final ColorImpl color) {
    this.radius = radius;
    this.angle = angle;
    this.color = color;
    final Arc2D.Double arc = new Arc2D.Double(-radius, -radius, radius * 2, radius * 2, 0, angle, Arc2D.PIE);
    setPath(new Path2D.Double(arc));
    //setBaseY(radius); // even if below BB bottom
    //addBoundingBoxLocations();
    //addLocation(Place.CENTER, 0, 0);
    //addLocation(Place.BEGIN, radius, 0);
    final double angleRad = -angle * 2 * Math.PI / 360;
    // Note: The following ex, ey calculation only works for circle, not for ellipse
    // https://math.stackexchange.com/questions/493104/evaluating-int-ab-frac12-r2-mathrm-d-theta-to-find-the-area-of-an-ellips/687384#687384
    final double ex = radius * Math.cos(angleRad);
    final double ey = -radius * Math.sin(angleRad);
    //addLocation(Place.END, ex, ey);
    arcCenter = new Location(this, 0, 0);
    arcStart = new Location(this, radius, 0);
    arcEnd = new Location(this, ex, ey);
  }

  // new Point API
  public Location getArcStart() {
    return arcStart;
  }

  public Location getArcEnd() {
    return arcEnd;
  }

  public Location getArcCenter() {
    return arcCenter;
  }

  public ColorImpl getColor() {
    return color;
  }

  @Override
  public void render(final Graphics2D g2, final RenderOptions o) {
    g2.setPaint(color.toAWT());
    g2.fill(getPath());
  }

  @Override
  public void dump(final StringBuilder sb, final String indent) {
    super.dump(sb, indent);
    appendField(sb, indent, "radius", "" + radius);
    appendField(sb, indent, "angle", "" + angle);
    appendField(sb, indent, "color", "" + color);
  }

}
