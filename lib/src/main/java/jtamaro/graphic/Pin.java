package jtamaro.graphic;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.util.Objects;
import javax.swing.tree.MutableTreeNode;

/**
 * A graphic that corresponds to a provided graphic, with a new pinning position.
 *
 * <p>Each graphic is contained in a rectangular bounding box. There are 9 notable points,
 * corresponding to the four corners of this rectangle, the middle points of the four edges and the
 * center of the rectangle. These points can be referred to using these names:
 * {@link Points#TOP_LEFT}, {@link Points#TOP_CENTER}, {@link Points#TOP_RIGHT},
 * {@link Points#CENTER_LEFT}, {@link Points#CENTER}, {@link Points#CENTER_LEFT},
 * {@link Points#BOTTOM_LEFT}, {@link Points#BOTTOM_CENTER} and {@link Points#BOTTOM_RIGHT}.
 */
public final class Pin extends Graphic {

  private final Point pinPoint;

  private final Graphic graphic;

  private final double dx;

  private final double dy;

  /**
   * Default constructor.
   *
   * @param pinPoint the point indicating the new pinning position
   * @param graphic  original graphic
   */
  public Pin(Point pinPoint, Graphic graphic) {
    super(buildPath(pinPoint, graphic));
    this.pinPoint = pinPoint;
    this.graphic = graphic;

    final Location pinLocation = graphic.getLocation(pinPoint);
    this.dx = graphic.xForLocation(pinLocation);
    this.dy = graphic.yForLocation(pinLocation);
  }

  public Point getPinPoint() {
    return pinPoint;
  }

  public Graphic getGraphic() {
    return graphic;
  }

  @Override
  double xForLocation(Location location) {
    if (location.isOfGraphic(this)) {
      return location.x;
    } else {
      final double xInGraphic = graphic.xForLocation(location);
      return Double.isNaN(xInGraphic)
          ? Double.NaN
          : xInGraphic - dx;
    }
  }

  @Override
  double yForLocation(Location location) {
    if (location.isOfGraphic(this)) {
      return location.y;
    } else {
      final double yInGraphic = graphic.yForLocation(location);
      return Double.isNaN(yInGraphic)
          ? Double.NaN
          : yInGraphic - dx;
    }
  }

  @Override
  protected void render(Graphics2D g2d, RenderOptions options) {
    final AffineTransform baseTransform = g2d.getTransform();
    g2d.translate(-dx, -dy);
    graphic.render(g2d, options);
    g2d.setTransform(baseTransform);
  }

  @Override
  protected void dump(StringBuilder sb, String indent) {
    super.dump(sb, indent);
    dumpField(sb, indent, "pinPoint", pinPoint);
    dumpChild(sb, indent, "graphic", graphic);
  }

  @Override
  public MutableTreeNode createInspectTree() {
    return new InspectTreeNode(graphic);
  }

  @Override
  protected String getInspectLabel() {
    return "pin";
  }

  @Override
  public boolean equals(Object other) {
    return this == other
        || (other instanceof Pin that
        && Objects.equals(that.pinPoint, pinPoint)
        && Objects.equals(that.graphic, graphic));
  }

  @Override
  public int hashCode() {
    return Objects.hash(Pin.class, pinPoint, graphic);
  }

  private static Path2D.Double buildPath(Point pinPoint, Graphic graphic) {
    final Location location = graphic.getLocation(pinPoint);
    final double dx = graphic.xForLocation(location);
    final double dy = graphic.yForLocation(location);
    if (Double.isNaN(dx) || Double.isNaN(dy)) {
      throw new IllegalArgumentException("pinPoint "
          + location
          + " does not exist in the given graphic.");
    }
    final Path2D.Double path = new Path2D.Double(graphic.getPath());
    path.transform(AffineTransform.getTranslateInstance(-dx, -dy));
    return path;
  }
}
