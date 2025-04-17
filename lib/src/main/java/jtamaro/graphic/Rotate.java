package jtamaro.graphic;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.SequencedMap;
import jtamaro.data.Option;

/**
 * A new graphic that rotates counterclockwise a provided graphic around its pinning position by the
 * given angle. A negative angle corresponds to a clockwise rotation.
 */
final class Rotate extends Graphic {

  private final double angle;

  private final double radAngle;

  private final Graphic graphic;

  /**
   * Default constructor.
   *
   * @param angle   angle of counterclockwise rotation, in degrees
   * @param graphic graphic to rotate
   */
  public Rotate(double angle, Graphic graphic) {
    super(buildPath(angle, graphic.getPath()));
    this.angle = angle;
    this.radAngle = Math.toRadians(angle);
    this.graphic = graphic;
  }

  public double getAngle() {
    return angle;
  }

  public Graphic getGraphic() {
    return graphic;
  }

  @Override
  double xForLocation(RelativeLocation location) {
    if (location.isOfGraphic(this)) {
      return location.x();
    } else {
      // Rotate by angle
      final double xInGraphic = graphic.xForLocation(location);
      final double yInGraphic = graphic.yForLocation(location);
      return Double.isNaN(xInGraphic) || Double.isNaN(yInGraphic)
          ? Double.NaN
          : Math.cos(radAngle) * xInGraphic - Math.sin(radAngle) * yInGraphic;
    }
  }

  @Override
  double yForLocation(RelativeLocation location) {
    if (location.isOfGraphic(this)) {
      return location.y();
    } else {
      // Rotate by angle
      final double xInGraphic = graphic.xForLocation(location);
      final double yInGraphic = graphic.yForLocation(location);
      return Double.isNaN(xInGraphic) || Double.isNaN(yInGraphic)
          ? Double.NaN
          : Math.sin(radAngle) * xInGraphic + Math.cos(radAngle) * yInGraphic;
    }
  }

  @Override
  Option<RelativeLocation> relativeLocationOf(double x, double y) {
    final double rotatedX = Double.isNaN(x)
        ? Double.NaN
        : Math.cos(radAngle) * x - Math.sin(radAngle) * y;
    final double rotatedY = Double.isNaN(y)
        ? Double.NaN
        : Math.sin(radAngle) * x + Math.cos(radAngle) * y;
    return graphic.relativeLocationOf(rotatedX, rotatedY);
  }

  @Override
  protected void render(Graphics2D g2d, RenderOptions options) {
    final AffineTransform baseTransform = g2d.getTransform();
    g2d.rotate(-radAngle);
    graphic.render(g2d, options);
    g2d.setTransform(baseTransform);
  }

  @Override
  SequencedMap<String, Graphic> getChildren() {
    final SequencedMap<String, Graphic> children = new LinkedHashMap<>();
    children.put("graphic", graphic);
    return children;
  }

  @Override
  protected SequencedMap<String, String> getProps(boolean plainText) {
    final SequencedMap<String, String> props = super.getProps(plainText);
    props.put("angle", String.format("%.2f", angle));
    return props;
  }

  @Override
  protected String getInspectLabel() {
    return "rotate";
  }

  @Override
  public boolean equals(Object other) {
    return this == other
        || (other instanceof Rotate that
        && Double.compare(that.angle, angle) == 0
        && Objects.equals(that.graphic, graphic));
  }

  @Override
  public int hashCode() {
    return Objects.hash(Rotate.class, angle, graphic);
  }

  private static Path2D.Double buildPath(double angle, Path2D.Double graphicPath) {
    final Path2D.Double path = new Path2D.Double(graphicPath);
    path.transform(AffineTransform.getRotateInstance(-Math.toRadians(angle)));
    return path;
  }
}
