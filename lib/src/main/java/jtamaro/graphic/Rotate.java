package jtamaro.graphic;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.util.Objects;
import javax.swing.tree.MutableTreeNode;

/**
 * A new graphic that rotates counterclockwise a provided graphic around its pinning position by the
 * given angle. A negative angle corresponds to a clockwise rotation.
 */
public final class Rotate extends Graphic {

  private final double angle;

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
    this.graphic = graphic;
  }

  public double getAngle() {
    return angle;
  }

  public Graphic getGraphic() {
    return graphic;
  }

  @Override
  double xForLocation(Location location) {
    if (location.isOfGraphic(this)) {
      return location.x;
    } else {
      // Rotate by angle
      final double xInGraphic = graphic.xForLocation(location);
      final double yInGraphic = graphic.yForLocation(location);
      final double radAngle = Math.toRadians(angle);
      return Double.isNaN(xInGraphic) || Double.isNaN(yInGraphic)
          ? Double.NaN
          : Math.cos(radAngle) * xInGraphic - Math.sin(radAngle) * yInGraphic;
    }
  }

  @Override
  double yForLocation(Location location) {
    if (location.isOfGraphic(this)) {
      return location.y;
    } else {
      // Rotate by angle
      final double xInGraphic = graphic.xForLocation(location);
      final double yInGraphic = graphic.yForLocation(location);
      final double radAngle = Math.toRadians(angle);
      return Double.isNaN(xInGraphic) || Double.isNaN(yInGraphic)
          ? Double.NaN
          : Math.sin(radAngle) * xInGraphic + Math.cos(radAngle) * yInGraphic;
    }
  }

  @Override
  protected void render(Graphics2D g2d, RenderOptions options) {
    final AffineTransform baseTransform = g2d.getTransform();
    g2d.rotate(-Math.toRadians(angle));
    graphic.render(g2d, options);
    g2d.setTransform(baseTransform);
  }

  @Override
  protected void dump(StringBuilder sb, String indent) {
    super.dump(sb, indent);
    dumpField(sb, indent, "angle", angle);
    dumpChild(sb, indent, "graphic", graphic);
  }

  @Override
  public MutableTreeNode createInspectTree() {
    return new InspectTreeNode(graphic);
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
