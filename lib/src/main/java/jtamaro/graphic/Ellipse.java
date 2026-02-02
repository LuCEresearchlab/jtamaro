package jtamaro.graphic;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.util.Objects;
import java.util.SequencedMap;

/**
 * An ellipse with the given width and height, filled with a color.
 *
 * <p>When width and height are the same, the ellipse becomes a circle with a diameter equal to
 * the provided size.
 */
final class Ellipse extends Graphic {

  private final double width;

  private final double height;

  private final Color color;

  /**
   * Default constructor.
   *
   * @param width  width of the ellipse
   * @param height height of the ellipse
   * @param color  color to be used to fill the ellipse
   */
  public Ellipse(double width, double height, Color color) {
    super(buildPath(width, height));
    this.width = width;
    this.height = height;
    this.color = color;
  }

  @Override
  public double getWidth() {
    return width;
  }

  @Override
  public double getHeight() {
    return height;
  }

  public Color getColor() {
    return color;
  }

  @Override
  protected void render(Graphics2D g2d, RenderOptions options) {
    g2d.setPaint(Colors.toAwtColor(color));
    g2d.fill(getPath());
  }

  @Override
  protected String getInspectLabel() {
    return "ellipse";
  }

  @Override
  protected SequencedMap<String, String> getProps(PropStyle propStyle) {
    final SequencedMap<String, String> props = super.getProps(propStyle);
    props.put("color", Colors.format(propStyle, color));
    return props;
  }

  @Override
  public boolean structurallyEqualTo(Graphic other) {
    return other instanceof Ellipse that
        && Double.compare(that.width, width) == 0
        && Double.compare(that.height, height) == 0
        && Objects.equals(that.color, color);
  }

  @Override
  public boolean equals(Object other) {
    return this == other
        || (other instanceof Ellipse that && structurallyEqualTo(that));
  }

  @Override
  public int hashCode() {
    return Objects.hash(Ellipse.class, width, height, color);
  }

  private static Path2D.Double buildPath(double width, double height) {
    return new Path2D.Double(new Ellipse2D.Double(
        width / -2.0,
        height / -2.0,
        width,
        height
    ));
  }
}
