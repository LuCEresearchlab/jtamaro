package jtamaro.graphic;

import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.util.Map;
import java.util.Objects;

/**
 * A rectangle of the given size, filled with a color.
 */
final class Rectangle extends Graphic {

  private final double width;

  private final double height;

  private final Color color;

  /**
   * Default constructor.
   *
   * @param width  width of the rectangle
   * @param height height of the rectangle
   * @param color  color to be used to fill the rectangle
   */
  public Rectangle(double width, double height, Color color) {
    super(buildPath(width, height));
    this.width = width;
    this.height = height;
    this.color = color;
  }

  public Color getColor() {
    return color;
  }

  @Override
  public double getWidth() {
    return width;
  }

  @Override
  public double getHeight() {
    return height;
  }

  @Override
  protected void render(Graphics2D g2d, RenderOptions options) {
    g2d.setPaint(Graphic.renderableColor(color));
    g2d.fill(getPath());
  }

  @Override
  protected void dump(StringBuilder sb, String indent) {
    super.dump(sb, indent);
    dumpField(sb, indent, "color", color);
  }

  @Override
  protected String getInspectLabel() {
    return "rectangle";
  }

  @Override
  protected Map<String, String> getProps(boolean plainText) {
    final Map<String, String> props = super.getProps(plainText);
    props.put("color", plainText
        ? color.toString()
        : Colors.htmlString(color));
    return props;
  }

  @Override
  public boolean equals(Object other) {
    return this == other
        || (other instanceof Rectangle that
        && Double.compare(that.width, width) == 0
        && Double.compare(that.height, height) == 0
        && Objects.equals(that.color, color));
  }

  @Override
  public int hashCode() {
    return Objects.hash(Rectangle.class, width, height, color);
  }

  private static Path2D.Double buildPath(double width, double height) {
    final Path2D.Double path = new Path2D.Double();
    final double halfWidth = width / 2.0;
    final double halfHeight = height / 2.0;
    path.moveTo(-halfWidth, -halfHeight);
    path.lineTo(halfWidth, -halfHeight);
    path.lineTo(halfWidth, halfHeight);
    path.lineTo(-halfWidth, halfHeight);
    path.closePath();
    return path;
  }
}
