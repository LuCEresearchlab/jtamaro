package jtamaro.graphic;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.util.Map;
import java.util.Objects;

/**
 * A triangle with two sides and the angle between them, filled with a color. The first side extends
 * horizontally to the right. The angle specifies how much the second side is rotated,
 * counterclockwise, from the first one.
 *
 * <p>For all triangles, except obtuse ones, the bottom-left corner of the resulting graphic
 * coincides with the vertex of the triangle for which the angle is specified.
 *
 * <p>The pinning position is the centroid of the triangle.
 */
final class Triangle extends Graphic {

  private final double side1;

  private final double side2;

  private final double angle;

  private final Color color;

  /**
   * Default constructor.
   *
   * @param side1 length of the first, horizontal side of the triangle
   * @param side2 length of the second side of the triangle
   * @param angle angle between the two sides, in degrees
   * @param color color to be used to fill the triangle
   */
  public Triangle(double side1, double side2, double angle, Color color) {
    super(buildPath(side1, side2, angle));
    this.side1 = side1;
    this.side2 = side2;
    this.angle = angle;
    this.color = color;
  }

  public double getSide1() {
    return side1;
  }

  public double getSide2() {
    return side2;
  }

  public double getAngle() {
    return angle;
  }

  public Color getColor() {
    return color;
  }

  @Override
  protected void render(Graphics2D g2d, RenderOptions options) {
    g2d.setPaint(Graphic.renderableColor(color));
    g2d.fill(getPath());
  }

  @Override
  protected void dump(StringBuilder sb, String indent) {
    super.dump(sb, indent);
    dumpField(sb, indent, "side1", side1);
    dumpField(sb, indent, "side2", side2);
    dumpField(sb, indent, "angle", angle);
    dumpField(sb, indent, "color", color);
  }

  @Override
  protected String getInspectLabel() {
    return "triangle";
  }

  @Override
  protected Map<String, String> getProps(boolean plainText) {
    final Map<String, String> props = super.getProps(plainText);
    props.put("side1", String.format("%.2f", side1));
    props.put("side2", String.format("%.2f", side2));
    props.put("angle", String.format("%.2f", angle));
    props.put("color", plainText
        ? color.toString()
        : Colors.htmlString(color));
    return props;
  }

  @Override
  public boolean equals(Object other) {
    return this == other
        || (other instanceof Triangle that
        && Double.compare(that.side1, side1) == 0
        && Double.compare(that.side2, side2) == 0
        && Double.compare(that.angle, angle) == 0
        && Objects.equals(that.color, color));
  }

  @Override
  public int hashCode() {
    return Objects.hash(Triangle.class, side1, side2, angle, color);
  }

  private static Path2D.Double buildPath(double side1, double side2, double angle) {
    final double radAngle = Math.toRadians(-angle);
    final double thirdPointX = side2 * Math.cos(radAngle);
    final double thirdPointY = side2 * Math.sin(radAngle);

    final Path2D.Double path = new Path2D.Double();
    path.moveTo(0, 0);
    path.lineTo(side1, 0);
    path.lineTo(thirdPointX, thirdPointY);
    path.closePath();

    // translate path so (0, 0) -- which always is the pinning position -- is the centroid
    final double centroidX = (side1 + thirdPointX) / 3;
    final double centroidY = thirdPointY / 3;
    path.transform(AffineTransform.getTranslateInstance(-centroidX, -centroidY));

    return path;
  }
}
