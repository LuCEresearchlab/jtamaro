package jtamaro.internal.representation;

import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.awt.geom.Ellipse2D;

import jtamaro.internal.gui.RenderOptions;


public final class EllipseImpl extends GraphicImpl {

  private final double width;
  private final double height;
  private final ColorImpl color;


  public EllipseImpl(final double width, final double height, final ColorImpl color) {
    this.width = width;
    this.height = height;
    this.color = color;
    final Ellipse2D.Double ellipse = new Ellipse2D.Double(-width / 2, -height / 2, width, height);
    setPath(new Path2D.Double(ellipse));
    //setBaseY(height / 2); // bottom of ellipse
    //addBoundingBoxLocations();
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
    appendField(sb, indent, "width", ""+width);
    appendField(sb, indent, "height", ""+height);
    appendField(sb, indent, "color", ""+color);
  }

}
