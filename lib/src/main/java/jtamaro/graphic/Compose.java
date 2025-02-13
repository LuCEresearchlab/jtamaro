package jtamaro.graphic;

import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.util.Objects;
import javax.swing.tree.MutableTreeNode;

/**
 * A graphic that composes the two provided graphics. The first graphic is kept in the foreground,
 * the second one in the background. The graphics are aligned by superimposing their pinning
 * positions.
 *
 * <p>The pinning position used to compose becomes the pinning position of the resulting graphic.
 */
final class Compose extends Graphic {

  private final Graphic foreground;

  private final Graphic background;

  /**
   * Default constructor.
   *
   * @param foreground graphic in the foreground
   * @param background graphic in the background
   */
  public Compose(Graphic foreground, Graphic background) {
    super(buildPath(foreground.getPath(), background.getPath()));
    this.foreground = foreground;
    this.background = background;
  }

  public Graphic getForeground() {
    return foreground;
  }

  public Graphic getBackground() {
    return background;
  }

  @Override
  protected void render(Graphics2D g2d, RenderOptions options) {
    background.render(g2d, options);
    foreground.render(g2d, options);
  }

  @Override
  public Point getPin() {
    return foreground.getPin();
  }

  @Override
  double xForLocation(Location location) {
    return location.isOfGraphic(this)
        ? location.x
        : valueForLocation(
            location,
            background.xForLocation(location),
            foreground.xForLocation(location)
        );
  }

  @Override
  double yForLocation(Location location) {
    return location.isOfGraphic(this)
        ? location.y
        : valueForLocation(
            location,
            background.yForLocation(location),
            foreground.yForLocation(location)
        );
  }

  private double valueForLocation(Location location, double inBottom, double inTop) {
    if (!Double.isNaN(inBottom) && !Double.isNaN(inTop)) {
      throw new IllegalArgumentException(location
          + " exists multiple times in composition "
          + this
          + ". Did you compose the same graphic twice?");
    } else if (!Double.isNaN(inBottom)) {
      return inBottom;
    } else if (!Double.isNaN(inTop)) {
      return inTop;
    } else {
      return Double.NaN;
    }
  }

  @Override
  protected void dump(StringBuilder sb, String indent) {
    super.dump(sb, indent);
    dumpChild(sb, indent, "foreground", foreground);
    dumpChild(sb, indent, "background", background);
  }

  @Override
  public MutableTreeNode createInspectTree() {
    return new InspectTreeNode(foreground, background);
  }

  @Override
  protected String getInspectLabel() {
    return "compose";
  }

  @Override
  public boolean equals(Object other) {
    return this == other
        || (other instanceof Compose that
        && Objects.equals(that.foreground, foreground)
        && Objects.equals(that.background, background));
  }

  @Override
  public int hashCode() {
    return Objects.hash(Compose.class, foreground, background);
  }

  private static Path2D.Double buildPath(Path2D.Double fgPath, Path2D.Double bgPath) {
    final Path2D.Double path = new Path2D.Double();
    path.append(bgPath, false);
    path.append(fgPath, false);
    return path;
  }
}
