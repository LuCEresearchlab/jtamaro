package jtamaro.internal.representation;

import java.awt.Graphics2D;
import java.awt.geom.Path2D;

import jtamaro.internal.gui.RenderOptions;


public final class TriangleImpl extends GraphicImpl {

  private final double side;
  private final ColorImpl color;


  public TriangleImpl(final double side, final ColorImpl color) {
    this.side = side;
    this.color = color;
    final double circumRadius = side / Math.sqrt(3);
    final double inRadius = circumRadius / 2;
    final Path2D.Double path = new Path2D.Double();
    path.moveTo(0, -circumRadius);
    path.lineTo(side / 2, inRadius);
    path.lineTo(-side / 2, inRadius);
    path.closePath();
    setPath(path);
    setBaseY(getBBox().getMaxY());
    addBoundingBoxPoints();
    addPoint(Place.CENTER, 0, 0);
    addPoint(Place.TOP, 0, -circumRadius);
    addPoint(Place.RIGHT, side / 2, inRadius);
    addPoint(Place.LEFT, -side / 2, inRadius);
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
    appendField(sb, indent, "side", ""+side);
    appendField(sb, indent, "color", ""+color);
  }

}
