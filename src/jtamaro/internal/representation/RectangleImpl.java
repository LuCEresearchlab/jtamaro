package jtamaro.internal.representation;

import java.awt.Graphics2D;
import java.awt.geom.Path2D;

import jtamaro.internal.gui.RenderOptions;


public final class RectangleImpl extends GraphicImpl {

  private final double width;
  private final double height;
  private final ColorImpl color;


  public RectangleImpl(final double width, final double height, final ColorImpl color) {
    this.width = width;
    this.height = height;
    this.color = color;
    final Path2D.Double path = new Path2D.Double();
    path.moveTo(-width / 2, -height / 2);
    path.lineTo(width / 2, -height / 2);
    path.lineTo(width / 2, height / 2);
    path.lineTo(-width / 2, height / 2);
    path.closePath();
    setPath(path);
    setBaseY(height / 2);
    addBoundingBoxPoints();
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
    appendField(sb, indent, "width", ""+width);
    appendField(sb, indent, "height", ""+height);
    appendField(sb, indent, "color", ""+color);
  }

}
