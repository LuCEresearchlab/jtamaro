package jtamaro.internal.representation;

import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.awt.geom.AffineTransform;

import jtamaro.internal.gui.RenderOptions;


/**
 * A triangle specified using two sides and the angle between them.
 * The specified angle is at the top-left corner of the triangle,
 * while the first side extends horizontally to the right.
 * Its pinning position is the centroid of the triangle.
 */
public final class TriangleImpl extends GraphicImpl {

  private final double side1;
  private final double side2;
  private final double angle;
  private final ColorImpl color;


  public TriangleImpl(final double side1, final double side2, final double angle, final ColorImpl color) {
    this.side1 = side1;
    this.side2 = side2;
    this.angle = angle;
    this.color = color;
    // define path with the angle's corner at (0, 0)
    final double angleInRadians = Math.toRadians(-angle);
    final double thirdPointX = side2 * Math.cos(angleInRadians);
    final double thirdPointY = side2 * Math.sin(angleInRadians);
    final Path2D.Double path = new Path2D.Double();
    path.moveTo(0, 0);
    path.lineTo(side1, 0);
    path.lineTo(thirdPointX, thirdPointY);
    path.closePath();
    // translate path so (0, 0) -- which always is the pinning position -- is the centroid
    final double centroidX = (side1 + thirdPointX) / 3;
    final double centroidY = thirdPointY / 3;
    path.transform(AffineTransform.getTranslateInstance(-centroidX, -centroidY));

    setPath(path);
    setBaseY(getBBox().getMaxY());
    addBoundingBoxLocations();
    addLocation(Place.CENTER, 0, 0);
    //addLocation(Place.ANGLE, -centroidX, -centroidY);
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
    appendField(sb, indent, "side1", ""+side1);
    appendField(sb, indent, "side2", ""+side2);
    appendField(sb, indent, "angle", ""+angle);
    appendField(sb, indent, "color", ""+color);
  }

}
