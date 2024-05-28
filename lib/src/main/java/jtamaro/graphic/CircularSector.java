package jtamaro.graphic;

import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Path2D;
import java.util.Objects;

/**
 * A circular sector belonging to a circle of the given radius, filled with a color.
 *
 * <p>A circular sector is a portion of a circle enclosed between two radii and an arc.
 * Considering a circle as a clock, the first radius is supposed to "point" towards 3 o’clock. The
 * angle determines the position of the second radius, computed starting from the first one in
 * counterclockwise direction. An angle of 360 degrees corresponds to a full circle.
 *
 * <p>The pinning position is at the center of the circle from which the circular sector is
 * taken.
 */
public final class CircularSector extends Graphic {

  private final double radius;

  private final double angle;

  private final Color color;

  /**
   * Default constructor.
   *
   * @param radius radius of the circle from which the circular sector is taken
   * @param angle  central angle, in degrees
   * @param color  the color to be used to fill the circular sector
   */
  public CircularSector(double radius, double angle, Color color) {
    super(buildPath(radius, angle));
    this.radius = radius;
    this.angle = angle;
    this.color = color;
  }

  public double getRadius() {
    return radius;
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
    dumpField(sb, indent, "radius", radius);
    dumpField(sb, indent, "angle", angle);
    dumpField(sb, indent, "color", color);
  }

  @Override
  protected String getInspectLabel() {
    return "circularSector";
  }

  @Override
  public boolean equals(Object other) {
    return this == other
        || (other instanceof CircularSector that
        && Double.compare(that.radius, radius) == 0
        && Double.compare(that.angle, angle) == 0
        && Objects.equals(that.color, color));
  }

  @Override
  public int hashCode() {
    return Objects.hash(CircularSector.class, radius, angle, color);
  }

  private static Path2D.Double buildPath(double radius, double angle) {
    return new Path2D.Double(new Arc2D.Double(
        -radius,
        -radius,
        radius * 2.0,
        radius * 2.0,
        0.0,
        angle,
        Arc2D.PIE
    ));
  }
}
